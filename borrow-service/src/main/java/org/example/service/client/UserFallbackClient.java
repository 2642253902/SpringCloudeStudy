package org.example.service.client;

import org.example.entiey.User;
import org.springframework.stereotype.Component;

@Component   //注意，需要将其注册为Bean，Feign才能自动注入
public class UserFallbackClient implements UserClient {

    @Override
    public User findUserById(int uid) {
        User user = new User();
        user.setName("我是替代方案");
        return user;
    }
}
