package com.wuxiu.forum.dto;

/**
 * 封装github用户信息
 */
public class GithubUser {

    private Long id;
    private String bio;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GithubUser{" +
                "id=" + id +
                ", bio='" + bio + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
