package com.kfs.voice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kfs.voice.entity.User;
import com.kfs.voice.enums.ResultEnum;
import com.kfs.voice.exception.AuthHandlerException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtil {
    public static String header;

    public static String tokenPrefix;

    public static String secret;

    public static long expireTime;

    public static final String USER_LOGIN_TOKEN = "Authorization";

    public void setHeader(String header) {
        JwtUtil.header = header;
    }

    public void setTokenPrefix(String tokenPrefix) {
        JwtUtil.tokenPrefix = tokenPrefix;
    }

    public void setSecret(String secret) {
        JwtUtil.secret = secret;
    }

    public void setExpireTime(int expireTimeInt) {
        JwtUtil.expireTime = expireTimeInt * 1000L * 60;
    }

    public static String createToken(User user) {
        return JWT.create()
                .withSubject(user.getPhone())
                .withClaim("userId", user.getId())
                .withClaim("username", user.getUsername())
                .withClaim("phone", user.getPhone())
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime * 1000L * 60))
                .sign(Algorithm.HMAC512(secret));
    }

    public static boolean validateToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token.replace(tokenPrefix, ""));

            if ("".equals(decodedJWT.getSubject())) {
                return false;
            }

            return true;
        } catch (TokenExpiredException e) {
            throw new AuthHandlerException(ResultEnum.UNAUTHORIZED_TOKEN_EXPIRE);
        } catch (Exception e) {
            throw new AuthHandlerException(ResultEnum.UNAUTHORIZED_TOKEN_VERIFY_FAILURE);
        }
    }

    public static User parseTokenToUser(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token.replace(tokenPrefix, ""));

            User user = new User();
            user.setId(decodedJWT.getClaim("userId").asString());
            user.setUsername(decodedJWT.getClaim("username").asString());
            user.setPhone(decodedJWT.getClaim("phone").asString());

            return user;
        } catch (TokenExpiredException e) {
            throw new AuthHandlerException(ResultEnum.UNAUTHORIZED_TOKEN_EXPIRE);
        } catch (Exception e) {
            throw new AuthHandlerException(ResultEnum.UNAUTHORIZED_TOKEN_VERIFY_FAILURE);
        }
    }

    public static boolean isNeedUpdate(String token) {
        Date expiresAt = null;
        try {
            expiresAt = JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token.replace(tokenPrefix, ""))
                    .getExpiresAt();
        } catch (TokenExpiredException e) {
            return true;
        } catch (Exception e) {
            throw new AuthHandlerException(ResultEnum.UNAUTHORIZED_TOKEN_VERIFY_FAILURE);
        }

        return (expiresAt.getTime() - System.currentTimeMillis()) < (expireTime >> 1);
    }
}
