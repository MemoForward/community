package study.memoforward.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import study.memoforward.community.model.Question;
import study.memoforward.community.model.User;

import java.util.List;

@Repository
@Mapper
public interface QuestionMapper {

    @Insert("insert into question " +
            "(title, description, gmt_create, gmt_modified, creator, comment_count, view_count, like_count, tag) " +
            "values (#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{commentCount}, #{viewCount}, #{likeCount}, #{tag})")
    void create(Question question);

    @Select("select * from question limit #{limit} offset #{offset}")
    List<Question> getList(@Param("offset") Integer offset, @Param("limit") Integer limit);

    @Select("select count(1) from question")
    Integer count();

    @Select("select count(1) from question where creator = #{id}")
    Integer countByUser(User user);

    @Select("select * from question where creator = #{user.id} limit #{limit} offset #{offset}")
    List<Question> getListByUser(@Param("user") User user, @Param("offset") Integer offset, @Param("limit") Integer limit);
}

