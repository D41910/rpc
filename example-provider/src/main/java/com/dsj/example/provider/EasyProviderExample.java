package com.dsj.example.provider;

import com.dsj.example.common.service.UserService;
import com.dsj.rpc.RpcApplication;
import com.dsj.rpc.registry.LocalRegistry;
import com.dsj.rpc.server.HttpServer;
import com.dsj.rpc.server.VertxHttpServer;

/**
 * 简易服务提供者示例
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        //RPC框架初始化
        RpcApplication.init();

        //注册服务
        LocalRegistry.register(UserService.class.getName(),UserServiceImpl.class);

        //启动web服务
        HttpServer httpServer=new VertxHttpServer();
        //根据配置动态修改web启动端口
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
