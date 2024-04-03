package com.example.hou.result;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //自定义异常
    @ExceptionHandler(BusinessException.class)
    public Result systemExceptionHandler(BusinessException e) {
        log.error("BusinessException全局异常：{}",e);

        Result result = new Result();
        result.setCode(e.getCode());
        result.setMsg(e.getMsg());
        return result;

    }

    //系统异常
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
        log.error("Exception全局异常：{}",e);
        Result result = new Result();
        result.setCode(-100);
        result.setMsg(e.getMessage());
        return result;
    }


}
