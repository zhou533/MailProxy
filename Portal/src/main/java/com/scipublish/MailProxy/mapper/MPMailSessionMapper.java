package com.scipublish.MailProxy.mapper;

import com.scipublish.MailProxy.model.MPMailSession;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-26
 * Time: PM5:47
 * To change this template use File | Settings | File Templates.
 */
public interface MPMailSessionMapper {

    public Integer addMailSession(MPMailSession session);
    public Integer updateMailSession(MPMailSession session);
    public List<MPMailSession> listMailSessions(Integer page, Integer size);
    public MPMailSession showMailSession(Integer id);
}
