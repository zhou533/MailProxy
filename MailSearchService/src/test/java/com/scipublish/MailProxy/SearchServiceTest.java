package com.scipublish.MailProxy;

import com.scipublish.MailProxy.elasticsearch.ESClient;
import com.scipublish.MailProxy.elasticsearch.ESSearchService;
import com.scipublish.MailProxy.elasticsearch.impl.ESIndexServiceImpl;
import com.scipublish.MailProxy.elasticsearch.impl.ESSearchServiceImpl;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-23
 * Time: PM12:17
 * com.scipublish.MailProxy
 * MailProxy
 */
public class SearchServiceTest {
    private ESSearchService esSearchService;

    public SearchServiceTest() {
        ESClient esClient = new ESClient("172.16.9.103", 9300, "elasticsearch2");
        this.esSearchService = new ESSearchServiceImpl(esClient);
    }
}
