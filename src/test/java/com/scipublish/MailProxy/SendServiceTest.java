package com.scipublish.MailProxy;

import com.scipublish.MailProxy.model.MPMail;
import com.scipublish.MailProxy.model.MPMailSession;
import com.scipublish.MailProxy.service.MPSendService;
import com.scipublish.MailProxy.service.MPSessionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-9-23
 * Time: PM6:15
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/*.xml" })
public class SendServiceTest {

    @Autowired
    private MPSendService sendService;

    @Autowired
    private MPSessionService sessionService;

    @Test
    public void testSend() throws Exception {
        MPMailSession mailSession = new MPMailSession();
        mailSession.setSession("HitAll");
        mailSession.setSubject("Hello there");
        mailSession.setContent("<a href=\"http://www.scipublish.com\">sciop</a>");
        mailSession.setFrom("yo_zhouq@hotmail.com");

        Integer result = sessionService.createSession(mailSession);
        assertEquals(1,result.intValue());

        result = sendService.sendMail(mailSession,new MPMail("yo.zhouq@qq.com"));
        assertEquals(0, result.intValue());

        result = sendService.sendMail(mailSession, new MPMail("zhouqiang@gozap.com"));
        assertEquals(0, result.intValue());

    }
}
