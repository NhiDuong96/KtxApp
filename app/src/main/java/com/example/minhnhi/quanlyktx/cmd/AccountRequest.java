package com.example.minhnhi.quanlyktx.cmd;

import com.google.gson.annotations.SerializedName;

public class AccountRequest {
    @SerializedName("studentCode")
    private String mssv;
    @SerializedName("userName")
    private String userName;
    @SerializedName("password")
    private String password;

    public AccountRequest(String mssv, String userName, String password) {
        this.mssv = mssv;
        this.userName = userName;
        this.password = password;
    }
}
