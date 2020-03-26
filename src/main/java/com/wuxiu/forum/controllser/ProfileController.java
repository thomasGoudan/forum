package com.wuxiu.forum.controllser;

import com.wuxiu.forum.dto.PaginationDTO;
import com.wuxiu.forum.model.User;
import com.wuxiu.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 个人信息主页
 */
@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{active}")
    public String userQuestionList(HttpServletRequest request, @PathVariable("active") String active,
                                   Model model,
                                   @RequestParam(name = "pageIndex",defaultValue = "1")Integer pageIndex,
                                   @RequestParam(name = "pageSize",defaultValue = "5")Integer pageSize){
        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            model.addAttribute("error","用户未登录");
            return "redirect:/";
        }
        if ("questions".equals(active)) {
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if ("replies".equals(active)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        PaginationDTO paginationDTO = questionService.list(user.getId(), pageIndex, pageSize);
        model.addAttribute("paginationDTO",paginationDTO);
        return "profile";
    }


}
