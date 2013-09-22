package com.scipublish.MailProxy;

import com.scipublish.MailProxy.common.JesqueConfigBuilder;
import net.greghaines.jesque.Job;
import net.greghaines.jesque.client.ClientImpl;
import net.greghaines.jesque.client.Client;
import net.greghaines.jesque.json.ObjectMapperFactory;
import static net.greghaines.jesque.utils.JesqueUtils.map;
import static net.greghaines.jesque.utils.JesqueUtils.entry;
import net.greghaines.jesque.worker.Worker;
import net.greghaines.jesque.worker.WorkerImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.greghaines.jesque.Config;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-29
 * Time: PM6:07
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml" })
public class JesqueTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JesqueConfigBuilder jesqueConfigBuilder;

    @Test
    public void testConfig() throws Exception {
        Config config = jesqueConfigBuilder.build();
        final Job job = new Job("TestAction", new Object[]{ 1, 2.3, true, "test", Arrays.asList("inner", 4.5)});
        final String json = ObjectMapperFactory.get().writeValueAsString(job);
        logger.info(json);
    }

    @Test
    public void testClient() throws Exception {
        Config config = jesqueConfigBuilder.build();
        final Job job = new Job("TestAction", new Object[]{ 1, 2.3, true, "test", Arrays.asList("inner", 4.5)});
        final Client client = new ClientImpl(config);
        client.enqueue("foo", job);
        client.end();
    }

    @Test
    public void testWork() throws Exception {
        Config config = jesqueConfigBuilder.build();
        final Worker worker = new WorkerImpl(config,
                Arrays.asList("foo"), map(entry("TestAction", TestAction.class)));
        final Thread workerThread = new Thread(worker);
        workerThread.start();

        workerThread.join();
    }
}
