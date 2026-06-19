package org.example.controller;

import jakarta.annotation.Resource;
import org.example.entiey.Book;
import org.example.service.BookService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @Resource
    BookService service;

    @RequestMapping("/book/{bid}")
    Book findBookById(@PathVariable("bid") int bid) {
        System.out.println("BookController: findBookById(" + bid + ")");
        return service.getBookById(bid);
    }
}
