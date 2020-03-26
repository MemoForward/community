package study.memoforward.community.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import study.memoforward.community.model.User;

@Repository
@Mapper
public interface UserMapper {
    @Insert("insert into user " +
            "(account_id, name, token, gmt_create, gmt_modified, bio, avatar_url) " +
            "values (#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified}, #{bio}, #{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set token = #{token} where account_id = #{accountId}")
    void updateToken(@Param("token") String token, @Param("accountId") String accountId);

    @Select("select * from user where id = #{userId}")
    User getById(@Param("userId") int userId);
}
