package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iUserService")
public class UserServiceimpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount=userMapper.checkUsername(username);
        if (resultCount==0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        //todo 密码登录M5
        User user=userMapper.selectLogin(username,password);
        if(user==null){
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBysuccess("登陆成功",user);
    }

    public ServerResponse<String> register(User user){
        int resultCount=userMapper.checkUsername(user.getUsername());
        if(resultCount>0){
            return ServerResponse.createByErrorMessage("用户名已经存在");

        }
        resultCount=userMapper.checkEmail(user.getEmail());
        if(resultCount>0){
            return ServerResponse.createByErrorMessage("email已经存在");

        }

        user.setRole(Const.Role.ROLE_CUSTOMER);


    }
}
