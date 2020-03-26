package com.wuxiu.forum.controllser;

import com.wuxiu.forum.dto.PaginationDTO;
import com.wuxiu.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request,
                        @RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                        @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        PaginationDTO paginationDTO = questionService.getList(pageIndex, pageSize);
        model.addAttribute("paginationDTO", paginationDTO);
        return "index";
    }


}
