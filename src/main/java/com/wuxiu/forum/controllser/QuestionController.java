package com.wuxiu.forum.controllser;

import com.wuxiu.forum.dto.CommentCreateDTO;
import com.wuxiu.forum.dto.CommentDTO;
import com.wuxiu.forum.dto.QuestionDTO;
import com.wuxiu.forum.service.CommentService;
import com.wuxiu.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 问题详情页
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{userQuestionId}")
    public String userQuestionDetail(@PathVariable("userQuestionId") Long userQuestionId, Model model){
        QuestionDTO questionDTO = questionService.getUserQuestionByQuestionId(userQuestionId);
        List<CommentDTO> commentDTOList = commentService.listByQuestionId(userQuestionId);
        //添加阅读数
        questionService.incView(userQuestionId);
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("commentDTOList",commentDTOList);
        return "question";
    }
}
