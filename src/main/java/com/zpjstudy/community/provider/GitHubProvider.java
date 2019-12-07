package com.zpjstudy.community.provider;

import com.alibaba.fastjson.JSON;
import com.zpjstudy.community.dto.AccessTokenDTO;
import com.zpjstudy.community.dto.GithubUser;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubProvider {
    private final Logger logger= LoggerFactory.getLogger(GitHubProvider.class);
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        System.out.println(JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String a = response.body().string();
        //    System.out.println("888888");
            System.out.println(a);
            return a;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public GithubUser getuser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://api.github.com/user?"+accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String a = response.body().string();
            GithubUser githubUser = JSON.parseObject(a, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            logger.error("出错啦",e);
        }
        return null;
    }
}
