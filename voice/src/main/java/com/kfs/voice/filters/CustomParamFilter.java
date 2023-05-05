package com.kfs.voice.filters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.kfs.voice.enums.ResultEnum;
import com.kfs.voice.exception.CommonHandlerException;
import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

//@WebFilter(filterName = "customParamFilter", urlPatterns = {"/*"})
public class CustomParamFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        MyHttpRequestWrapper requestWrapper = new MyHttpRequestWrapper(request);
        try {
            // get请求
            if (requestWrapper.getMethod().equals(HttpMethod.GET.name())) {
                // 对参数进行处理，得到新的参数，将参数重新设置到request中
//                Map<String, String> params = handle(requestWrapper.getParameterMap());
//                requestWrapper.setParameter(params);
            } else { // post请求
                String body = requestWrapper.getBody();
                if (StringUtils.isNotEmpty(body)) {
                    if ("application/x-www-form-urlencoded".equals(requestWrapper.getContentType())) {
                        String paramStr = processParams(body);
                        requestWrapper = new MyHttpRequestWrapper(requestWrapper, paramStr.getBytes(StandardCharsets.UTF_8));
                    } else if ("application/json".equals(requestWrapper.getContentType())) {
                        Map<String, String> paramMap = processParams(JSON.parseObject(body));
                        requestWrapper = new MyHttpRequestWrapper(requestWrapper, JSON.toJSONString(paramMap).getBytes());
                    }

                    // 由于body是设置成不可变的，所以需要重新创建一个request，将body设置进去

                }
            }
            filterChain.doFilter(requestWrapper, servletResponse);
        } catch (Exception ex) {
            throw new CommonHandlerException(ResultEnum.INTERNAL_SERVER_ERROR);
        }
    }

//    public void outErrorMessage(ServletResponse servletResponse, Integer code, String msg) throws IOException {
//        //返回json错误
//        servletResponse.setCharacterEncoding("UTF-8");
//        servletResponse.setContentType("application/json; charset=utf-8");
//        PrintWriter out = servletResponse.getWriter();
//        JSONObject res = new JSONObject();
//        res.put("code", code);
//        res.put("msg", msg);
//        out.append(JSON.toJSONString(res));
//    }

    private String processParams(String params) {
        StringBuilder paramStr = new StringBuilder();

        String[] paramsArr = params.split("&");

        for (int i = 0; i < paramsArr.length; i++) {
            String param = paramsArr[i];
            String paramKey = param.substring(0, param.indexOf("="));
            String paramVal = param.substring(param.indexOf("=") + 1);

            if (i != 0) {
                paramStr.append("&");
            }

            paramStr.append(paramKey).append("=").append(paramVal);
        }

        return paramStr.toString();
    }

    private Map<String, String> processParams(JSONObject params) {
        HashMap<String, String> paramMap = new HashMap<>();

        params.entrySet().stream().forEach(paramSet -> {
            paramMap.put(paramSet.getKey(), (String) paramSet.getValue());
        });

        return paramMap;
    }
}
