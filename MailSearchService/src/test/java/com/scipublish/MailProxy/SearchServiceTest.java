package com.scipublish.MailProxy;

import com.scipublish.MailProxy.elasticsearch.ESClient;
import com.scipublish.MailProxy.elasticsearch.ESSearchService;
import com.scipublish.MailProxy.elasticsearch.common.ESKeyword;
import com.scipublish.MailProxy.elasticsearch.common.ESKeywordType;
import com.scipublish.MailProxy.elasticsearch.common.ESOperationType;
import com.scipublish.MailProxy.elasticsearch.common.ESTimeRange;
import com.scipublish.MailProxy.elasticsearch.impl.ESIndexServiceImpl;
import com.scipublish.MailProxy.elasticsearch.impl.ESSearchServiceImpl;
import com.scipublish.MailProxy.result.MPSearchResult;
import com.scipublish.MailProxy.result.MPSearchServiceResult;
import com.scipublish.MailProxy.search.MailProxySearch;
import com.scipublish.MailProxy.search.impl.MailProxySearchImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void testSearch() throws Exception {
        //MailProxySearch search = new MailProxySearchImpl(null, this.esSearchService);
        //MPSearchServiceResult result = search.searchMails("role", null,null,null)
        ESKeyword keyword = new ESKeyword("role", ESKeywordType.SHOULD, ESOperationType.OR);
        List<ESKeyword> keywords = new ArrayList<ESKeyword>();
        keywords.add(keyword);
        ESTimeRange timeRange = new ESTimeRange("pub_date", "2010-01-01", "2010-03-01");
        MPSearchResult result = this.esSearchService.search("mailproxy", "mails", keywords, null, null,timeRange,null,1,25);

        if (result != null){
            System.out.println(result.toString());
        }
    }
}
