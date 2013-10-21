package com.scipublish.MailProxy;

import com.scipublish.MailProxy.elasticsearch.ESClient;
import com.scipublish.MailProxy.elasticsearch.ESIndexService;
import com.scipublish.MailProxy.elasticsearch.impl.ESIndexServiceImpl;
import com.scipublish.MailProxy.result.MPSearchServiceResult;
import com.scipublish.MailProxy.search.MailProxySearch;
import com.scipublish.MailProxy.search.impl.MailProxySearchImpl;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-21
 * Time: AM10:59
 * com.scipublish.MailProxy
 * MailProxy
 */
public class IndexServiceTest {
    private ESIndexService esIndexService;

    public IndexServiceTest() {
        ESClient esClient = new ESClient("172.16.9.103", 9300, "elasticsearch2");
        this.esIndexService = new ESIndexServiceImpl(esClient);
    }

    @Test
    public void testInit() throws Exception {
        MailProxySearch search = new MailProxySearchImpl(this.esIndexService, null);
        MPSearchServiceResult result =  search.initSearchService();
        assertEquals(MPSearchServiceResult.OK, result);
    }

    @Test
    public void testPutDate() throws Exception {
        MailProxySearch search = new MailProxySearchImpl(this.esIndexService, null);
        MPSearchServiceResult result =  search.putMailData(1,
                "Kuo-Chih Chou",
                "kcc126@126.com",
                "College of Materials Science and Engineering, Chongqing University, Chongqing 400045, China",
                "A new model for evaluating the electrical conductivity of nonferrous slag",
                "美国密苏里州的一个大型山顶水库.....看起来不是很安全",
                "",
                "",
                "Electrical conductivity of molten slag is an important physicochemical property for designing the refining process in electric smelting furnaces. Though conductivities of many slag systems have been measured, the quantitative relationships of conductivity with slag composition and temperature are still very limited. In this article, the Arrhenius law was used to describe the experimental data of conductivities for CaO-MgO-Al2O3-SiO2, CaO-Al2O3-SiO2, CaO-MnO-Al2O3-SiO2, as well as CaO-MgO-MnO-Al2O3-SiO2 systems, and it is found that activation energy can be expressed as a linear function of the content of components, where the optical basicity of slag must be within the range of 0.58 to 0.68.",
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
}
