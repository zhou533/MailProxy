package com.scipublish.MailProxy.service.impl;

import com.scipublish.MailProxy.common.JesqueConfigBuilder;
import com.scipublish.MailProxy.common.MailGunConfiguration;
import com.scipublish.MailProxy.common.MailProxyConfiguration;
import com.scipublish.MailProxy.mailgun.MailGunAPI;
import com.scipublish.MailProxy.mailgun.MailGunSendBuilder;
import com.scipublish.MailProxy.model.MPMailSession;
import com.scipublish.MailProxy.service.MPSendService;
import net.greghaines.jesque.Config;
import net.greghaines.jesque.Job;
import net.greghaines.jesque.client.Client;
import net.greghaines.jesque.client.ClientImpl;
import net.greghaines.jesque.worker.Worker;
import net.greghaines.jesque.worker.WorkerImpl;
import static net.greghaines.jesque.utils.JesqueUtils.map;
import static net.greghaines.jesque.utils.JesqueUtils.entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-30
 * Time: PM12:07
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MPSendServiceImpl implements MPSendService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Worker sendWorker;
    private Config config;

    @Autowired
    private JesqueConfigBuilder jesqueConfigBuilder;

    @Autowired
    private MailGunConfiguration mailGunConfiguration;

    @Autowired
    private MailProxyConfiguration mailProxyConfiguration;


    public MPSendServiceImpl() {
        config = jesqueConfigBuilder.build();
        sendWorker = new WorkerImpl(config,
                Arrays.asList("send"), map(entry("SendMailRun", SendMailRun.class)));
        Thread sendThread = new Thread(sendWorker);
        sendThread.start();
    }

    @Override
    public Integer sendMail(MPMailSession session, String to) {

        final Job job = new Job("SendMailRun", new Object[]{

        });
        final Client client = new ClientImpl(config);
        client.enqueue("foo", job);
        client.end();
        return null;
    }

    /**
     *
     */
    private class SendMailRun implements Runnable{

        @Override
        public void run() {

            MailGunSendBuilder builder = new MailGunSendBuilder()
                    .setFrom("me@scipublish.com")
                    .addMail("zhouqiang@gozap.com")
                    .setSubject("Hello Mailgun")
                    .setHtml("<a href=\"http://www.w3school.com.cn\">W3School</a>").setTrackEnable(true)
                    .setTrackClicksEnable(true)
                    .setTrackOpensEnable(true);
            String result = MailGunAPI.SendMail(
                    mailGunConfiguration.getKey(),
                    mailProxyConfiguration.getDomain(),
                    builder);
        }
    }
}
