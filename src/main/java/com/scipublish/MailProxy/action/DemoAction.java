package com.scipublish.MailProxy.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-20
 * Time: PM4:09
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/demo")
public class DemoAction {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("hello")
    public void hello(HttpServletResponse response){
        logger.info("hello");
    }
}
