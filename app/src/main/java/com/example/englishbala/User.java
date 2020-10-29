package com.example.englishbala;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class User extends LitePalSupport {

    // 唯一
    @Column(unique = true, defaultValue = "000000")
    private long userId;

    // 头像
    private String userProfile;

    private String userName;

    // 词汇量
    @Column(defaultValue = "0")
    private int userWordNumber;

    // 金币数
    @Column(defaultValue = "0")
    private int userMoney;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserWordNumber() {
        return userWordNumber;
    }

    public void setUserWordNumber(int userWordNumber) {
        this.userWordNumber = userWordNumber;
    }

    public int getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(int userMoney) {
        this.userMoney = userMoney;
    }

}
