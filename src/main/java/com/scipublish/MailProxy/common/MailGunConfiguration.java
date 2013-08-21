package com.scipublish.MailProxy.common;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-21
 * Time: PM1:01
 * To change this template use File | Settings | File Templates.
 */
public class MailGunConfiguration {

    private String apiUrl;
    private String key;

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
