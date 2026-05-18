package org.example.service.client;

import org.example.entiey.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("bookservice")
public interface BookClient {

    @RequestMapping("/book/{bid}")
    Book findBookById(@PathVariable("bid") int bid);


}
