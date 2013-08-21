package com.scipublish.MailProxy;

import com.scipublish.MailProxy.common.MailGunConfiguration;
import com.scipublish.MailProxy.common.MailProxyConfiguration;
import com.scipublish.MailProxy.mailgun.MailGunAPI;
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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml" })
public class MailGunAPITest {

    @Autowired
    MailGunConfiguration mailGunConfiguration;

    @Autowired
    MailProxyConfiguration mailProxyConfiguration;

    @Test
    public void testSendHtmlMail() throws Exception {
        List<String> to = new ArrayList<String>();
        to.add("zhouqiang@gozap.com");
        String result = MailGunAPI.SendHtmlMail(
                mailGunConfiguration.getKey(), mailProxyConfiguration.getDomain(),
                "me@scipublish.com", to, "Hello", "XXX");
        System.out.println(result);

    }
}
