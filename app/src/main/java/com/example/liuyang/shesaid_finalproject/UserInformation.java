package com.example.liuyang.shesaid_finalproject;

import cn.bmob.v3.BmobObject;

public class UserInformation extends BmobObject {
    private String user;
    private String password;
    private String sex;
    private String icon;
    //private String telephone;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
