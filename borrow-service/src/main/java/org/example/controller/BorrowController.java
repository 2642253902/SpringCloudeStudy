package org.example.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import org.example.entiey.User;
import org.example.entity.UserBorrowDetail;
import org.example.service.BorrowService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class BorrowController {

    @Resource
    BorrowService service;


    @RequestMapping("/borrow/{uid}")
    UserBorrowDetail findUserBorrows(@PathVariable("uid") int uid) {
        return service.getUserBorrowDetailByUid(uid);
    }

    @RequestMapping("/borrow2/{uid}")
    @SentinelResource(value = "findUserBorrows2", blockHandler = "test")
    UserBorrowDetail findUserBorrows2(@PathVariable("uid") int uid) throws InterruptedException {
        throw new RuntimeException("测试异常");
    }

    UserBorrowDetail test(int uid, BlockException e) {
        System.out.println(e.getClass() + "  " + e.getMessage());
        return new UserBorrowDetail(new User(), Collections.emptyList());
    }


    @RequestMapping("/blocked")
    JSONObject blocked() {
        JSONObject object = new JSONObject();
        object.put("code", 403);
        object.put("success", false);
        object.put("massage", "您的请求频率过快，请稍后再试！");
        return object;
    }


    @RequestMapping("/test")
    @SentinelResource("test")
        //注意这里需要添加@SentinelResource才可以，用户资源名称就使用这里定义的资源名称
    String findUserBorrows2(@RequestParam(value = "a", required = false) String a,
                            @RequestParam(value = "b", required = false) String b,
                            @RequestParam(value = "c", required = false) String c) {
        return "请求成功！a = " + a + ", b = " + b + ", c = " + c;
    }

}
