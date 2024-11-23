package com.dsj.example.comsumer;

import com.dsj.example.common.model.User;
import com.dsj.example.common.service.UserService;
import com.dsj.rpc.proxy.ServiceProxyFactory;

/**
 * 简易服务消费者示例
 */
public class EasyConsumerExample {
    public static void main(String[] args) {
        UserService userService= ServiceProxyFactory.getProxy(UserService.class);
        User user=new User();
        user.setName("dsj");
        //调用
        User newUser=userService.getUser(user);
        if(newUser != null){
            System.out.println(newUser.getName());
        }else{
            System.out.println("user == null");
        }
    }
}
