package com.yh.system.service;

import com.yh.constant.Constants;
import com.yh.domain.model.LoginUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author yaohuan
 * @version 1.0
 **/
@Component
public class TokenService {

    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    /**
     * 生成令牌
     * @param loginUser 登录用户
     * @return
     */
    public String createToken(LoginUser loginUser) {
        String token = UUID.randomUUID().toString();
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, token);
        return createToken(claims);
    }

    private String createToken(Map<String, Object> claims) {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }
}
