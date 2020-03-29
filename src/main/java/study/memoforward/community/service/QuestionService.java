package study.memoforward.community.service;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.memoforward.community.dto.PageDTO;
import study.memoforward.community.dto.QuestionDTO;
import study.memoforward.community.exception.CustomizeError;
import study.memoforward.community.exception.CustomizeException;
import study.memoforward.community.mapper.QuestionExtMapper;
import study.memoforward.community.mapper.QuestionMapper;
import study.memoforward.community.mapper.UserMapper;
import study.memoforward.community.model.Question;
import study.memoforward.community.model.QuestionExample;
import study.memoforward.community.model.User;
import study.memoforward.community.model.UserExample;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    @Autowired
    UserMapper userMapper;

    public void create(Question question){

        questionMapper.insert(question);
    }

    public PageDTO getList(Integer page, Integer pageLimit) {
        Integer totalNum = (int)questionMapper.countByExample(new QuestionExample());
        Integer totalPage = totalNum % pageLimit == 0 ? totalNum / pageLimit : totalNum / pageLimit + 1;
        if(page < 1) page = 1;
        if(page > totalPage) page = totalPage;
        Integer offset = (page - 1) * pageLimit;
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_modified desc");
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, pageLimit));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        for(Question q: questionList){
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            int userId = q.getCreator();
            User user = userMapper.selectByPrimaryKey(userId);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestionDTOList(questionDTOList);
        pageDTO.setPage(page, totalPage);
        return pageDTO;
    }

    public PageDTO getList(User user, Integer page, Integer pageLimit) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(user.getId());
        Integer totalNum = (int)questionMapper.countByExample(questionExample);
        Integer totalPage = totalNum % pageLimit == 0 ? totalNum / pageLimit : totalNum / pageLimit + 1;
        if(page < 1) page = 1;
        if(page > totalPage) page = totalPage;
        Integer offset = (page - 1) * pageLimit;
        questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_modified desc");
        questionExample.createCriteria().andCreatorEqualTo(user.getId());
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, pageLimit));
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

    public QuestionDTO getById(Integer id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null) throw new CustomizeException(CustomizeError.QUESTION_NOT_FOUND);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }


    public Question findById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null) throw new CustomizeException(CustomizeError.QUESTION_NOT_FOUND);
        return question;
    }

    public void edit(Question question) {
        questionMapper.updateByPrimaryKeySelective(question);
    }

    public void incViewCount(Integer id) {
        // 我觉得这里没有解决高并发啊....就当练手了
        Question record = new Question();
        record.setViewCount(1);
        record.setId(id);
        questionExtMapper.incViewCount(record);
    }
}
