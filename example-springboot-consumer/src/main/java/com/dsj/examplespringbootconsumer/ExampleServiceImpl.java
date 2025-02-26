package com.dsj.examplespringbootconsumer;

import com.dsj.example.common.model.User;
import com.dsj.example.common.service.UserService;
import com.dsj.rpc.springboot.starter.annotation.RpcReference;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl {

    @RpcReference
    private UserService userService;

    public void test() {
        User user = new User();
        user.setName("dsj");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }

}
