package com.scipublish.MailProxy;

import com.scipublish.MailProxy.result.MPSearchResult;
import com.scipublish.MailProxy.result.MPSearchServiceResult;
import com.scipublish.MailProxy.search.MailProxySearch;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-21
 * Time: PM4:55
 * com.scipublish.MailProxy
 * MailProxy
 */
public class HessianTest {
    private MailProxySearch mpSearch;

    public HessianTest() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-test.xml");
        this.mpSearch = (MailProxySearch)applicationContext.getBean("mpSearchService");
    }

    @Test
    public void testHessian() throws Exception {
        MPSearchServiceResult result = this.mpSearch.putMailData(5,
                "Kuo-Chih Chou",
                "kcc126@126.com",
                "College of Materials Science and Engineering, Chongqing University, Chongqing 400045, China",
                "A new model for evaluating the electrical conductivity of nonferrous slag",
                "美国密苏里州的一个大是很安全",
                "",
                "",
                "Electrical conductivity of molten slag is an important physicochemical.",
                "",
                "10.1016/S1674-4799(09)60087-X",
                "",
                "http://www.sciencedirect.com/science/article/pii/S167447990960087X",
                "International Journal of Minerals, Metallurgy and Materials",
                "16744799",
                "",
                "",
                "",
                "Volume 16, Issue 5, October 2009, Pages 500–504",
                "f7b0425544313c417a89d43b90d8e2cc");
        assertEquals(MPSearchServiceResult.OK.getCode(), result.getCode());
    }

    @Test
    public void testSearch() throws Exception {
        MPSearchServiceResult result = this.mpSearch.searchMails(null, "00207462", null,null, "<em class=highlight>", "</em>");
        assertEquals(MPSearchServiceResult.OK.getCode(), result.getCode());
        if (result.getObject() != null){
            MPSearchResult searchResult = (MPSearchResult)result.getObject();
            System.out.println(searchResult.toString());
        }
    }
}
