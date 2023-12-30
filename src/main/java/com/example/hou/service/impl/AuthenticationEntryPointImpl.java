package com.example.hou.service.impl;


import com.alibaba.fastjson.JSON;
import com.example.hou.util.AuthExceptionUtil;
import com.example.hou.util.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



//自定义认证失败异常处理类
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException authenticationException) throws IOException, ServletException {
        System.out.println("AuthenticationEntryPoint:用户未登录");
        WebUtils.rednerString(httpServletResponse, JSON.toJSONString(AuthExceptionUtil.getErrMsgByExceptionType(authenticationException)));
    }
}
