package com.ljqweb.community_rap.service;

import com.ljqweb.community_rap.mapper.UserMapper;
import com.ljqweb.community_rap.model.User;
import com.ljqweb.community_rap.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size()==0){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            User dbUser = users.get(0);
            User updateuser = new User();
            updateuser.setGmtCreate(System.currentTimeMillis());
            updateuser.setAvatarUrl(user.getAvatarUrl());
            updateuser.setName(user.getName());
            updateuser.setToken(user.getToken());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateuser,example);


        }

    }
}
