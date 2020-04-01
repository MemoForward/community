package study.memoforward.community.service;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.memoforward.community.dto.CommentDTO;
import study.memoforward.community.dto.PageDTO;
import study.memoforward.community.dto.ResultDTO;
import study.memoforward.community.enums.CommentTypeEnum;
import study.memoforward.community.exception.CustomizeError;
import study.memoforward.community.exception.CustomizeException;
import study.memoforward.community.mapper.*;
import study.memoforward.community.model.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    @Autowired
    CommentExtMapper commentExtMapper;

    @Transactional
    public ResultDTO insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            return ResultDTO.errorOf(CustomizeError.HIT_NO_TARGET);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            return ResultDTO.errorOf(CustomizeError.COMMENT_TYPE_NOT_EXIST);
        }else if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            // 回复评论
            Comment parentComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(parentComment == null){
                return ResultDTO.errorOf(CustomizeError.COMMENT_NOT_EXIST);
            }
            int i = commentMapper.insert(comment);
            if(i == 0) return ResultDTO.errorOf(CustomizeError.COMMENT_NOT_GOOD);
            parentComment.setCommentCount(1l);
            commentExtMapper.incCommentCount(parentComment);
        }else if(comment.getType() == CommentTypeEnum.QUESTION.getType()){
            // 回复问题
            Question parentQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(parentQuestion == null){
                return ResultDTO.errorOf(CustomizeError.QUESTION_NOT_FOUND);
            }
            int i = commentMapper.insert(comment);
            if(i == 0) return ResultDTO.errorOf(CustomizeError.COMMENT_NOT_GOOD);
            parentQuestion.setCommentCount(1);
            questionExtMapper.incCommentCount(parentQuestion);
        }
        return ResultDTO.success();
    }

    public PageDTO<CommentDTO> getCommentList(Long id, Integer page, Integer pageLimit, CommentTypeEnum type) {
        if(id == null) throw new CustomizeException(CustomizeError.HIT_NO_TARGET);
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().
                andParentIdEqualTo(id).andTypeEqualTo(type.getType());
        Integer totalNum = (int)commentMapper.countByExample(commentExample);
        Integer totalPage = totalNum % pageLimit == 0 ? totalNum / pageLimit : totalNum / pageLimit + 1;
        if(page < 1) page = 1;
        if(page > totalPage) page = totalPage;
        Integer offset = (page - 1) * pageLimit;
        commentExample = new CommentExample();
        // 默认先降序吧
        commentExample.setOrderByClause("gmt_modified desc");
        commentExample.createCriteria().
                andParentIdEqualTo(id).andTypeEqualTo(type.getType());
        List<Comment> commentList = commentMapper.selectByExampleWithRowbounds(commentExample, new RowBounds(offset, pageLimit));
        List<CommentDTO> commentDTOList = new ArrayList<>();
        PageDTO<CommentDTO> pageDTO = new PageDTO<>();
        for(Comment comment: commentList){
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            User user = userMapper.selectByPrimaryKey(comment.getCommentator());
            commentDTO.setUser(user);
            commentDTOList.add(commentDTO);
        }
        pageDTO.setList(commentDTOList);
        pageDTO.setPage(page, totalPage);
        return pageDTO;
    }
}
