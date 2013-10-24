package com.scipublish.MailProxy;

import com.scipublish.MailProxy.elasticsearch.common.ESTimeRange;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-24
 * Time: PM12:40
 * To change this template use File | Settings | File Templates.
 */
public class ServiceTest {

    @Test
    public void testTimeRange() throws Exception {
        ESTimeRange timeRange = new ESTimeRange("a", "2093-01", "2010e-02");

    }
}
