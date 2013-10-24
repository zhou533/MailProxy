package com.scipublish.MailProxy.elasticsearch.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private String startTime;
    private String endTime;

    public ESTimeRange(String dateField, String startTime, String endTime) throws Exception {
        if (!checkTime(startTime) || !checkTime(endTime)){
            throw new Exception("Bad time format.");
        }
        this.dateField = dateField;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDateField() {
        return dateField;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    private static boolean checkTime(String formattedTime){
        return checkMatcher("\\d{4}-\\d{2}", formattedTime);
    }

    private static boolean checkMatcher(String regex, String source){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source.toLowerCase());
        return matcher.matches();
    }
}
