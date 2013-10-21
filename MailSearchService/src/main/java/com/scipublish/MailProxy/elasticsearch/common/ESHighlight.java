package com.scipublish.MailProxy.elasticsearch.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-19
 * Time: PM9:03
 * com.scipublish.MailProxy.elasticsearch.common
 * MailProxy
 */
public class ESHighlight {
    private List<String> fields;
    private String preTag;
    private String postTag;

    public ESHighlight(String preTag, String postTag) {
        this.fields = new ArrayList<String>();
        this.postTag = postTag;
        this.preTag = preTag;
    }

    public void addField(String field){
        fields.add(field);
    }

    public List<String> getFields() {
        return fields;
    }

    public String getPostTag() {
        return postTag;
    }

    public String getPreTag() {
        return preTag;
    }
}
