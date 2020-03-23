package com.wuxiu.forum.controllser;

import com.wuxiu.forum.mapper.UserMapper;
import com.wuxiu.forum.model.PaginationDTO;
import com.wuxiu.forum.model.User;
import com.wuxiu.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class mainController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "pageIndex",defaultValue = "1")Integer pageIndex,
                        @RequestParam(name = "pagiSize",defaultValue = "5")Integer pageSize){
        //获取客户端cookie,在数据库匹配
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return "index";
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")){
                String tokenValue = cookie.getValue();
                User user = userMapper.findUserByToken(tokenValue);
                if(user != null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        PaginationDTO paginationDTO = questionService.getList(pageIndex,pageSize);
        model.addAttribute("paginationDTO",paginationDTO);
        return "index";
    }


}
