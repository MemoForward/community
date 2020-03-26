package study.memoforward.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import study.memoforward.community.model.Question;

import java.util.List;

@Repository
@Mapper
public interface QuestionMapper {

    @Insert("insert into question " +
            "(title, description, gmt_create, gmt_modified, creator, comment_count, view_count, like_count, tag) " +
            "values (#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{commentCount}, #{viewCount}, #{likeCount}, #{tag})")
    public void create(Question question);

    @Select("select * from question")
    List<Question> getList();
}

