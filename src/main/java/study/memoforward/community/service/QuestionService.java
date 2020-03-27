package study.memoforward.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.memoforward.community.dto.PageDTO;
import study.memoforward.community.dto.QuestionDTO;
import study.memoforward.community.mapper.QuestionMapper;
import study.memoforward.community.mapper.UserMapper;
import study.memoforward.community.model.Question;
import study.memoforward.community.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    public void create(Question question){
        questionMapper.create(question);
    }

    public PageDTO getList(Integer page, Integer pageLimit) {
        Integer totalNum = questionMapper.count();
        Integer totalPage = totalNum % pageLimit == 0 ? totalNum / pageLimit : totalNum / pageLimit + 1;
        if(page < 1) page = 1;
        if(page > totalPage) page = totalPage;
        Integer offset = (page - 1) * pageLimit;
        List<Question> questionList = questionMapper.getList(offset, pageLimit);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        for(Question q: questionList){
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            int userId = q.getCreator();
            User user = userMapper.getById(userId);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestionDTOList(questionDTOList);
        pageDTO.setPage(page, totalPage);
        return pageDTO;
    }

    public PageDTO getList(User user, Integer page, Integer pageLimit) {
        Integer totalNum = questionMapper.countByUser(user);
        Integer totalPage = totalNum % pageLimit == 0 ? totalNum / pageLimit : totalNum / pageLimit + 1;
        if(page < 1) page = 1;
        if(page > totalPage) page = totalPage;
        Integer offset = (page - 1) * pageLimit;
        List<Question> questionList = questionMapper.getListByUser(user, offset, pageLimit);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        for(Question q: questionList){
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestionDTOList(questionDTOList);
        pageDTO.setPage(page, totalPage);
        return pageDTO;
    }
}
