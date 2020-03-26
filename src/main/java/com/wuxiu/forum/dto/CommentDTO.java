package com.wuxiu.forum.dto;

import lombok.Data;

/**
 * 数据传输对象
 */
@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;

}
