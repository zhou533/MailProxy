package com.scipublish.MailProxy.service.impl;

import com.scipublish.MailProxy.model.MPMail;
import com.scipublish.MailProxy.model.MPMailRecord;
import com.scipublish.MailProxy.model.MPMailRecordState;
import com.scipublish.MailProxy.model.MPMailState;
import com.scipublish.MailProxy.service.MPCallbackService;
import com.scipublish.MailProxy.service.MPMailService;
import com.scipublish.MailProxy.service.MPRecordService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-9-24
 * Time: PM5:57
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MPCallbackServiceImpl implements MPCallbackService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MPMailService mailService;

    @Autowired
    private MPRecordService recordService;

    @Override
    public void eventHandler(String event, String mail, Integer recordId, Integer mailId, String reason) {
        if (StringUtils.isEmpty(event) ||
                StringUtils.isEmpty(mail) ||
                recordId == null ||
                mailId == null){
            return;
        }

        MPMailRecordState recordState = MPMailRecordState.MAIL_DELIVERED;
        MPMailState mailState = MPMailState.STATE_OK;
        if (StringUtils.equalsIgnoreCase(event, "dropped")){

            if (StringUtils.equalsIgnoreCase(reason, "hardfail")){
                recordState = MPMailRecordState.MAIL_DROPED_HARDFAIL;
                mailState = MPMailState.STATE_DROPED_HARD;
            }else if (StringUtils.equalsIgnoreCase(reason, "maxfails")){
                recordState = MPMailRecordState.MAIL_DROPED_MAXFAIL;
                mailState = MPMailState.STATE_DROPED_MAX;
            }else {
                recordState = MPMailRecordState.MAIL_DROPED_OLD;
                mailState = MPMailState.STATE_DROPED_OLD;
            }
        }else if (StringUtils.equalsIgnoreCase(event, "complained")){
            recordState = MPMailRecordState.MAIL_COMPLAINT;
            mailState = MPMailState.STATE_COMPLAINT;
        }else if (StringUtils.equalsIgnoreCase(event, "bounced")){
            recordState = MPMailRecordState.MAIL_BOUNCE;
            mailState = MPMailState.STATE_BOUNCE;
        }

        if (mailState != MPMailState.STATE_OK){
            MPMail m = new MPMail(mail);
            m.setId(mailId);
            m.setState(mailState.getValue());
            Integer result = mailService.updateMail(m);
            if (result <= 0){

            }
        }

        //
        MPMailRecord mailRecord = new MPMailRecord();
        mailRecord.setId(recordId);
        mailRecord.setState(recordState.getValue());
        Integer result = recordService.updateRecord(mailRecord);
        if (result <= 0){

        }
    }

    @Override
    public void trackingClickHandler() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void trackingOpenHandler() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
