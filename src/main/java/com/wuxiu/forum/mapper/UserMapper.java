package com.wuxiu.forum.mapper;

import com.wuxiu.forum.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    /**
     * 实例化用户
     * @param user
     */
    @Insert("INSERT INTO  user (NAME,ACCOUNT_ID,TOKEN,GTM_CREATE,GTM_MODIFIED,AVATAR_URL) VALUES (#{name},#{accountId},#{token},#{gtmCreate},#{gtmModified},#{avatarUrl})")
    void insert(User user);

    /**
     * 通过tokenc查找用户
     * @param tokenValue
     * @return
     */
    @Select("SELECT  * FROM user WHERE token = #{token}")
    User findUserByToken(@Param("token") String tokenValue);

    /**
     * 通过id获取用户
     * @param creator
     * @return
     */
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findUserById(@Param("id") Integer creator);

    /**
     * 通过用户登录账户id获取用户
     * @param accountId
     * @return
     */
    @Select("SELECT * FROM user WHERE account_id = #{accountId}")
    User findUserByAccountId(@Param("accountId")String accountId);

    /**
     * 用户更新
     * @param userDb
     */
    @Update("UPDATE user SET ACCOUNT_ID=#{accountId},NAME=#{name},TOKEN=#{token},GTM_MODIFIED=#{gtmModified},AVATAR_URL=#{avatarUrl} WHERE id = #{id}")
    void update(User userDb);
}
