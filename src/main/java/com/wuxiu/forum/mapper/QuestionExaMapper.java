package com.wuxiu.forum.mapper;

import com.wuxiu.forum.model.Question;

public interface QuestionExaMapper {
    //阅读数
    int incView(Question question);
    //评论数
    int incCommentCount(Question question);

}