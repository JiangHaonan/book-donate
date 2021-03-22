package com.mutou.auth.service.impl;


import com.mutou.auth.service.AuthService;
import com.mutou.auth.service.pojo.Account;
import com.mutou.auth.service.pojo.AuthResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static com.mutou.auth.service.pojo.AuthCode.SUCCESS;
import static com.mutou.auth.service.pojo.AuthCode.USER_NOT_FOUND;
import static com.mutou.auth.service.pojo.AuthCode.INVALID_TOKEN;

import java.util.UUID;

@RestController
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String USER_TOKEN = "USER_TOKEN-";

    @Override
    public AuthResponse tokenize(String userId) {
        Account account = Account.builder()
                .userId(userId)
                .build();

        String token = jwtService.token(account);
        account.setToken(token);
        account.setRefreshToken(UUID.randomUUID().toString());

//        redisTemplate.opsForValue().set(USER_TOKEN + userId, account);
//        redisTemplate.opsForValue().set(account.getRefreshToken(), userId);

        return AuthResponse.builder()
                .account(account)
                .code(SUCCESS.getCode())
                .build();
    }

    @Override
    public AuthResponse verify(Account account) {
        boolean success = jwtService.verify(account.getToken(), account.getUserId());
        return AuthResponse.builder()
                .code(success ? SUCCESS.getCode() : INVALID_TOKEN.getCode())
                .build();
    }

    // 有很多种方法实现自动刷新，比如前端主动调用（可以在AuthResponse里将过期时间返回给前端口）
    @Override
    public AuthResponse refresh(String refreshToken) {
//        String userId = (String) redisTemplate.opsForValue().get(refreshToken);
//        if (StringUtils.isBlank(userId)) {
//            return AuthResponse.builder()
//                    .code(USER_NOT_FOUND.getCode())
//                    .build();
//        }
//
//        redisTemplate.delete(refreshToken);
//        return tokenize(userId);
        return null;
    }

    @Override
    public AuthResponse delete(@RequestBody Account account) {
        AuthResponse token = verify(account);
        AuthResponse resp = new AuthResponse();
        if (SUCCESS.getCode().equals(token.getCode())) {
//            redisTemplate.delete(USER_TOKEN + account.getUserId());
//            redisTemplate.delete(account.getRefreshToken());
            resp.setCode(SUCCESS.getCode());
        } else {
            resp.setCode(USER_NOT_FOUND.getCode());
        }
        return resp;
    }
}
