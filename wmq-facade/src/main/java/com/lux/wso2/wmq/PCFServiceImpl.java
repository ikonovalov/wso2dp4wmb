package com.lux.wso2.wmq;

import com.ibm.mq.MQException;
import com.ibm.mq.pcf.PCFAgent;
import com.lux.wso2.wmq.command.PCFQueryLocalQueues;
import com.lux.wso2.wmq.exceptions.PCFServiceException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 22.05.2014.
 */
public class PCFServiceImpl implements PCFService {

    private final PCFAgent agent;

    public PCFServiceImpl(PCFAgent agent) {
        this.agent = agent;
    }

    public List<Queue> getLocalQueues() throws PCFServiceException {
        PCFQueryLocalQueues localQueuesQuery = new PCFQueryLocalQueues();
        final List<Queue> queues = new ArrayList<>();
        try {
            List<String> qList = localQueuesQuery.execute(agent);
            for (String qName: qList) {
                queues.add(new Queue(qName));
            }
            return queues;
        } catch (MQException | IOException e) {
            throw new PCFServiceException("getLocalQueues failed", e);
        }
    }
}
