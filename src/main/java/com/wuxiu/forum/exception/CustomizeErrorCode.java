package com.wuxiu.forum.exception;

public enum CustomizeErrorCode implements IcustomizeErrorCode{

    QUESTION_NOT_FOUND(2001,"问题不存在哦,找不到"),
    TARGET_PRAM_NOT_FOUND(2002,"未选中任何问题"),
    NOT_LOGIN(2003,"未登录，请先登录"),
    SYS_ERROR(2004,"服务器出问题了,不骗你"),
    TYPE_PRAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUNT(2006,"你的评论不存在"),
    COMMENT_is_EMPTY(2007,"输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008,"不是本人消息"),
    NOTIFICATION_NOT_FOUND(2009,"消息没有找到");


    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
