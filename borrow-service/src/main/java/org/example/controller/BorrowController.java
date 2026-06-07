package org.example.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import jakarta.annotation.Resource;
import org.example.entity.UserBorrowDetail;
import org.example.service.BorrowService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class BorrowController {

    @Resource
    private BorrowService service;

//    @HystrixCommand(fallbackMethod = "onError")
    @RequestMapping("/borrow/{uid}")
    public UserBorrowDetail findUserBorrows(@PathVariable("uid") int uid) {
        return service.getUserBorrowDetailByUid(uid);
    }

    // 备选方案：参数、返回值必须与原方法一致
//    public UserBorrowDetail onError(int uid) {
//        return new UserBorrowDetail(null, Collections.emptyList());
//    }
}
