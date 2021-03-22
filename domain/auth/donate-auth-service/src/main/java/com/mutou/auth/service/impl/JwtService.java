package com.mutou.auth.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.mutou.auth.service.pojo.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class JwtService {

    // 生产环境不能这么用
    private static final String KEY = "changeIt";
    private static final String ISSUER = "test";

    private static final long TOKEN_EXP_TIME = 24 * 3600 * 1000;
    private static final String USER_ID = "user_id";

    /**
     * 生成Token
     *
     * @param acct
     * @return
     */
    public String token(Account acct) {
        Date now = new Date();
        // 生产环境换成非堆成加密
        Algorithm algorithm = Algorithm.HMAC256(KEY);

        String token = JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + TOKEN_EXP_TIME))
                .withClaim(USER_ID, acct.getUserId())
//                .withClaim("ROLE", "")
                .sign(algorithm);

        log.info("jwt generated, userId={}", acct.getUserId());
        return token;
    }

    /**
     * 校验Token
     *
     * @param token
     * @param userId
     * @return
     */
    public boolean verify(String token, String userId) {
        log.info("verifying jwt - username={}", userId);

        try {
            Algorithm algorithm = Algorithm.HMAC256(KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .withClaim(USER_ID, userId)
                    .build();

            verifier.verify(token);
            return true;
        } catch (Exception e) {
            log.error("auth failed, userId={}", userId);
            return false;
        }
    }

}
