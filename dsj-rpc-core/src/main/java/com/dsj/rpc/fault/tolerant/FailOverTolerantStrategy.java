package com.dsj.rpc.fault.tolerant;

import com.dsj.rpc.model.RpcResponse;

import java.util.Map;

/**
 * 转移到其他服务节点 - 容错策略
 */
public class FailOverTolerantStrategy implements TolerantStrategy {
    @Override
    public RpcResponse deTolerant(Map<String, Object> context, Exception e) {
        // todo 获取其他服务节点并调用
        return null;
    }
}
