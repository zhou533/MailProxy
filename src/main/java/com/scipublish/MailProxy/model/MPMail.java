package com.scipublish.MailProxy.model;

import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-23
 * Time: PM3:43
 * To change this template use File | Settings | File Templates.
 */
public class MPMail {
    private Integer id;
    private String name;
    private String mail;
    private Integer state;

    public MPMail(String mail, String name) {
        this.mail = mail;
        this.name = name;
        this.state = 0;
    }

    public MPMail(String mail) {
        this.mail = mail;
        this.name = null;
        this.state = 0;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        if (StringUtils.isEmpty(name)){
            return mail;
        }

        if (!StringUtils.isEmpty(mail)){
            return name + " <" + mail + ">";
        }

        return null;
    }

    @Override
    public int hashCode() {
        return (mail != null) ? mail.hashCode() : "".hashCode();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
