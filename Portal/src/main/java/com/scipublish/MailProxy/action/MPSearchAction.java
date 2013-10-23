package com.scipublish.MailProxy.action;

import com.scipublish.MailProxy.result.MPSearchResult;
import com.scipublish.MailProxy.result.MPSearchServiceResult;
import com.scipublish.MailProxy.search.MailProxySearch;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-23
 * Time: AM9:41
 * com.scipublish.MailProxy.action
 * MailProxy
 */
@Controller
@RequestMapping("/search")
public class MPSearchAction {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MailProxySearch mailProxySearch;

    @RequestMapping("search")
    public String search(Model model, HttpServletRequest request, HttpServletResponse response){
        logger.info("search........");
        Enumeration<String> e = request.getParameterNames();
        while (e.hasMoreElements()){
            String name = e.nextElement();
            String value = request.getParameter(name);
            logger.info("name: " + name + " - " + value + "\n");
        }

        String keywords = request.getParameter("keywords");
        String issn = request.getParameter("issn");
        String publisher = request.getParameter("publisher");
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String scopes = request.getParameter("scopes");

        logger.info("Year: " + start + "-" + end + " in " + scopes);

        MPSearchServiceResult result = mailProxySearch.searchMails(keywords, issn, publisher, scopes);
        if (result.getCode() != 0 || result.getObject() == null){
            model.addAttribute("count", 0);
            return "search";
        }

        MPSearchResult searchResult = (MPSearchResult)result.getObject();
        model.addAttribute("count", searchResult.getTotal());

        return "search";
    }
}
