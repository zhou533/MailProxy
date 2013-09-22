package com.scipublish.MailProxy.mapper;

import com.scipublish.MailProxy.model.MPMail;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-9-22
 * Time: PM6:07
 * To change this template use File | Settings | File Templates.
 */
public interface MPMailMapper {

    public Boolean addMail(MPMail mail);
    //public Boolean updateMail(MPMail mail);
}
