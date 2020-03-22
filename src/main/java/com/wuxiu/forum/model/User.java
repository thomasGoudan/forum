package com.wuxiu.forum.model;

/**
 * 用户信息
 */
public class User {

    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gtmCreate;
    private Long gtmModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getGtmCreate() {
        return gtmCreate;
    }

    public void setGtmCreate(Long gtmCreate) {
        this.gtmCreate = gtmCreate;
    }

    public Long getGtmModified() {
        return gtmModified;
    }

    public void setGtmModified(Long gtmModified) {
        this.gtmModified = gtmModified;
    }
}
