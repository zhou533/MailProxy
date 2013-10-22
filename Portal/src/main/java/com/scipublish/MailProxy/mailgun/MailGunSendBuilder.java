package com.scipublish.MailProxy.mailgun;

import com.alibaba.fastjson.JSON;
import com.scipublish.MailProxy.model.MPMail;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-23
 * Time: PM4:14
 * To change this template use File | Settings | File Templates.
 */
public class MailGunSendBuilder {

    private static final String O_TAG = "o:tag";
    private static final String O_CAMPAIGN = "o:campaign";
    private static final String O_TEST_MODE = "o:testmode";
    private static final String O_TRACKING = "o:tracking";
    private static final String O_TRACKING_CLICKS = "o:tracking-clicks";
    private static final String O_TRACKING_OPENS = "o:tracking-opens";

    private String from;
    private Set<MPMail> to = new HashSet<MPMail>();
    private String text;
    private String html;
    private String subject;

    private String customVariable;
    private String customVariableValue;

    private List<String> tag = new ArrayList<String>();
    private String campaign;
    private boolean trackEnable;
    private boolean trackClicksEnable;
    private boolean trackOpensEnable;
    private boolean testEnable;

    public  MailGunSendBuilder setSubject(String subject){
        this.subject = subject;
        return this;
    }

    public MailGunSendBuilder setCampaign(String campaign) {
        this.campaign = campaign;
        return this;
    }

    public MailGunSendBuilder setFrom(String from) {
        this.from = from;
        return this;
    }

    public MailGunSendBuilder setHtml(String html) {
        this.html = html;
        return this;
    }

    public MailGunSendBuilder setTestEnable(boolean testEnable) {
        this.testEnable = testEnable;
        return this;
    }

    public MailGunSendBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public MailGunSendBuilder setTrackClicksEnable(boolean trackClicksEnable) {
        this.trackClicksEnable = trackClicksEnable;
        return this;
    }

    public MailGunSendBuilder setTrackEnable(boolean trackEnable) {
        this.trackEnable = trackEnable;
        return this;
    }

    public MailGunSendBuilder setTrackOpensEnable(boolean trackOpensEnable) {
        this.trackOpensEnable = trackOpensEnable;
        return this;
    }

    public MailGunSendBuilder addMail(String mail){
        this.to.add(new MPMail(mail));
        return this;
    }

    public MailGunSendBuilder addMail(String mail, String name){
        this.to.add(new MPMail(mail, name));
        return this;
    }

    public MailGunSendBuilder addMail(MPMail mail){
        this.to.add(mail);
        return this;
    }

    public MailGunSendBuilder addTag(String tag){
        this.tag.add(tag);
        return this;
    }

    public MailGunSendBuilder addCustomVariable(String variable, Map<String, Object> values){
        this.customVariable = variable;
        this.customVariableValue = JSON.toJSONString(values);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        if (from != null){
            str.append("from:");
            str.append(from);
            str.append("\n");
        }
        if (to != null && to.size() > 0){
            for (MPMail mail : to){
                str.append("to:");
                str.append(mail);
                str.append("\n");
            }
        }
        if (subject != null){
            str.append("subject:");
            str.append(subject);
            str.append("\n");
        }
        if (text != null){
            str.append("text:");
            str.append("\n");
        }
        if (html != null){
            str.append("html:");
            str.append("\n");
        }

        if (tag != null && tag.size() > 0){
            str.append("o:tag:");
            str.append(StringUtils.join(tag, ","));
            str.append("\n");
        }

        if (campaign != null){
            str.append("o:campaign:");
            str.append(campaign);
            str.append("\n");
        }

        if (trackEnable){
            str.append("tracking:on");
            str.append("\n");
        }

        if (trackClicksEnable){
            str.append("tracking-clicks:on");
            str.append("\n");
        }

        if (trackOpensEnable){
            str.append("tracking-opens:on");
            str.append("\n");
        }

        if (testEnable){
            str.append("test-mode:on");
            str.append("\n");
        }

        return str.toString();
    }

    public MultivaluedMapImpl getParams(){
        MultivaluedMapImpl params = new MultivaluedMapImpl();
        if (from != null){
            params.add("from", from);
        }
        if (to != null && to.size() > 0){
            for (MPMail mail : to){
                params.add("to", mail);
            }
        }
        if (subject != null){
            params.add("subject", subject);
        }
        if (text != null){
            params.add("text", text);
        }
        if (html != null){
            params.add("html", html);
        }

        if (tag != null && tag.size() > 0){
            for (String t : tag){
                params.add(O_TAG, t);
            }
        }

        if (campaign != null){
            params.add(O_CAMPAIGN, campaign);
        }

        params.add(O_TRACKING, trackEnable?"yes":"no");
        params.add(O_TRACKING_CLICKS, trackClicksEnable?"yes":"no");
        params.add(O_TRACKING_OPENS, trackOpensEnable?"yes":"no");

        if (testEnable){
            params.add(O_TEST_MODE, "yes");
        }

        if (!StringUtils.isEmpty(customVariable) &&
                !StringUtils.isEmpty(customVariableValue)){
            params.add("v:" + customVariable, customVariableValue);
        }

        return params;
    }

}
