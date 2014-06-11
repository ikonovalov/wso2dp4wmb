package com.lux.wso2.wmq;

import com.ibm.mq.MQException;
import com.ibm.mq.pcf.PCFAgent;
import com.lux.wso2.wmq.command.PCFQueryLocalQueues;
import com.lux.wso2.wmq.command.PCFQueryQueue;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Igor on 23.05.2014.
 */
public class PCFRunTest extends CommonMQConnection {

    private static final Logger LOG = Logger.getLogger(PCFRunTest.class.getName());

    @Test
    public void listQueuesByCommandsWrapper() throws MQException, IOException {
        PCFAgent agent = getAgent();
        PCFQueryQueue queryQueue = new PCFQueryQueue();
        List<String> queues = queryQueue.execute(agent);
        final StringBuilder sb = new StringBuilder();
        for (String queue : queues) {
            sb.append(queue).append('\n');
        }
        LOG.info("listQueuesByCommandsWrapper:");
        LOG.info(sb.toString());
        LOG.info("Total " + queues.size() + " queues found");
    }

    @Test
    public void listLocalQueuesByCommandsWrapper() throws MQException, IOException {
        PCFAgent agent = getAgent();
        PCFQueryQueue queryQueue = new PCFQueryLocalQueues();
        List<String> queues = queryQueue.execute(agent);
        final StringBuilder sb = new StringBuilder();
        for (String queue : queues) {
            sb.append(queue).append('\n');
        }
        LOG.info("listLocalQueuesByCommandsWrapper:");
        LOG.info(sb.toString());
        LOG.info("Total " + queues.size() + " queues found");
    }

    @Test
    public void listLocalQueuesWithFilterByCommandsWrapper() throws MQException, IOException {
        PCFAgent agent = getAgent();
        PCFQueryQueue queryQueue = new PCFQueryLocalQueues("Q*");
        List<String> queues = queryQueue.execute(agent);
        final StringBuilder sb = new StringBuilder();
        for (String queue : queues) {
            sb.append(queue).append('\n');
        }
        LOG.info("listLocalQueuesWithFilterByCommandsWrapper:");
        LOG.info(sb.toString());
        LOG.info("Total " + queues.size() + " queues found");
    }

}
