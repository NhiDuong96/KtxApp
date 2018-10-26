package com.example.minhnhi.quanlyktx.beans;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class UserAccount implements Serializable {
    public static final String TAG = "UserAccount";
    private String name, password;
    private String apiUrl;

    public UserAccount(String name, String password) {
        this.name = name;
        this.password = password;
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

    public JSONObject toJson() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("name", name)
                .put("password", password);
        return object;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
}
