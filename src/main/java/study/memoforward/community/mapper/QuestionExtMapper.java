package study.memoforward.community.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;
import study.memoforward.community.dto.QuestionDTO;
import study.memoforward.community.model.Question;
import study.memoforward.community.model.QuestionExample;

import java.util.List;

@Repository
public interface QuestionExtMapper {
    int incViewCount(Question record);
    int incCommentCount(Question record);
    List<Question> relatedSelectByTag(QuestionDTO record);
    long countRelatedQuestion(QuestionDTO questionDTO);
}
