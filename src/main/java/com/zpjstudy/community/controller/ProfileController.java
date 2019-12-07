package com.zpjstudy.community.controller;


import com.zpjstudy.community.Service.QuestionService;
import com.zpjstudy.community.dto.PaginationDTO;
import com.zpjstudy.community.mapper.UserMapper;
import com.zpjstudy.community.model.Question;
import com.zpjstudy.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action")String action,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size,
                          Model model){
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }
        else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }

//        User user=null;
//        Cookie[] cookies = request.getCookies();
//        if(cookies!=null&&cookies.length!=0)
//            for (Cookie cookie : cookies) {
//                if(cookie.getName().equals("token")){
//                    String token = cookie.getValue();
//                    user = userMapper.findByToken(token);
//                    if(user != null){
//                        request.getSession().setAttribute("user",user);
//                    }
//                    break;
//                }
//            }
        User user = (User)request.getSession().getAttribute("user");
        if(user == null)
            return "redirect:/";
        PaginationDTO paginationDTO =questionService.list(user.getId(),page,size);


        model.addAttribute("pagination",paginationDTO);
        return "profile";
    }
}
