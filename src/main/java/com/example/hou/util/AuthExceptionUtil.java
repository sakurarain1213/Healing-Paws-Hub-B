package com.example.hou.util;


import com.example.hou.result.Result;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.*;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.csrf.CsrfException;



//认证异常工具类
public class AuthExceptionUtil {

 public static Result getErrMsgByExceptionType(AuthenticationException e) {

  if (e instanceof LockedException) {

   return ResultUtil.error("账户被锁定，请联系管理员!");

  } else if (e instanceof CredentialsExpiredException) {
   return  ResultUtil.error("用户名或者密码输入错误!");

  }else if (e instanceof InsufficientAuthenticationException) {
   return ResultUtil.error("token有误或过期，请登录");

  } else if (e instanceof AccountExpiredException) {
   return ResultUtil.error("账户过期 请联系管理员");

  } else if (e instanceof DisabledException) {
   return ResultUtil.error("账户被禁用，请联系管理员!");

  } else if (e instanceof BadCredentialsException) {
   return ResultUtil.error("用户名或者密码输入错误!");

  }else if (e instanceof AuthenticationServiceException) {
   return ResultUtil.error("认证失败，请重试!");
  }

  return ResultUtil.error(e.getMessage());
 }

 public static Result getErrMsgByExceptionType(AccessDeniedException e) {

  if (e instanceof CsrfException) {

   return ResultUtil.error("非法访问跨域请求异常!");
  } else if (e instanceof CsrfException) {

   return ResultUtil.error("非法访问跨域请求异常!");
  } else if (e instanceof AuthorizationServiceException) {

   return ResultUtil.error("认证服务异常请重试!");
  }else if (e instanceof AccessDeniedException) {

   return ResultUtil.error("权限不足不允许访问! (debug模式 请检查此接口是否已经设置为匿名可访问，此时不需要传token)");
  }

  return ResultUtil.error(e.getMessage());
 }

}
