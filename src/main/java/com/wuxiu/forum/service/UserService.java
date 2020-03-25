package com.wuxiu.forum.service;

import com.wuxiu.forum.mapper.UserMapper;
import com.wuxiu.forum.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User userDb = userMapper.findUserByAccountId(user.getAccountId());
        if (userDb==null){

            user.setGtmCreate(System.currentTimeMillis());
            user.setGtmModified(user.getGtmCreate());
            userMapper.insert(user);
        }else {
            userDb.setGtmModified(System.currentTimeMillis());
            userDb.setAvatarUrl(user.getAvatarUrl());
            userDb.setName(user.getName());
            userDb.setToken(userDb.getToken());
            userMapper.update(userDb);
        }
    }
}
