package com.wuxiu.forum.service;

import com.wuxiu.forum.mapper.UserMapper;
import com.wuxiu.forum.model.User;
import com.wuxiu.forum.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        //拼接创建查询
        UserExample userExampleAndAccountId= new UserExample();
        userExampleAndAccountId.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExampleAndAccountId);
        if (users.size() == 0) {
            user.setGtmCreate(System.currentTimeMillis());
            user.setGtmModified(user.getGtmCreate());
            userMapper.insertSelective(user);
        } else {
            User userDb = users.get(0);
            User updateUser = new User();
            updateUser.setGtmModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            //创建拼接查询
            UserExample userExampleAndId = new UserExample();
            userExampleAndId.createCriteria().andIdEqualTo(userDb.getId());
            //跟新数据
            userMapper.updateByExampleSelective(updateUser, userExampleAndId);
        }
    }
}
