package com.dsj.rpc.fault.tolerant;

import com.dsj.rpc.model.RpcResponse;

import java.util.Map;

/**
 * 快速失败 - 容错策略（立即通知外层调用方）
 */
public class FailFastTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse deTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务报错", e);
    }
}
