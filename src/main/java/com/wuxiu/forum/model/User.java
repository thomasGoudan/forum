package com.wuxiu.forum.model;

import lombok.Data;

/**
 * 用户信息
 */
@Data
public class User {

    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gtmCreate;
    private Long gtmModified;
    private String avatarUrl;
}
