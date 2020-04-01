package study.memoforward.community.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.memoforward.community.dto.CommentDTO;
import study.memoforward.community.dto.PageDTO;
import study.memoforward.community.dto.ResultDTO;
import study.memoforward.community.enums.CommentTypeEnum;
import study.memoforward.community.exception.CustomizeError;
import study.memoforward.community.model.Comment;
import study.memoforward.community.model.User;
import study.memoforward.community.service.CommentService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @Value("${comment.second.showCounts}")
    private Integer SECONDCOMMENTSLIMIT;

    @ResponseBody
    @PostMapping(value = "/comment")
    public ResultDTO comment(@RequestBody CommentDTO commentDTO,
                          HttpServletRequest request) {
        if(commentDTO == null || StringUtils.isBlank(commentDTO.getContent())){
            return ResultDTO.errorOf(CustomizeError.COMMENT_CANNOT_BE_NULL);
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeError.USER_RELOGIN_IN);
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0l);
        return commentService.insert(comment);
    }


    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResultDTO<PageDTO<CommentDTO>> showSecondComments(@PathVariable("id") Long id,
                                        @RequestParam(value = "page",defaultValue = "1") Integer page){
        PageDTO<CommentDTO> pageDTO = commentService.getCommentList(id, page, SECONDCOMMENTSLIMIT, CommentTypeEnum.COMMENT);
        return ResultDTO.success(pageDTO);
    }

}
