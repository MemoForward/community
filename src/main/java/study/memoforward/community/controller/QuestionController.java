package study.memoforward.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import study.memoforward.community.dto.CommentDTO;
import study.memoforward.community.dto.PageDTO;
import study.memoforward.community.dto.QuestionDTO;
import study.memoforward.community.enums.CommentTypeEnum;
import study.memoforward.community.model.Question;
import study.memoforward.community.service.CommentService;
import study.memoforward.community.service.QuestionService;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Value("${comment.showCounts}")
    private Integer COMMENTPAGELIMIT;

    private Integer RELATEDQUESTIONPAGELIMIT;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                           Model model){
        // 增加阅读数
        questionService.incViewCount(id);
        QuestionDTO questionDTO = questionService.getById(id);
        List<Question> relatedQuestions = questionService.getRelatedQuestions(questionDTO);
        PageDTO<CommentDTO> commentPageDTO = commentService.getCommentList(id, page, COMMENTPAGELIMIT, CommentTypeEnum.QUESTION);
        model.addAttribute("questionDTO", questionDTO);
        model.addAttribute("pageDTO", commentPageDTO);
        model.addAttribute("relatedQuestions", relatedQuestions);
//        System.out.println(pageDTO);
        return "question";
    }
}
