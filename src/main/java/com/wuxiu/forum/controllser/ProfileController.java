package com.wuxiu.forum.controllser;

import com.wuxiu.forum.dto.PaginationDTO;
import com.wuxiu.forum.exception.CustomizeErrorCode;
import com.wuxiu.forum.mapper.NotificationMapper;
import com.wuxiu.forum.model.User;
import com.wuxiu.forum.service.NotificationService;
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
    @Autowired
    private NotificationService notificationService;

    /**
     * 个人问题和个人回复列表
     * @param request
     * @param active
     * @param model
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/profile/{active}")
    public String userProfileList(HttpServletRequest request, @PathVariable("active") String active,
                                   Model model,
                                   @RequestParam(name = "pageIndex",defaultValue = "1")Integer pageIndex,
                                   @RequestParam(name = "pageSize",defaultValue = "5")Integer pageSize){
        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            model.addAttribute("error", CustomizeErrorCode.NOT_LOGIN);
            return "redirect:/";
        }
        if ("questions".equals(active)) {
            PaginationDTO paginationDTO = questionService.list(user.getId(), pageIndex, pageSize);
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            model.addAttribute("paginationDTO",paginationDTO);
        }else if ("replies".equals(active)){
            PaginationDTO paginationDTO = notificationService.list(user.getId(),pageIndex,pageSize);
            Long unreadCount = notificationService.unreadCount(user.getId());
            model.addAttribute("paginationDTO",paginationDTO);
            model.addAttribute("unreadCount",unreadCount);
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");

        }

        return "profile";
    }


}
