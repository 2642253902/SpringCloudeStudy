package org.example.service.impl;

import jakarta.annotation.Resource;
import org.apache.seata.spring.annotation.GlobalTransactional;
import org.example.entiey.Book;
import org.example.entiey.Borrow;
import org.example.entiey.User;
import org.example.entity.UserBorrowDetail;
import org.example.mapper.BorrowMapper;
import org.example.service.BorrowService;
import org.example.service.client.BookClient;
import org.example.service.client.UserClient;
import org.springframework.stereotype.Service;

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

    @GlobalTransactional
    @Override
    public boolean doBorrow(int uid, int bid) {
        //1. 判断图书和用户是否都支持借阅
        if (bookClient.bookRemain(bid) < 1) {
            throw new RuntimeException("图书数量不足");
        }
        if (userClient.userRemain(uid) < 1) {
            throw new RuntimeException("用户借阅量不足");
        }
        //2. 首先将图书的数量-1
        if (!bookClient.bookBorrow(bid)) {
            throw new RuntimeException("在借阅图书时出现错误！");
        }
        //3. 添加借阅信息
        if (mapper.getBorrow(uid, bid) != null) {
            throw new RuntimeException("此书籍已经被此用户借阅了！");
        }
        if (mapper.addBorrow(uid, bid) <= 0) {
            throw new RuntimeException("在录入借阅信息时出现错误！");
        }
        //4. 用户可借阅-1
        if (!userClient.userBorrow(uid)) {
            throw new RuntimeException("在借阅时出现错误！");
        }
        //完成
        return true;
    }


}
