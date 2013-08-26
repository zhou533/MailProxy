package com.scipublish.MailProxy;

import com.scipublish.MailProxy.mapper.MPMailSessionMapper;
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
@ContextConfiguration(locations = { "classpath:/spring/*.xml" })
public class MailDBTest {

    @Autowired
    private MPMailSessionMapper mailSessionMapper;

    @Test
    public void testSessionInsert() throws Exception {
        MPMailSession session = new MPMailSession();
        session.setSession("demo");
        session.setCreateTime(new Timestamp(System.currentTimeMillis()));
        session.setFrom("zhoutee@gozap.com");
        session.setSubject("Hello");
        session.setContent("XXX");
        Integer id = mailSessionMapper.addMailSession(session);
        System.out.println("Session id : " + id);
    }
}
