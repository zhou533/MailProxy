package com.scipublish.MailProxy.service;

import com.scipublish.MailProxy.model.MPMail;
import com.scipublish.MailProxy.model.MPMailSession;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-29
 * Time: PM5:47
 * To change this template use File | Settings | File Templates.
 */
public interface MPSendService {

    public Integer sendMail(MPMailSession session, MPMail to);
}