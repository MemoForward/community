package study.memoforward.community.service;

import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public List<QuestionDTO> getList() {
        List<Question> questionList = questionMapper.getList();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(Question q: questionList){
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            int userId = q.getCreator();
            User user = userMapper.getById(userId);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
