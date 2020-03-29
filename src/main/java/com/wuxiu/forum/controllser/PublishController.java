package com.wuxiu.forum.controllser;

import com.wuxiu.forum.cache.TagCache;
import com.wuxiu.forum.exception.CustomizeErrorCode;
import com.wuxiu.forum.mapper.QuestionMapper;
import com.wuxiu.forum.model.Question;
import com.wuxiu.forum.model.User;
import com.wuxiu.forum.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
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
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }


    /**
     * 发布问题
     * @param title
     * @param describe
     * @param tag
     * @param questionId
     * @param request
     * @param model
     * @return
     */
    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("describe") String describe,
                            @RequestParam("tag") String tag,
                            @RequestParam(value = "questionId", required = false) Long questionId,
                            HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", CustomizeErrorCode.NOT_LOGIN);
            return "publish";
        }
        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNoneBlank(invalid)){
            model.addAttribute("error","标签输入错误"+invalid);
            return "publish";
        }

        model.addAttribute("title", title);
        model.addAttribute("describe", describe);
        model.addAttribute("tag", tag);

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(describe);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(questionId);
        questionService.createOrUpdate(question);
        model.addAttribute("tags", TagCache.get());
        return "redirect:/";
    }

    /**
     * 查看发布问题
     * @param questionId
     * @param model
     * @return
     */
    @GetMapping("/publish/{questionId}")
    public String edit(@PathVariable("questionId") Long questionId, Model model) {
        Question question = questionService.getQuestionById(questionId);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("describe", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("questionId", question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
}
