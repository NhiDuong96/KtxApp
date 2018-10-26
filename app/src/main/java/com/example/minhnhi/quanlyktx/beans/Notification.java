package com.example.minhnhi.quanlyktx.beans;

import java.util.Date;

public class Notification {
    private String title;
    private Date postDate;
    private String content;

    public Notification(String title, Date postDate, String content) {
        this.title = title;
        this.postDate = postDate;
        this.content = content;
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
