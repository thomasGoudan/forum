package com.wuxiu.forum.dto;

import lombok.Data;

/**
 * 数据传输对象
 * 页面传递过来的
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;

}
