package com.scipublish.MailProxy.service;

import com.scipublish.MailProxy.model.MPMail;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-9-23
 * Time: PM12:29
 * To change this template use File | Settings | File Templates.
 */
public interface MPMailService {

    public Integer addMail(MPMail mail);

    public Integer updateMail(MPMail mail);

    public MPMail getMail(String mail);
}
