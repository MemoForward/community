package study.memoforward.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.memoforward.community.dto.PageDTO;
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

    @Value("${index.showCounts}")
    private Integer PAGELIMIT;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        @RequestParam(value = "page", defaultValue = "1") Integer page){
        return "forward:/getPage";
    }

    @GetMapping("/getPage")
    public String addPage(Model model,
                          @RequestParam(value = "page", defaultValue = "1") Integer page){
        PageDTO<QuestionDTO> pageDTO =  questionService.getList(page, PAGELIMIT);
        model.addAttribute("pageDTO", pageDTO);
        return "index";
    }
}
