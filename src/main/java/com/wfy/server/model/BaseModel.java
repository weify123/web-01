package com.wfy.server.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by weifeiyu on 2017/5/4.
 */
public class BaseModel {

    private Integer removed;

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date created;

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date updated;

    public Integer getRemoved() {
        return removed;
    }

    public void setRemoved(Integer removed) {
        this.removed = removed;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
