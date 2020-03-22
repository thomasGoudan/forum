package com.wuxiu.forum.controllser;

import com.wuxiu.forum.dto.AccessTokenDTO;
import com.wuxiu.forum.dto.GithubUser;
import com.wuxiu.forum.mapper.UserMapper;
import com.wuxiu.forum.model.User;
import com.wuxiu.forum.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String clientRedirectURL;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,@RequestParam(name = "state")String state,
                            HttpServletRequest request ){
        AccessTokenDTO dto = new AccessTokenDTO();
        dto.setClient_id(clientId);
        dto.setClient_secret(clientSecret);
        dto.setCode(code);
        dto.setState(state);
        dto.setRedirect_uri(clientRedirectURL);
        String accessToken = githubProvider.getAccessToken(dto);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null){
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGtmCreate(System.currentTimeMillis());
            user.setGtmModified(user.getGtmCreate());
            userMapper.insert(user);
            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else {
            return"redirect:/";
        }
    }
}
