package com.example.king.managebook.model.body;

public class AdminRegisterBody {
    private String fullName;
    private String position;

    public AdminRegisterBody() {
    }

    public AdminRegisterBody(String fullName, String position) {
        this.fullName = fullName;
        this.position = position;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
