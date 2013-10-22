package com.scipublish.MailProxy.mapper;

import com.scipublish.MailProxy.model.MPMailRecord;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-27
 * Time: PM3:39
 * To change this template use File | Settings | File Templates.
 */
public interface MPMailRecordMapper {

    public Integer addMailRecord(MPMailRecord record);

    public Integer updateMailRecord(MPMailRecord record);
}
