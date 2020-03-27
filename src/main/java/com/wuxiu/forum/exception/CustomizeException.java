package com.wuxiu.forum.exception;

/**
 * 定义自己的异常
 */
public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;

    /**
     * 当创建自定义异常的时候传入异常接口来实现自己的异常处理
     * @param errorCode
     */
    public CustomizeException(IcustomizeErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}

