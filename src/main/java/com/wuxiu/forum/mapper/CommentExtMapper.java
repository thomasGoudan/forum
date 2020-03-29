package com.wuxiu.forum.mapper;

import com.wuxiu.forum.model.Comment;

public interface CommentExtMapper {
    //评论数
    int incCommentCount(Comment comment);
}