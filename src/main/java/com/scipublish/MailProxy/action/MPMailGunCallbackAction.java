package com.scipublish.MailProxy.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-23
 * Time: PM5:41
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/callback")
public class MPMailGunCallbackAction {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("drop")
    public void dropCallback(HttpServletRequest request, HttpServletResponse response){

        String event = (String) request.getAttribute("event");
        String recipient = (String) request.getAttribute("recipient");
        String domain = (String) request.getAttribute("domain");
        String messageHeaders = (String) request.getAttribute("message-headers");
        String reason = (String) request.getAttribute("reason");
        String code = (String) request.getAttribute("code");
        String description = (String) request.getAttribute("description");
        String timestamp = (String) request.getAttribute("timestamp");

        logger.info(recipient + " " + event + " at " + timestamp
                + ". domain=" + domain
                + ", message-headers=" + messageHeaders
                + ", Reason for failure=" + reason
                + ", ESP response code=" + code
                + ", Detailed explanation=" + description);
    }


}
