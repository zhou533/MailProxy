package com.scipublish.MailProxy.action;

import org.apache.struts2.dispatcher.multipart.MultiPartRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.Enumeration;

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

    @RequestMapping(value = "drop", method = RequestMethod.POST)
    public void dropCallback(MultipartHttpServletRequest request, HttpServletResponse response){

        String event = request.getParameter("event");
        String recipient = request.getParameter("recipient");
        String domain = request.getParameter("domain");
        String messageHeaders = request.getParameter("message-headers");
        String reason = request.getParameter("reason");
        String code = request.getParameter("code");
        String description = request.getParameter("description");
        String timestamp = request.getParameter("timestamp");

        logger.info(recipient + " " + event + " at " + timestamp
                + ". domain=" + domain
                + ", message-headers=" + messageHeaders
                + ", Reason for failure=" + reason
                + ", ESP response code=" + code
                + ", Detailed explanation=" + description);
    }

    @RequestMapping("delivered")
    public void deliveredCallback(HttpServletRequest request, HttpServletResponse response){

        String event = (String) request.getAttribute("event");
        String recipient = (String) request.getAttribute("recipient");
        String domain = (String) request.getAttribute("domain");
        String messageHeaders = (String) request.getAttribute("message-headers");
        String messageId = (String) request.getAttribute("Messsage-Id");
        String timestamp = (String) request.getAttribute("timestamp");

        logger.info(recipient + " " + event + " at " + timestamp
                + ". domain=" + domain
                + ", message-headers=" + messageHeaders
                + ", Messsage-Id=" + messageId);
    }

    @RequestMapping("bounce")
    public void bounceCallback(HttpServletRequest request, HttpServletResponse response){
        String event = (String) request.getParameter("event");
        logger.info("bounce " + event);
    }

    @RequestMapping("complaint")
    public void complaintCallback(HttpServletRequest request, HttpServletResponse response){
        String event = (String) request.getParameter("event");
        logger.info("complaint " + event);
    }

    @RequestMapping("opens")
    public void openCallback(HttpServletRequest request, HttpServletResponse response){
        String event = (String) request.getParameter("event");
        logger.info("opens " + event);
    }

    @RequestMapping("clicks")
    public void clickCallback(HttpServletRequest request, HttpServletResponse response){
        String event = (String) request.getParameter("event");
        logger.info("clicks " + event);
    }

}
