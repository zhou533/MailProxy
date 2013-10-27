package com.scipublish.MailProxy.service.impl;

import com.scipublish.MailProxy.mapper.MPMailSessionMapper;
import com.scipublish.MailProxy.model.MPMailSession;
import com.scipublish.MailProxy.service.MPSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-29
 * Time: PM4:12
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MPSessionServiceImpl implements MPSessionService {

    @Autowired
    private MPMailSessionMapper sessionMapper;

    @Override
    public Integer createSession(MPMailSession session) {

        return sessionMapper.addMailSession(session);
    }

    @Override
    public Integer updateSession(MPMailSession session) {
        return sessionMapper.updateMailSession(session);
    }

    @Override
    public List<MPMailSession> listSessions(Integer page, Integer size) {
        return sessionMapper.listMailSessions(page, size);
    }

    @Override
    public MPMailSession showSession(Integer id) {
        return sessionMapper.showMailSession(id);
    }
}
