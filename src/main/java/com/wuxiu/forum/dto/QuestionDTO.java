package com.wuxiu.forum.dto;

import com.wuxiu.forum.model.User;
import lombok.Data;

@Data
public class QuestionDTO {

    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private String tag;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private User user;
}
