package com.scipublish.MailProxy;

import com.scipublish.MailProxy.common.MailGunConfiguration;
import com.scipublish.MailProxy.common.MailProxyConfiguration;
import com.scipublish.MailProxy.mailgun.MailGunAPI;
import com.scipublish.MailProxy.mailgun.MailGunSendBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-21
 * Time: PM5:25
 * To change this template use File | Settings | File Templates.
 */
public class MailGunAPITest {


    @Test
    public void testSendHtmlMail() throws Exception {
        MailGunSendBuilder builder = new MailGunSendBuilder()
                .setFrom("me@scipublish.com")
                .addMail("yo.zhouq@qq.com")
                .setSubject("Hello Mailgun %recipient.name%")
                .setHtml("<a href=\"http://www.w3school.com.cn?id=%recipient.name%&name=%recipient.id%\">W3School</a>").setTrackEnable(true)
                .setTrackClicksEnable(false)
                .setTrackOpensEnable(false);
        builder.recipient_variables = "{\"yo.zhouq@qq.com\":{\"name\":\"hahassssssss\",\"id\":\"123333333\"}}";
        System.out.println(builder.toString());
        String result = MailGunAPI.SendMail(
                "key-8n6oqa7yq-wa3fyfoqm6ostvkr-x9p87",
                "scipublish.com",
                builder);
        System.out.println(result);

    }
}
