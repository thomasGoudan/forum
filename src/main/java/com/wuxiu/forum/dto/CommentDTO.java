package com.wuxiu.forum.dto;

import com.wuxiu.forum.model.User;
import lombok.Data;

/**
 * 实体对象的数据传递对象
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private User user;


}
