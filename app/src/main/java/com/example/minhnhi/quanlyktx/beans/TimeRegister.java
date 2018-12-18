package com.example.minhnhi.quanlyktx.beans;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class TimeRegister {
    @SerializedName("id")
    private int id;
    @SerializedName("dateBegin")
    private Date dateBegin;
    @SerializedName("dateEnd")
    private Date dateEnd;
    @SerializedName("status")
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
