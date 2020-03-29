package com.wuxiu.forum.controllser;

import com.wuxiu.forum.dto.NotificationDTO;
import com.wuxiu.forum.enums.NotificationTypeEnum;
import com.wuxiu.forum.exception.CustomizeErrorCode;
import com.wuxiu.forum.mapper.NotificationMapper;
import com.wuxiu.forum.model.User;
import com.wuxiu.forum.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String readNotification(Model model , HttpServletRequest request, @PathVariable(name = "id")Long id){
        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            model.addAttribute("error", CustomizeErrorCode.NOT_LOGIN);
            return "redirect:/";
        }
        NotificationDTO notificationDTO  = notificationService.read(id,user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterId();
        }else {
            return "redirect:/";
        }
    }
}
