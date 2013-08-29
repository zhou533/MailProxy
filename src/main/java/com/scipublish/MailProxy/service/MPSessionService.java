package com.scipublish.MailProxy.service;

import com.scipublish.MailProxy.model.MPMailSession;

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
     * @return
     */
    public Integer createSession(MPMailSession session);
}
