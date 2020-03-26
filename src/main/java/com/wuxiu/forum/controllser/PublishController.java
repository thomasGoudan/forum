package com.wuxiu.forum.controllser;

import com.wuxiu.forum.mapper.QuestionMapper;
import com.wuxiu.forum.model.Question;
import com.wuxiu.forum.model.User;
import com.wuxiu.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户问题
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }


    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("describe") String describe,
                            @RequestParam("tig") String tig,
                            @RequestParam(value = "questionId", required = false) Integer questionId,
                            HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        model.addAttribute("title", title);
        model.addAttribute("describe", describe);
        model.addAttribute("tig", tig);

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(describe);
        question.setTag(tig);
        question.setCreator(user.getId());
        question.setId(questionId);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }

    @GetMapping("/publish/{questionId}")
    public String edit(@PathVariable("questionId") Integer questionId, Model model) {
        Question question = questionService.getQuestionById(questionId);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("describe", question.getDescription());
        model.addAttribute("tig", question.getTag());
        model.addAttribute("questionId", question.getId());
        return "publish";
    }
}
