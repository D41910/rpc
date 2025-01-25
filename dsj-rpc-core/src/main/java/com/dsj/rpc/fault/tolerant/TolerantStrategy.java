package com.dsj.rpc.fault.tolerant;

import com.dsj.rpc.model.RpcResponse;

import java.util.Map;

/**
 * 容错策略
 */
public interface TolerantStrategy {
    /**
     * 容错
     *
     * @param context 上下文，用于传输数据
     * @param e       异常
     * @return
     */
    RpcResponse deTolerant(Map<String, Object> context, Exception e);
}
