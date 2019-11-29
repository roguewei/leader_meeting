package com.winston.service;

import com.winston.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName ILoginService
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/11/26 17:12
 * @Version：
 */
public interface ILoginService {

    Map<String, Object> login(HttpServletRequest request, User user);

    Map<String, Object> shiroLogin(HttpServletRequest request, User user);

    void logout(HttpServletRequest request);
}
