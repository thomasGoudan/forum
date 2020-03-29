package com.wuxiu.forum.enums;

/**
 * 状态枚举
 */
public enum NotificationStatusEnum {

    UNREAD(0),
    READE(1);

    private int status;

    public int getStatus() {
        return status;
    }


    NotificationStatusEnum(int status) {
        this.status = status;
    }

}
