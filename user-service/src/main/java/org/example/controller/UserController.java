package org.example.controller;

import jakarta.annotation.Resource;

import org.example.entiey.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope   //添加此注解就能实现自动刷新了
public class UserController {
    @Resource
    UserService service;

    @Value("${test.lbwnb}")
    String test;


    //这里以RESTFul风格为例
    @RequestMapping("/user/{uid}")
    public User findUserById(@PathVariable("uid") int uid) {
        System.out.println("test: " + test);
        return service.getUserById(uid);
    }

    @RequestMapping("/user/remain/{uid}")
    public int userRemain(@PathVariable("uid") int uid) {
        return service.getRemain(uid);
    }

    @RequestMapping("/user/borrow/{uid}")
    public boolean userBorrow(@PathVariable("uid") int uid) {
        int remain = service.getRemain(uid);
        return service.setRemain(uid, remain - 1);
    }
}
