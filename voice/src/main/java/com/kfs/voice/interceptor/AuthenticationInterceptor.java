package com.kfs.voice.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String token = request.getHeader(JwtUtil.USER_LOGIN_TOKEN);
//        if (token == null || token.equals("")) {
//            throw new RuntimeException("请先登录");
//        }
//
//        String sub = JwtUtil.validateToken(token);
//        if (sub == null || sub.equals("")) {
//            throw new RuntimeException("token验证失败");
//        }
//
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
