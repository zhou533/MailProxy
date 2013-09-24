package com.scipublish.MailProxy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.scipublish.MailProxy.mapper.MPMailMapper;
import com.scipublish.MailProxy.model.MPMail;
import com.scipublish.MailProxy.service.MPMailService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-9-23
 * Time: PM12:30
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MPMailServiceImpl implements MPMailService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String KEY_MAIL_PREFIX = "com.scipublish.mailproxy.mail:";

    @Autowired
    private MPMailMapper mailMapper;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public Integer addMail(MPMail mail) {

        Integer result = mailMapper.addMail(mail);
        if (result > 0){
            setMailToRedis(mail);
        }
        return result;
    }

    @Override
    public Integer updateMail(MPMail mail) {
        return mailMapper.updateMail(mail);
    }

    @Override
    public MPMail getMail(String mail) {

        return getMailFromRedis(mail);
    }

    /***********/
    public boolean setMailToRedis(MPMail mail){
        Jedis jedis = jedisPool.getResource();
        try{
            String key = genMailKey(mail.getMail());
            jedis.set(key, JSON.toJSONString(mail));
        }finally {
            jedisPool.returnResource(jedis);
        }
        return true;
    }

    private MPMail getMailFromRedis(String mail){
        String result = "";
        Jedis jedis = jedisPool.getResource();
        try{
            String key = genMailKey(mail);
            result = jedis.get(key);
        }finally {
            jedisPool.returnResource(jedis);
        }

        if (StringUtils.isEmpty(result)){
            return null;
        }

        return JSON.parseObject(result, MPMail.class);
    }

    private String genMailKey(String mail){
        return KEY_MAIL_PREFIX + mail;
    }

}
