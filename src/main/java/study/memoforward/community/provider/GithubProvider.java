package study.memoforward.community.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import study.memoforward.community.dto.AccessTokenDTO;
import study.memoforward.community.dto.GithubUser;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO atd){
        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        String json = com.alibaba.fastjson.JSON.toJSONString(atd);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            // 返回了一个access_token相关的键值对：access_token=***&scope=***&token_type=bearer
            String s = response.body().string();
            // 希望得到access_token的值，因此要对字符串进行拆分
            String access_token = s.split("&")[0].split("=")[1];
            return access_token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String s = response.body().string();
            // JSON能够自动把json中的键值对一一对应到java类的属性上
            GithubUser githubUser = JSON.parseObject(s, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
