package com.winston.service.impl;

import com.winston.entity.User;
import com.winston.exception.ErrorException;
import com.winston.jwt.TokenService;
import com.winston.redis.RedisService;
import com.winston.redis.key.SessionRedisKey;
import com.winston.result.CodeMsg;
import com.winston.service.ILoginService;
import com.winston.service.IUserService;
import com.winston.utils.PasswordHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName LoginServiceImpl
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/11/26 17:13
 * @Version：
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordHelper passwordHelper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private TokenService tokenService;

    /**
     * @auther: Winston
     * @Description: 未配置shiro时使用
     * @param:
     * @return:
     * @date: 2019/11/27 9:42
     */
    @Override
    public Map<String, Object> login(HttpServletRequest request, User user) {
        if(user == null){
            throw new ErrorException("登录失败");
        }
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            throw new ErrorException("用户名或密码不能为空");
        }
        passwordHelper.encryptPassword(user);
        User dbUser = userService.queryByUsername(user.getUsername());
        if(dbUser == null){
            throw new ErrorException(CodeMsg.ACCOUNT_NOT_EXIST);
        }
        if(!dbUser.getPassword().equals(user.getPassword())){
            throw new ErrorException(CodeMsg.USERNAME_OR_PASSWORD_ERROR);
        }
        //        得到session
        HttpSession session = request.getSession(true);
        //        保存登录对象到redis
        redisService.set(SessionRedisKey.JSESSION_KEY, session.getId(), dbUser);
        String token = tokenService.getToken(dbUser.getId(), dbUser.getUsername());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        dbUser.setPassword("");
        result.put("userInfo", dbUser);
        return result;
    }

    @Override
    public Map<String, Object> shiroLogin(HttpServletRequest request, User user) {
        User dbUser = userService.queryByUsername(user.getUsername());
        String token = tokenService.getToken(dbUser.getId(), dbUser.getUsername());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        dbUser.setPassword("");
        result.put("userInfo", dbUser);
        return result;
    }

    @Override
    public void logout(HttpServletRequest request) {

    }
}
