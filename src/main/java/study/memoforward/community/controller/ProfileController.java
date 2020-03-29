package study.memoforward.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import study.memoforward.community.dto.PageDTO;
import study.memoforward.community.exception.CustomizeError;
import study.memoforward.community.exception.CustomizeException;
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
                          @RequestParam(value = "id", required = false) Integer id,
                          @RequestParam(value = "page", defaultValue = "1") Integer page){
        User user = null;
        if(id == null){
            user = (User)request.getSession().getAttribute("user");
        }else{
            user = userService.findById(id);
        }
        if(user == null) {throw new CustomizeException(CustomizeError.USER_NOT_LOGIN);}

        if("question".equals(action)){
            model.addAttribute("section", "question");
            model.addAttribute("sectionName", user.getName() + "的提问");
            PageDTO pageDTO = questionService.getList(user, page, PAGELIMIT);
            model.addAttribute("id", user.getId());
            model.addAttribute("pageDTO", pageDTO);
            return "profile";
        }else if("reply".equals(action)){
            model.addAttribute("section", "reply");
            model.addAttribute("sectionName","最新回复");
            PageDTO pageDTO = null;
            model.addAttribute("pageDTO", pageDTO);
            return "profile";
        }
        throw new CustomizeException(CustomizeError.URL_NOT_FOUND);
    }
}
