package com.scipublish.MailProxy.elasticsearch.common;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-19
 * Time: AM11:20
 * com.scipublish.MailProxy.elasticsearch.common
 * MailProxy
 */
public class ESField {

    private String field;
    private float boost;

    public ESField(String field) {
        this.field = field;
        this.boost = 1.0f;
    }

    public ESField(String field, float boost) {
        this.field = field;
        this.boost = boost;
    }

    public float getBoost() {
        return boost;
    }

    public void setBoost(float boost) {
        this.boost = boost;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
