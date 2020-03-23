package com.wuxiu.forum.model;

import lombok.Data;

@Data
public class Question {

    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private String tag;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
}
