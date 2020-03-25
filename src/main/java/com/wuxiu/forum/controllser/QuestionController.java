package com.wuxiu.forum.controllser;

import com.wuxiu.forum.dto.QuestionDTO;
import com.wuxiu.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 问题详情页
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{userQuestionId}")
    public String userQuestionDetail(@PathVariable("userQuestionId") Integer userQuestionId, Model model){
        QuestionDTO questionDTO = questionService.getUserQuestionByQuestionId(userQuestionId);
        model.addAttribute("questionDTO",questionDTO);
        return "question";
    }
}
