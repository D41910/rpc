package com.dsj.example.provider;

import com.dsj.example.common.service.UserService;
import com.dsj.rpc.RpcApplication;
import com.dsj.rpc.config.RegistryConfig;
import com.dsj.rpc.config.RpcConfig;
import com.dsj.rpc.model.ServiceMetaInfo;
import com.dsj.rpc.registry.LocalRegistry;
import com.dsj.rpc.registry.Registry;
import com.dsj.rpc.registry.RegistryFactory;
import com.dsj.rpc.server.HttpServer;
import com.dsj.rpc.server.VertxHttpServer;
import com.dsj.rpc.server.tcp.VertxTcpServer;

public class ProviderExample {

    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();
        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 web 服务
//        HttpServer httpServer = new VertxHttpServer();
//        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());

        //启动TCP服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(8080);
    }
}
