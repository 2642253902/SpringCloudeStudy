package org.example.service;

import jakarta.annotation.Resource;
import org.example.entity.UserBorrowDetail;

public interface BorrowService {

    UserBorrowDetail getUserBorrowDetailByUid(int uid);

    public boolean doBorrow(int uid, int bid);
}
