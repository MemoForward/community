package study.memoforward.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import study.memoforward.community.model.User;

@Repository
@Mapper
public interface UserMapper {
    @Insert("insert into user " +
            "(account_id, name, token, gmt_create, gmt_modified) " +
            "values (#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified})")
    void insert(User user);
}
