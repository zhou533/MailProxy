package com.scipublish.MailProxy.elasticsearch.common;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-19
 * Time: PM9:02
 * com.scipublish.MailProxy.elasticsearch.common
 * MailProxy
 */
public class ESFixedTerm {

    private String field;
    private Object value;

    public ESFixedTerm(String field, Object value) {
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }
}
