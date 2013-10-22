package com.scipublish.MailProxy.service.impl;

import com.scipublish.MailProxy.mapper.MPMailRecordMapper;
import com.scipublish.MailProxy.model.MPMailRecord;
import com.scipublish.MailProxy.service.MPRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-29
 * Time: PM4:40
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MPRecordServiceImpl implements MPRecordService{

    @Autowired
    private MPMailRecordMapper mailRecordMapper;

    @Override
    public Integer addRecord(MPMailRecord record) {

        return mailRecordMapper.addMailRecord(record);
    }

    @Override
    public Integer updateRecord(MPMailRecord record) {

        Integer result = mailRecordMapper.updateMailRecord(record);
        return result;
    }
}
