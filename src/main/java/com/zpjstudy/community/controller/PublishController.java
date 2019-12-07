package com.zpjstudy.community.controller;

import com.zpjstudy.community.mapper.QuestionMapper;
import com.zpjstudy.community.mapper.UserMapper;
import com.zpjstudy.community.model.Question;
import com.zpjstudy.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.text.normalizer.NormalizerBase;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.stream.StreamSupport;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;


    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }


    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title")String title,
            @RequestParam("description")String description,
            @RequestParam("tag")String tag,
            HttpServletRequest request,
            Model model
    ){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);


        if(title==null||title==""){
            model.addAttribute("error","标题不能空");
            return "/publish";
        }
        if(description==null||description==""){
            model.addAttribute("error","补充不能空");
            return "/publish";
        }
        if(tag==null||tag==""){
            model.addAttribute("error","标签不能空");
            return "/publish";
        }

        User user = (User)request.getSession().getAttribute("user");

        if(user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtModified(System.currentTimeMillis());
        question.setGmtCreate(System.currentTimeMillis());

        questionMapper.create(question);
        return "redirect:/";
    }
}
