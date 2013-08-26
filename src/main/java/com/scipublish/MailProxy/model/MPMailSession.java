package com.scipublish.MailProxy.model;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-26
 * Time: PM4:26
 * To change this template use File | Settings | File Templates.
 */
public class MPMailSession {
    private Integer id;
    private String session;
    private Timestamp createTime;
    private Timestamp sendTime;
    private String from;
    private String subject;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
