package org.example.controller;

import org.example.entiey.Book;
import org.example.service.BookService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
public class BookController {

    @Resource
    BookService service;

    @RequestMapping("/book/{bid}")
    Book findBookById(@PathVariable("bid") int bid, HttpSession session) {

        //通过SecurityContextHolder将用户信息取出
        SecurityContext context =SecurityContextHolder.getContext();
        System.out.println(context.getAuthentication());
        return service.getBookById(bid);
    }
}
