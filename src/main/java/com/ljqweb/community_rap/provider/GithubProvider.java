package com.ljqweb.community_rap.provider;

import com.alibaba.fastjson.JSON;
import com.ljqweb.community_rap.dto.AccessTokenDTO;
import com.ljqweb.community_rap.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO),mediaType);
        Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token?client_id=69b77bd8543e279d2c5c&client_secret=384878fa2edaf5b882cfc7729b355d4b246629ab&code="+accessTokenDTO.getClient_code()+"&redirect_uri=http://localhost:8887/callback&state=1")
                    .post(body)
                    .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        }catch (IOException e){
        }
        return null;

    }
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            System.out.println(string);
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {

        }
        return null;
    }
}
