package study.memoforward.community.service;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.memoforward.community.mapper.UserMapper;
import study.memoforward.community.model.User;
import study.memoforward.community.model.UserExample;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void insert(User user){
        userMapper.insert(user);
    }

    public User findByToken(String token){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andTokenEqualTo(token);
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() != 0) return users.get(0);
        else return null;
    }

    public User findByAccountId(String accountId){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(accountId);
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() != 0) return users.get(0);
        else return null;
    }

    public void updateToken(String token, String accountId){
        User record = new User();
        record.setToken(token);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(accountId);
        userMapper.updateByExampleSelective(record, userExample);
    }

    public User findById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
