package com.example.minhnhi.quanlyktx.beans;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class UserAccount implements Serializable {
    public static final String TAG = "UserAccount";

    private int id;

    private String name;

    private UserProfile profile;

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

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public static UserAccount parseJson(String json) throws JSONException {
        JSONObject res = new JSONObject(json);
        JSONObject data = res.getJSONObject("data");
        JSONObject details = data.getJSONObject("userDetail");
        JSONObject studentCode = data.getJSONObject("studentCode");

        UserAccount account = new UserAccount();
        UserProfile profile = new UserProfile();

        account.setId(data.getInt("id"));
        account.setName(data.getString("userName"));

        profile.setName(details.getString("fullName"));
        profile.setMssv(studentCode.getString("value"));
        profile.setYear(studentCode.getString("grade"));
        profile.setClassName(studentCode.getString("name"));
        profile.setAddress(details.getString("address"));
        profile.setPhone(details.getString("phone"));
        profile.setGender(data.getInt("gender"));

        account.setProfile(profile);
        return account;
    }

}
