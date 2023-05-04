package com.kfs.voice.interceptor;

import com.kfs.voice.enums.ResultEnum;
import com.kfs.voice.exception.AuthHandlerException;
import com.kfs.voice.utils.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String token = request.getHeader(JwtUtil.USER_LOGIN_TOKEN);

        if (token == null || token.equals("")) {
            throw new AuthHandlerException(ResultEnum.UNAUTHORIZED_TOKEN_NOT_MATCH);
        }

        boolean isValid = JwtUtil.validateToken(token);
        if (!isValid) {
            throw new AuthHandlerException(ResultEnum.UNAUTHORIZED_TOKEN_VERIFY_FAILURE);
        }


//        if (JwtUtil.isNeedUpdate(token)) {
//            String newToken = JwtUtil.createToken(sub);
//            response.setHeader(JwtUtil.USER_LOGIN_TOKEN, newToken);
//        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
