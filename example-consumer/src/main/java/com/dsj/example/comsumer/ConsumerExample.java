package com.dsj.example.comsumer;

import com.dsj.rpc.config.RpcConfig;
import com.dsj.rpc.utils.ConfigUtils;

/**
 * 简易服务消费者示例
 */
public class ConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpc= ConfigUtils.loadConfig(RpcConfig.class,"rpc");
        System.out.println(rpc);
        System.out.println(rpc.getName());
        System.out.println(rpc.getVersion());
        System.out.println(rpc.getServerHost());
        System.out.println(rpc.getServerPort());
    }
}
