package study.memoforward.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.memoforward.community.dto.QuestionDTO;
import study.memoforward.community.exception.CustomizeError;
import study.memoforward.community.exception.CustomizeException;
import study.memoforward.community.model.Question;
import study.memoforward.community.model.User;
import study.memoforward.community.service.QuestionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PublishController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id,
                       HttpServletRequest request,
                       Model model){

        // 在后端检验恶意修改数据，防止有人直接通过url修改问题
        Question question = questionService.findById(id);
        User user = (User)request.getSession().getAttribute("user");
        if(user == null) throw new CustomizeException(CustomizeError.URL_NOT_FOUND);
        if(!user.getId().equals(question.getCreator())) throw new CustomizeException(CustomizeError.USER_NO_PERMISSION);

        // 执行修改逻辑
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", id);
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            @RequestParam(value = "id", required = false) Long id,
                            HttpServletRequest request,
                            Model model) {

        // 发布内容记录
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        // 发布是否合理
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        if (title == null || "".equals(title)) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || "".equals(description)) {
            model.addAttribute("error", "正文不能为空");
            return "publish";
        }
        if (tag == null || "".equals(tag)) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        if(id == null){
            // 发布逻辑
            Question question = new Question();
            question.setCreator(user.getId());
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            questionService.create(question);
            return "redirect:/";
        }else{
            Question question = questionService.findById(id);
            if(!user.getId().equals(question.getCreator()))
                throw new CustomizeException((CustomizeError.USER_NO_PERMISSION));
            question.setGmtModified(System.currentTimeMillis());
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            questionService.edit(question);
        }return "redirect:/";

    }
}
