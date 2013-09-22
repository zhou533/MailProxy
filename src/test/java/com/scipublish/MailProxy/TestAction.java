package com.scipublish.MailProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-30
 * Time: AM11:06
 * To change this template use File | Settings | File Templates.
 */
public class TestAction implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(TestAction.class);

    private final Integer i;
    private final Double d;
    private final Boolean b;
    private final String s;
    private final List<Object> l;

    public TestAction(final Integer i, final Double d, final Boolean b, final String s, final List<Object> l)
    {
        this.i = i;
        this.d = d;
        this.b = b;
        this.s = s;
        this.l = l;
    }

    public void run()
    {
        log.info("TestAction.run() {} {} {} {} {}", new Object[]{this.i, this.d, this.b, this.s, this.l});
        try { Thread.sleep(100); } catch (Exception e){}
    }
}
