package com.scipublish.MailProxy.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public void dropCallback(HttpServletRequest request, HttpServletResponse response){

        /*String event = request.getParameter("event");
        if (null == event || !StringUtils.equalsIgnoreCase(event, "droped")){
            logger.info("Not DROP event!");
            return;
        }
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
                + ", Detailed explanation=" + description);*/
        logger.info("---------------drop");
        Enumeration<String> e = request.getParameterNames();
        while (e.hasMoreElements()){
            String name = e.nextElement();
            String value = request.getParameter(name);
            logger.info("name: " + name + " - " + value + "\n");
        }
    }

    @RequestMapping(value = "delivered", method = RequestMethod.POST)
    public void deliveredCallback(HttpServletRequest request, HttpServletResponse response){

        /*String event = request.getParameter("event");
        String recipient = request.getParameter("recipient");
        String domain = request.getParameter("domain");
        String messageHeaders = request.getParameter("message-headers");
        String messageId = request.getParameter("Messsage-Id");
        String timestamp = request.getParameter("timestamp");

        logger.info(recipient + " " + event + " at " + timestamp
                + ". domain=" + domain
                + ", message-headers=" + messageHeaders
                + ", Messsage-Id=" + messageId);*/
        logger.info("---------------delivered");
        Enumeration<String> e = request.getParameterNames();
        while (e.hasMoreElements()){
            String name = e.nextElement();
            String value = request.getParameter(name);
            logger.info("name: " + name + " - " + value + "\n");
        }
    }

    @RequestMapping(value = "bounce", method = RequestMethod.POST)
    public void bounceCallback(HttpServletRequest request, HttpServletResponse response){
        logger.info("---------------bounce");
        Enumeration<String> e = request.getParameterNames();
        while (e.hasMoreElements()){
            String name = e.nextElement();
            String value = request.getParameter(name);
            logger.info("name: " + name + " - " + value + "\n");
        }

    }

    @RequestMapping(value = "complaint", method = RequestMethod.POST)
    public void complaintCallback(HttpServletRequest request, HttpServletResponse response){
        logger.info("---------------complaint");
        Enumeration<String> e = request.getParameterNames();
        while (e.hasMoreElements()){
            String name = e.nextElement();
            String value = request.getParameter(name);
            logger.info("name: " + name + " - " + value + "\n");
        }
    }

    @RequestMapping(value = "opens", method = RequestMethod.POST)
    public void openCallback(HttpServletRequest request, HttpServletResponse response){
        logger.info("---------------opens");
        Enumeration<String> e = request.getParameterNames();
        while (e.hasMoreElements()){
            String name = e.nextElement();
            String value = request.getParameter(name);
            logger.info("name: " + name + " - " + value + "\n");
        }
    }

    @RequestMapping(value = "clicks", method = RequestMethod.POST)
    public void clickCallback(HttpServletRequest request, HttpServletResponse response){
        logger.info("---------------clicks");
        Enumeration<String> e = request.getParameterNames();
        while (e.hasMoreElements()){
            String name = e.nextElement();
            String value = request.getParameter(name);
            logger.info("name: " + name + " - " + value + "\n");
        }
    }

}
