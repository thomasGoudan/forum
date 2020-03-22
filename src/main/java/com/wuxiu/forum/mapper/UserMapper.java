package com.wuxiu.forum.mapper;

import com.wuxiu.forum.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * 实例化用户
     * @param user
     */
    @Insert("INSERT INTO  user (NAME,ACCOUNT_ID,TOKEN,GTM_CREATE,GTM_MODIFIED) VALUES (#{name},#{accountId},#{token},#{gtmCreate},#{gtmModified})")
    void insert(User user);

    /**
     * 通过tokenc查找用户
     * @param tokenValue
     * @return
     */
    @Select("SELECT  * FROM user WHERE token = #{token}")
    User findUserByToken(@Param("token") String tokenValue);
}
