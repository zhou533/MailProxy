package com.scipublish.MailProxy.service;

import com.scipublish.MailProxy.model.MPMailSession;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-29
 * Time: PM3:57
 * To change this template use File | Settings | File Templates.
 */
public interface MPSessionService {

    /**
     *
     * @param session
     * @return id; when <0, session creating failed.
     */
    public Integer createSession(MPMailSession session);

    public Integer updateSession(MPMailSession session);

    public List<MPMailSession> listSessions(Integer page, Integer size);

    public MPMailSession showSession(Integer id);
}
