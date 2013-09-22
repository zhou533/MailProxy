package com.scipublish.MailProxy;

import com.scipublish.MailProxy.mapper.MPMailMapper;
import com.scipublish.MailProxy.mapper.MPMailRecordMapper;
import com.scipublish.MailProxy.mapper.MPMailSessionMapper;
import com.scipublish.MailProxy.model.MPMail;
import com.scipublish.MailProxy.model.MPMailRecord;
import com.scipublish.MailProxy.model.MPMailSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-26
 * Time: PM6:05
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext-db.xml" })
public class MailDBTest {

    @Autowired
    private MPMailSessionMapper mailSessionMapper;

    @Autowired
    private MPMailRecordMapper mailRecordMapper;

    @Autowired
    private MPMailMapper mailMapper;


    @Test
    public void testSessionInsert() throws Exception {
        MPMailSession session = new MPMailSession();
        session.setSession("demo");
        session.setCreateTime(new Timestamp(System.currentTimeMillis()));
        session.setSendTime(new Timestamp(System.currentTimeMillis()));
        session.setFrom("zhoutee@gozap.com");
        session.setSubject("Hello");
        session.setContent("XXX");
        Integer id = mailSessionMapper.addMailSession(session);
        System.out.println("Session id : " + id);
    }

    @Test
    public void testRecordInsert() throws Exception {
        MPMailRecord record = new MPMailRecord();
        record.setReceiver("asdasd");
        record.setSessionId(2);
        record.setSendTime(new Timestamp(System.currentTimeMillis()));
        record.setCreateTime(new Timestamp(System.currentTimeMillis()));
        record.setState(0);
        record.setMessageId("adaseeeee");
        Integer id = mailRecordMapper.addMailRecord(record);
        System.out.println("--------->Record id : " + id);
    }

    @Test
    public void testMailInsert() throws Exception {
        MPMail mail = new MPMail("SS@WS.com");
        Boolean result = mailMapper.addMail(mail);
        if (result)
            System.out.println("--------->Record id : " + mail.getId());
        else
            System.out.println("add mail failed.");
    }
}
