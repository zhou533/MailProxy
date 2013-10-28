package com.scipublish.MailProxy.model;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-28
 * Time: PM2:21
 * To change this template use File | Settings | File Templates.
 */
public class MPMailSessionMails {
    private Integer id;
    private Integer sessionId;
    private Integer type;
    private String params;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
