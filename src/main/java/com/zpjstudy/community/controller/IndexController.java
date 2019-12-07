package com.zpjstudy.community.controller;

import com.zpjstudy.community.Service.QuestionService;
import com.zpjstudy.community.dto.PaginationDTO;
import com.zpjstudy.community.mapper.UserMapper;
import com.zpjstudy.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {


    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size
                        ){



        PaginationDTO pagination = questionService.list(page,size);

        model.addAttribute("pagination",pagination);
        return "index";

    }

}
