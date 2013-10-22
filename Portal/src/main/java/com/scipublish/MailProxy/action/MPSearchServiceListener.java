package com.scipublish.MailProxy.action;

import com.scipublish.MailProxy.search.MailProxySearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Controller;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-21
 * Time: PM5:15
 * com.scipublish.MailProxy.action
 * MailProxy
 */
@Controller
public class MPSearchServiceListener implements ApplicationListener{

    private static final Logger logger = LoggerFactory.getLogger(MPSearchServiceListener.class);

    @Autowired
    private MailProxySearch mpSearch;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

        if (applicationEvent instanceof ContextRefreshedEvent){
            //ApplicationContext启动或刷新时触发该事件
            //搜索服务初始化
            logger.info(">>>>>> Mail Proxy Search Service INIT <<<<<<");
            mpSearch.initSearchService();
            //logger.info(">>>>>>>>>>>>>>>>>>" + ((ContextRefreshedEvent) applicationEvent).getApplicationContext().getDisplayName());
        }
    }

}
