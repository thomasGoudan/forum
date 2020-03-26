package com.wuxiu.forum.exception;

public enum CustomizeErrorCode implements IcustomizeErrorCode{
    QUESTION_NOT_FOUND("问题不存在哦,找不到");

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
