package com.example.minhnhi.quanlyktx.beans;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Notification {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("time")
    private Date postDate;
    @SerializedName("content")
    private String content;
    @SerializedName("status")
    private int status;
    @SerializedName("user_id")
    private int user_id;

    public Notification(int id, String title, Date postDate, String content) {
        this.id = id;
        this.title = title;
        this.postDate = postDate;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
