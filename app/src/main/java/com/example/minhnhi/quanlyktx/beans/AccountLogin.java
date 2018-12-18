package com.example.minhnhi.quanlyktx.beans;

import com.google.gson.annotations.SerializedName;

public class AccountLogin {
    @SerializedName("studentCode")
    private String mssv;
    @SerializedName("userName")
    private String userName;
    @SerializedName("password")
    private String password;

    public AccountLogin(String mssv, String userName, String password) {
        this.mssv = mssv;
        this.userName = userName;
        this.password = password;
    }
}
