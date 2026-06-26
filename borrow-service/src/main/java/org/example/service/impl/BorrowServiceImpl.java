package org.example.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import jakarta.annotation.Resource;
import org.example.entiey.Book;
import org.example.entiey.Borrow;
import org.example.entiey.User;
import org.example.entity.UserBorrowDetail;
import org.example.mapper.BorrowMapper;
import org.example.service.BorrowService;
import org.example.service.client.BookClient;
import org.example.service.client.UserClient;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowServiceImpl implements BorrowService {

    @Resource
    BorrowMapper mapper;

    @Resource
    UserClient userClient;

    @Resource
    BookClient bookClient;

    /* 监控此方法，无论被谁执行都在监控范围内，这里给的value是自定义名称，
    这个注解可以加在任何方法上，包括Controller中的请求映射方法，跟HystrixCommand贼像 */
    @SentinelResource(value = "getBorrow", blockHandler = "blocked")
    @Override
    public UserBorrowDetail getUserBorrowDetailByUid(int uid) {
        List<Borrow> borrow = mapper.getBorrowsByUid(uid);
        //获取User信息
        User user = userClient.getUserById(uid);

        //获取每一本书的详细信息
        List<Book> bookList = borrow.stream()
                .map(b -> bookClient.getBookById(b.getBid()))
                .collect(Collectors.toList());
        return new UserBorrowDetail(user, bookList);
    }

    //替代方案，注意参数和返回值需要保持一致，并且参数最后还需要额外添加一个BlockException
    public UserBorrowDetail blocked(int uid, BlockException e) {
        return new UserBorrowDetail(null, Collections.emptyList());
    }

}
