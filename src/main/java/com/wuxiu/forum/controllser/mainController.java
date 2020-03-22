package com.wuxiu.forum.controllser;

import com.wuxiu.forum.mapper.UserMapper;
import com.wuxiu.forum.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class mainController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request){
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
        return "index";
    }


}
