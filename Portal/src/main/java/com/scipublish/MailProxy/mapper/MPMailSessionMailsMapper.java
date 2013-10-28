package com.scipublish.MailProxy.mapper;

import com.scipublish.MailProxy.model.MPMailSessionMails;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-28
 * Time: PM2:27
 * To change this template use File | Settings | File Templates.
 */
public interface MPMailSessionMailsMapper {

    public Integer addSessionMails(MPMailSessionMails sessionMails);
    public Integer updateSessionMails(MPMailSessionMails sessionMails);
    public MPMailSessionMails showSessionMails(Integer id);
    public List<MPMailSessionMails> listSessionMails(Integer sessionId, Integer page, Integer size);
}
