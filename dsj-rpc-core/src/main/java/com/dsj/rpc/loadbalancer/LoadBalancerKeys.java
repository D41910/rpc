package com.dsj.rpc.loadbalancer;

/**
 * 负载均衡器键名常量
 */
public interface LoadBalancerKeys {
    /**
     * 轮询负载均衡器
     */
    String ROUND_ROBIN = "roundRobin";

    /**
     * 随机负载均衡器
     */
    String RANDOM = "random";

    /**
     * 一致性哈希负载均衡器
     */
    String CONSISTENT_HASH = "consistentHash";

}
