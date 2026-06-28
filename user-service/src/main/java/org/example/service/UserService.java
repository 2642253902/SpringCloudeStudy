package org.example.service;


import org.example.entiey.User;

public interface UserService {
    User getUserById(int uid);

    public int getRemain(int uid);

    public boolean setRemain(int uid, int count);
}

