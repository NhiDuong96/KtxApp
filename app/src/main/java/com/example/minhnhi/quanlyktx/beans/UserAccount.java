package com.example.minhnhi.quanlyktx.beans;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class UserAccount implements Serializable {
    public static final String TAG = "UserAccount";
    @SerializedName("id")
    private int id;
    @SerializedName("userName")
    private String name;
    @SerializedName("password")
    private String password;

    public UserAccount(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
