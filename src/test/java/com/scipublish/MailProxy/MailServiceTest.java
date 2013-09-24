package com.scipublish.MailProxy;

import com.alibaba.fastjson.JSON;
import com.scipublish.MailProxy.common.MPErrorCode;
import com.scipublish.MailProxy.model.MPMail;
import com.scipublish.MailProxy.model.MPMailRecord;
import com.scipublish.MailProxy.model.MPMailRecordState;
import com.scipublish.MailProxy.service.MPMailService;
import com.scipublish.MailProxy.service.MPRecordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-9-23
 * Time: PM5:22
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/*.xml" })
public class MailServiceTest {

    @Autowired
    private MPMailService mailService;

    @Autowired
    private MPRecordService recordService;

    @Test
    public void testSetRedis() throws Exception {
        MPMail mail = new MPMail("yo.zhouq@WS.com");
        Integer result = mailService.addMail(mail);
        assertEquals("done",1,result.intValue());
    }

    @Test
    public void testGetMail() throws Exception {
        MPMail mail = mailService.getMail("yo.zhouqd@WS.com");
        System.out.println(JSON.toJSONString(mail));
    }

    @Test
    public void testRecordUpdate() throws Exception {
        MPMailRecord mailRecord = new MPMailRecord();
        mailRecord.setReceiver("zhouqiang@gozap.com");
        mailRecord.setSessionId(12);
        mailRecord.setState(MPMailRecordState.MAIL_CREATED.getValue());
        mailRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
        Integer addResult = recordService.addRecord(mailRecord);
        assertEquals("done",1,addResult.intValue());

        MPMailRecord mailRecord2 = mailRecord;
        mailRecord2.setId(mailRecord.getId());
        mailRecord2.setReceiver("zhouqiang@gozap.com");
        mailRecord2.setSendTime(new Timestamp(System.currentTimeMillis()));
        mailRecord2.setMessageId("sdfsdf");
        mailRecord2.setState(MPMailRecordState.MAIL_SENT.getValue());
        Integer updateResult = recordService.updateRecord(mailRecord2);
        assertEquals("done",3,updateResult.intValue());

    }
}
