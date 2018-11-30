package com.example.king.managebook.model;

import java.io.Serializable;

public class Profile implements Serializable {
    private int id;
    private User user;
    private String name;
    private String address;
    private String srcAvatar;
    private long birthday;

    public Profile() {
    }

    public Profile(int id, User user, String name, String address, String srcAvatar) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.address = address;
        this.srcAvatar = srcAvatar;
    }

    public Profile(User user, String name, String address) {
        this.user = user;
        this.name = name;
        this.address = address;
    }

    public Profile(int id, String name, String address, String srcAvatar) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.srcAvatar = srcAvatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSrcAvatar() {
        return srcAvatar;
    }

    public void setSrcAvatar(String srcAvatar) {
        this.srcAvatar = srcAvatar;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }
}
