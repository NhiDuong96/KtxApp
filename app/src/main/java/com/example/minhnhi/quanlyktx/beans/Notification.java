package com.example.minhnhi.quanlyktx.beans;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Notification {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("timestamp")
    private Date postDate;
    @SerializedName("content")
    private String content;

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

}
