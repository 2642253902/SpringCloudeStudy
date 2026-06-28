package org.example.service.impl;

import jakarta.annotation.Resource;
import org.example.entiey.User;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper mapper;

    @Override
    public User getUserById(int uid) {
        return mapper.getUserById(uid);
    }

    @Override
    public int getRemain(int uid) {
        return mapper.getUserBookRemain(uid);
    }

    @Override
    public boolean setRemain(int uid, int count) {
        return mapper.updateBookCount(uid, count) > 0;
    }

}
