package com.wuxiu.forum.dto;

import lombok.Data;

/**
 * 封装github用户信息
 */
@Data
public class GithubUser {

    private Long id;
    private String bio;
    private String name;
    private String avatarUrl;
}
