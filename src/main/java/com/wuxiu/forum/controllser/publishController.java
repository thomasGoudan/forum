package com.wuxiu.forum.controllser;

import com.wuxiu.forum.mapper.QuestionMapper;
import com.wuxiu.forum.mapper.UserMapper;
import com.wuxiu.forum.model.Question;
import com.wuxiu.forum.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class publishController {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }


    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("describe") String describe,
                            @RequestParam("tig") String tig,
                            HttpServletRequest request, Model model){
        User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return "index";
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")){
                String tokenValue = cookie.getValue();
                 user = userMapper.findUserByToken(tokenValue);
                if (user !=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        if (user ==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(describe);
        question.setTag(tig);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        return"redirect:/";
    }
}
