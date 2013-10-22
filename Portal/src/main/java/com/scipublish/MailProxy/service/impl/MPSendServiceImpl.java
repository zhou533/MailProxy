package com.scipublish.MailProxy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.scipublish.MailProxy.common.MPErrorCode;
import com.scipublish.MailProxy.common.MailGunConfiguration;
import com.scipublish.MailProxy.common.MailProxyConfiguration;
import com.scipublish.MailProxy.mailgun.MailGunAPI;
import com.scipublish.MailProxy.mailgun.MailGunSendBuilder;
import com.scipublish.MailProxy.model.*;
import com.scipublish.MailProxy.service.MPMailService;
import com.scipublish.MailProxy.service.MPRecordService;
import com.scipublish.MailProxy.service.MPSendService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-30
 * Time: PM12:07
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MPSendServiceImpl implements MPSendService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MailGunConfiguration mailGunConfiguration;

    @Autowired
    private MailProxyConfiguration mailProxyConfiguration;

    @Autowired
    private MPRecordService recordService;

    @Autowired
    private MPMailService mailService;


    @Override
    public Integer sendMail(MPMailSession session, MPMail to) {

        if (StringUtils.isEmpty(to.getMail())){
            return MPErrorCode.PARAM_MISSING;
        }

        if (session.getId() == null || session.getId() <= 0){
            logger.error("--Session " + session.getSession() +"-- invalid");
            return MPErrorCode.SESSION_INVALID;
        }

        //TODO check whether 'to' exist in table_mail or not
        //TODO if not, insert it into db
        MPMail mail = mailService.getMail(to.getMail());
        if (mail == null){
            mail = to;
            Integer result = mailService.addMail(mail);
            if (result <= 0){

                return MPErrorCode.MAIL_STORE_FAILED;
            }
        }

        if (mail.getMailState() != MPMailState.STATE_OK){
            return 0;
        }



        //record
        MPMailRecord mailRecord = new MPMailRecord();
        mailRecord.setReceiver(to.getMail());
        mailRecord.setSessionId(session.getId());
        mailRecord.setState(MPMailRecordState.MAIL_CREATED.getValue());
        mailRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
        Integer addResult = recordService.addRecord(mailRecord);
        if (addResult <= 0){
            logger.error("--Record for " + mailRecord.getReceiver() +"-- can't be created");
            return MPErrorCode.RECORD_ERR;
        }

        Map<String,Object> mpid = new HashMap<String, Object>();
        mpid.put("mailId",mail.getId());
        mpid.put("sessionId",session.getId());
        mpid.put("recordId",mailRecord.getId());

        MailGunSendBuilder builder = new MailGunSendBuilder()
                .setFrom(session.getFrom())
                .addMail(to)
                .setSubject(session.getSubject())
                .setHtml(session.getContent())
                .setTrackEnable(true)
                .setTrackClicksEnable(true)
                .setTrackOpensEnable(true)
                .addCustomVariable("mpid", mpid);

        String result = MailGunAPI.SendMail(
                mailGunConfiguration.getKey(),
                mailProxyConfiguration.getDomain(),
                builder);
        if (StringUtils.isEmpty(result)){
            return MPErrorCode.SEND_FAILED;
        }

        JSONObject object = JSON.parseObject(result);
        String messageId = object.getString("id");
        if (StringUtils.isEmpty(messageId)){
            logger.error("SendMail failed:" + object.getString("message"));
            return MPErrorCode.SEND_FAILED;
        }

        //
        mailRecord.setSendTime(new Timestamp(System.currentTimeMillis()));
        mailRecord.setMessageId(messageId);
        mailRecord.setState(MPMailRecordState.MAIL_SENT.getValue());
        Integer updateResult = recordService.updateRecord(mailRecord);
        if (updateResult <= 0){
            logger.error("--Record for " + mailRecord.getReceiver() +"-- can't be updated");
            return MPErrorCode.RECORD_ERR;
        }

        return 0;
    }

}
