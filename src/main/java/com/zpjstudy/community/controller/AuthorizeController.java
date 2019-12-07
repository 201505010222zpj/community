package com.zpjstudy.community.controller;

import com.zpjstudy.community.dto.AccessTokenDTO;
import com.zpjstudy.community.dto.GithubUser;
import com.zpjstudy.community.mapper.UserMapper;
import com.zpjstudy.community.model.User;
import com.zpjstudy.community.provider.GitHubProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import javax.xml.ws.Response;
import java.util.UUID;

@Controller
public class AuthorizeController  {
    private final Logger   logger= LoggerFactory.getLogger(AuthorizeController.class);
    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletResponse response
                           ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String string = gitHubProvider.getAccessToken(accessTokenDTO);
        String[] split=string.split("&");
        GithubUser githubUser =gitHubProvider.getuser(split[0]);
        if(githubUser != null){
            //登录成功，写cookie session
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            user.setAvatarUrl(githubUser.getAvatar_url());
            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));
        //    request.getSession().setAttribute("user",githubUser);
            /*在request里面加一个session是服务器做的，这样当服务器区解析html的时候，会取出session，
            而取出这个session的cookie是jsessonid，是框架自带的
            * */
            return "redirect:/";
        }
        else {
            return "redirect:/";
        }
    }


}
