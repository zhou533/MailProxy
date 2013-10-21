package com.scipublish.MailProxy.elasticsearch.common;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-19
 * Time: PM9:09
 * com.scipublish.MailProxy.elasticsearch.common
 * MailProxy
 */
public class ESTimeRange {

    private String dateField;
    private long startTime;
    private long endTime;

    public ESTimeRange(String dateField, long startTime, long endTime) {
        this.dateField = dateField;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDateField() {
        return dateField;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getStartTime() {
        return startTime;
    }
}
