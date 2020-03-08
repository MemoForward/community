package study.memoforward.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.memoforward.community.dto.AccessTokenDTO;
import study.memoforward.community.dto.GithubUser;
import study.memoforward.community.mapper.UserMapper;
import study.memoforward.community.model.User;
import study.memoforward.community.provider.GithubProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String getCode(@RequestParam(name = "code") String code,
                          @RequestParam(name = "state") String state,
                          HttpServletRequest request) {
        // 2. 接受到github返回的code，并构建参数列表，向github请求(POST)一个access_token
        AccessTokenDTO atd = new AccessTokenDTO();
        atd.setClient_id(clientId);
        atd.setClient_secret(clientSecret);
        atd.setCode(code);
        atd.setRedirect_url(redirectUri);
        atd.setState(state);
        String access_token = githubProvider.getAccessToken(atd);
        // 3.通过access_token获得github用户的信息并在数据库保存
        GithubUser githubUser = githubProvider.getUser(access_token);
        if (githubUser != null) {
            request.getSession().setAttribute("user", githubUser);
            User user = new User();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            //登录成功，写cookie和session
            return "redirect:/";
        } else {
            //登录失败
            return null;
        }
    }
}
