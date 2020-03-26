package study.memoforward.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import study.memoforward.community.dto.QuestionDTO;
import study.memoforward.community.model.User;
import study.memoforward.community.service.QuestionService;
import study.memoforward.community.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model){
        Cookie[] cookies = request.getCookies();
        if(cookies == null) return "index";
        for(Cookie cookie: cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                User user = userService.findByToken(token);
                if(user != null){
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }

        List<QuestionDTO> questionDTOList =  questionService.getList();
        model.addAttribute("questionLists", questionDTOList);
        return "index";
    }
}
