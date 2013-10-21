package com.scipublish.MailProxy.elasticsearch.common;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-19
 * Time: PM8:59
 * com.scipublish.MailProxy.elasticsearch.common
 * MailProxy
 */
public class ESSort {
    private String field;
    private ESSortType type;

    public ESSort(String field, ESSortType type) {
        this.field = field;
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public ESSortType getType() {
        return type;
    }
}
