package org.example.service;

import jakarta.annotation.Resource;
import org.example.entity.UserBorrowDetail;

public interface BorrowService {

    @Resource



    UserBorrowDetail getUserBorrowDetailByUid(int uid);
}
