package com.dsj.rpc.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

/**
 * 配置工具类
 */
public class ConfigUtils {
    /**
     * 加载配置类
     * @param tClass
     * @param prefix
     * @return
     * @param <T>
     */
    public static <T> T loadConfig(Class<T> tClass,String prefix){
        return loadConfig(tClass,prefix,"");
    }

    /**
     * 加载配置对象，支持分区域环境
     * @param tClass
     * @param prefix
     * @param environment
     * @return
     * @param <T>
     */
    private static <T> T loadConfig(Class<T> tClass, String prefix, String environment) {
        StringBuilder configFileBuilder=new StringBuilder("application");
        if(StrUtil.isNotBlank(environment)){
            configFileBuilder.append("-").append(environment);
        }
        configFileBuilder.append(".properties");
        //获取配置文件，初始化Props
        Props props=new Props(configFileBuilder.toString());
        //转化为RpcConfig对象存储，并且配置前缀比如为prefix，最终返回
        return props.toBean(tClass,prefix);
    }
}
