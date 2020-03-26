package study.memoforward.community.service;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.memoforward.community.mapper.UserMapper;
import study.memoforward.community.model.User;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void insert(User user){
        userMapper.insert(user);
    }

    public User findByToken(String token){
        return userMapper.findByToken(token);
    }

    public User findByAccountId(String accountId){
        return userMapper.findByAccountId(accountId);
    }

    public void updateToken(String token, String accountId){
        userMapper.updateToken(token, accountId);
    }
}
