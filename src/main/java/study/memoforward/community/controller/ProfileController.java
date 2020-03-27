package study.memoforward.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import study.memoforward.community.dto.PageDTO;
import study.memoforward.community.model.User;
import study.memoforward.community.service.QuestionService;
import study.memoforward.community.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    @Value("${index.showCounts}")
    private Integer PAGELIMIT;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          HttpServletRequest request,
                          Model model,
                          @RequestParam(value = "page", defaultValue = "1") Integer page){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("error","用户未登录");
            return "404";
        }
        if("question".equals(action)){
            model.addAttribute("section", "question");
            model.addAttribute("sectionName", "我的提问");
            return "forward:/profile/getQues";
        }else if("reply".equals(action)){
            model.addAttribute("section", "reply");
            model.addAttribute("sectionName","最新回复");
            return "forward:/profile/getRep";
        }else if("getQues".equals(action)){
            PageDTO pageDTO = questionService.getList(user, page, PAGELIMIT);
            model.addAttribute("pageDTO", pageDTO);
            return "profile";
        }else if("getRep".equals(action)){
            PageDTO pageDTO = null;
            model.addAttribute("pageDTO", pageDTO);
            return "profile";
        }
        return "404";
    }
}
