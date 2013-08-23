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
    private String name;
    private String mail;

    public MPMail(String mail, String name) {
        this.mail = mail;
        this.name = name;
    }

    public MPMail(String mail) {
        this.mail = mail;
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
