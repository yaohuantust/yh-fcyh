package com.yh.system.service;

import com.yh.domain.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @author yaohuan
 * @version 1.0
 **/
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return token
     */
    public String login(String username, String password, String code, String uuid) {
        // TODO 从Redis中取出验证码并进行处理

        // 用户验证
        Authentication authentication = null;

        // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // TODO 记录登录日志

        return tokenService.createToken(loginUser);
    }
}
