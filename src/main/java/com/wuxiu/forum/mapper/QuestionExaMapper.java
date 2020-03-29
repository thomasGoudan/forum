package com.wuxiu.forum.mapper;

import com.wuxiu.forum.model.Question;

import java.util.List;

public interface QuestionExaMapper {
    //阅读数
    int incView(Question question);
    //评论数
    int incCommentCount(Question question);
    //相关问题查询
    List<Question> selectRelated(Question question);

}