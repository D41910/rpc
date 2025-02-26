package com.dsj.rpc.bootstrap;

import com.dsj.rpc.RpcApplication;
import com.dsj.rpc.config.RegistryConfig;
import com.dsj.rpc.config.RpcConfig;
import com.dsj.rpc.model.ServiceMetaInfo;
import com.dsj.rpc.model.ServiceRegisterInfo;
import com.dsj.rpc.registry.LocalRegistry;
import com.dsj.rpc.registry.Registry;
import com.dsj.rpc.registry.RegistryFactory;
import com.dsj.rpc.server.tcp.VertxTcpServer;

import java.util.List;

/**
 * 服务提供者初始化
 */
public class ProviderBootstrap {
    /**
     * 初始化
     */
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) {
        //RPC框架初始化（配置和注册中心）
        RpcApplication.init();
        //全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        //注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            //本地注册
            LocalRegistry.register(serviceName, serviceRegisterInfo.getImplClass());

            //注册到服务中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + "服务注册失败", e);
            }
        }

        //启动服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getServerPort());
    }
}
