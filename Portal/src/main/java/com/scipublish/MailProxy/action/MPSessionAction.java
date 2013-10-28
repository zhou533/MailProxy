package com.scipublish.MailProxy.action;

import com.scipublish.MailProxy.common.MPErrorCode;
import com.scipublish.MailProxy.common.MPUtils;
import com.scipublish.MailProxy.common.MailProxyWeb;
import com.scipublish.MailProxy.model.MPMailSession;
import com.scipublish.MailProxy.model.MPMailSessionMails;
import com.scipublish.MailProxy.service.MPSessionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-29
 * Time: PM3:09
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/session")
public class MPSessionAction {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MPSessionService sessionService;

    @RequestMapping("create")
    public String createSession(Model model,
                                HttpServletRequest request,
                                HttpServletResponse response){

        logger.info("create session");
        Enumeration<String> e = request.getParameterNames();
        while (e.hasMoreElements()){
            String name = e.nextElement();
            String value = request.getParameter(name);
            logger.info("name: " + name + " - " + value + "\n");
        }

        String session = request.getParameter("session");
        String from = request.getParameter("from");
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");
        if (StringUtils.isEmpty(session) ||
                StringUtils.isEmpty(from) ||
                StringUtils.isEmpty(subject) ||
                StringUtils.isEmpty(content)){
            return "session_mails";
        }

        MPMailSession mailSession = new MPMailSession();
        mailSession.setSession(session);
        mailSession.setFrom(from);
        mailSession.setSubject(subject);
        mailSession.setContent(content);
        mailSession.setCreateTime(new Timestamp(System.currentTimeMillis()));
        Integer result = sessionService.createSession(mailSession);
        if (result <= 0){
            logger.info("Create session failed.");
            return "session_mails";
        }

        List<String> variables = MPUtils.parseVariables(content);
        logger.info("Parse variables:" + StringUtils.join(variables,','));
        model.addAttribute("variables", variables);

        return "session_mails";
    }

    @RequestMapping("list")
    public String showSessions(Integer page,
                               Model model,
                               HttpServletRequest request,
                               HttpServletResponse response){
        logger.info("list sessions");

        if (page == null || page < 1){
            page = 1;
        }

        List<MPMailSession> sessions = sessionService.listSessions(page, 25);


        return "sessions";
    }


    /**
     *
     */
    @RequestMapping("mails/add")
    public void addSesssionMails(Integer sessionId,
                                 Integer type,
                                 String parmas,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        if (null == sessionId || null == type || StringUtils.isEmpty(parmas)){
            MailProxyWeb.outputErrResult(MPErrorCode.PARAM_MISSING,"Params Missing", response);
            return;
        }

        if (sessionId <= 0 || (type != 0 && type != 1)){
            MailProxyWeb.outputErrResult(MPErrorCode.PARAM_INVALID,"Params Invalid", response);
            return;
        }

        MPMailSessionMails sessionMails = new MPMailSessionMails();
        sessionMails.setSessionId(sessionId);
        sessionMails.setType(type);
        sessionMails.setParams(parmas);
        Integer result = sessionService.addSessionMails(sessionMails);
        if (result <= 0){
            MailProxyWeb.outputErrResult(MPErrorCode.SESSION_INVALID,"Session mails add failed", response);
            return;
        }

        MailProxyWeb.outputOKResult("OK", sessionMails, response);
    }
}