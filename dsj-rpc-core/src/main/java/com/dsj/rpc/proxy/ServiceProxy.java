package com.dsj.rpc.proxy;

import cn.hutool.core.collection.CollUtil;
import com.dsj.rpc.RpcApplication;
import com.dsj.rpc.config.RpcConfig;
import com.dsj.rpc.constant.RpcConstant;
import com.dsj.rpc.loadbalancer.LoadBalancer;
import com.dsj.rpc.loadbalancer.LoadBalancerFactory;
import com.dsj.rpc.model.RpcRequest;
import com.dsj.rpc.model.RpcResponse;
import com.dsj.rpc.model.ServiceMetaInfo;
import com.dsj.rpc.registry.Registry;
import com.dsj.rpc.registry.RegistryFactory;
import com.dsj.rpc.serializer.Serializer;
import com.dsj.rpc.serializer.SerializerFactory;
import com.dsj.rpc.server.tcp.VertxTcpClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * 服务代理类
 */
public class ServiceProxy implements InvocationHandler {
    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //指定序列化器
        Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        //构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        try {
            //序列化
//            byte[] bodyBytes = serializer.serializer(rpcRequest);
            //从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }

            //获取负载均衡器实例
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
            //将调用方法名（请求路径）作为负载均衡器参数
            HashMap<String, Object> requestParams = new HashMap<>();
            requestParams.put("methodName", rpcRequest.getMethodName());
            ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);


            //发送HTTP请求
//            try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress())
//                    .body(bodyBytes)
//                    .execute()) {
//                byte[] result = httpResponse.bodyBytes();
//                //反序列化
//                RpcResponse rpcResponse = serializer.deserializer(result, RpcResponse.class);
//                return rpcResponse.getData();
//            }

            //发送TCP请求
            RpcResponse rpcResponse = VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo);
            return rpcResponse.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
