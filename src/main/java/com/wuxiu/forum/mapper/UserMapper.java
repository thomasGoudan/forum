package com.wuxiu.forum.mapper;

import com.wuxiu.forum.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO  user (NAME,ACCOUNT_ID,TOKEN,GTM_CREATE,GTM_MODIFIED) VALUES (#{name},#{accountId},#{token},#{gtmCreate},#{gtmModified})")
    void insert(User user);
}
