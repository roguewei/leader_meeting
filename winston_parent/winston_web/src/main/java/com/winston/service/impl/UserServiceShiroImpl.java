package com.winston.service.impl;

import com.winston.entity.User;
import com.winston.exception.ErrorException;
import com.winston.jwt.comment.RawAccessJwtToken;
import com.winston.mapper.UserMapper;
import com.winston.result.CodeMsg;
import com.winston.utils.PasswordHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ClassName UserServiceShiroImpl
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/11/27 10:10
 * @Version：
 */
@Service
public class UserServiceShiroImpl extends UserServiceImpl {

    @Autowired
    private PasswordHelper passwordHelper;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private RawAccessJwtToken rawAccessJwtToken;

    @Override
    public int save(User user) {
        String nicname = rawAccessJwtToken.getUserName();
        long nowTime = new Date().getTime();

        user.setEnable(1);
        user.setState("1");
        user.setCreateOpr("system");
        user.setCreateTime(nowTime);
        user.setUpdateOpr("system");
        user.setUpdateTime(nowTime);
        user.setOperatorType("0");
        passwordHelper.encryptPassword(user);
        mapper.insert(user);
        return user.getId();
    }

}
