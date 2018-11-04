package com.example.minhnhi.quanlyktx.cmd;

import com.google.gson.annotations.SerializedName;

public class AccountRequest {
    @SerializedName("userName")
    private String userName;
    @SerializedName("password")
    private String password;

    public AccountRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
