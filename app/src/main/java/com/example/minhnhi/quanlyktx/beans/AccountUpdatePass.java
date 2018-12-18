package com.example.minhnhi.quanlyktx.beans;

import com.google.gson.annotations.SerializedName;

public class AccountUpdatePass {
    @SerializedName("name")
    private String name;
    @SerializedName("oldPassword")
    private String oldPass;
    @SerializedName("newPassword")
    private String newPass;

    public AccountUpdatePass(String name, String oldPass, String newPass) {
        this.name = name;
        this.oldPass = oldPass;
        this.newPass = newPass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
