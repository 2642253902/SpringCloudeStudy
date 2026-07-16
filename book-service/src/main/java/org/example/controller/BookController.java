package org.example.controller;

import jdk.nashorn.internal.ir.annotations.Reference;
import org.example.entiey.Book;
import org.example.service.BookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RefreshScope
@RestController
public class BookController {

    @Resource
    BookService service;

    @RequestMapping("/book/{bid}")
    Book findBookById(@PathVariable("bid") int bid) {
        return service.getBookById(bid);
    }
    @Value("${test.name}")
    String name;

    @RequestMapping("/test")
    String  test() {
        return name;
    }

}
