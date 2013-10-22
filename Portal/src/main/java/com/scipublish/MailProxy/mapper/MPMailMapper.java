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

    /**
     *
     * @param mail
     * @return >0 succeed, otherwise failed
     */
    public Integer addMail(MPMail mail);

    /**
     *
     * @param mail
     * @return >0 succeed, otherwise failed
     */
    public Integer updateMail(MPMail mail);
}
