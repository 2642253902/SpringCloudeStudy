package org.example.controller;

import jakarta.annotation.Resource;

import org.example.entiey.User;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Resource
    UserService service;

    //这里以RESTFul风格为例
    @RequestMapping("/user/{uid}")
    public User findUserById(@PathVariable("uid") int uid) {

        System.out.println("我被调用了，uid = " + uid);

        return service.getUserById(uid);
    }

}
