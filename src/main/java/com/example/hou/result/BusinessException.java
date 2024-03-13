package com.example.hou.result;

import lombok.Data;

/**
 * 自定义异常类
 */
@Data
public class BusinessException extends RuntimeException{
    private int code;
    private String msg;


    public BusinessException(Result r) {
        super(r.getMsg());
        this.code = r.getCode();
        this.msg = r.getMsg();
    }

}
