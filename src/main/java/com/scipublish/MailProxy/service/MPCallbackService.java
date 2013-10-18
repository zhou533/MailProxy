package com.scipublish.MailProxy.service;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-9-24
 * Time: PM2:28
 * To change this template use File | Settings | File Templates.
 */
public interface MPCallbackService {

    public void eventHandler(String event, String mail,
                             Integer recordId, Integer mailId,
                             String reason);
    public void trackingOpenHandler();
    public void trackingClickHandler();
}
