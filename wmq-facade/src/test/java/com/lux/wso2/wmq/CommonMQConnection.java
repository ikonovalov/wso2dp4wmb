package com.lux.wso2.wmq;

import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.pcf.PCFAgent;

import java.util.Properties;

import static com.ibm.mq.constants.CMQC.USER_ID_PROPERTY;
import static com.lux.wso2.wmq.ConnectionUtils.agent;
import static com.lux.wso2.wmq.ConnectionUtils.bind;

/**
 * Created by Igor on 23.05.2014.
 */
public class CommonMQConnection {

    private MQQueueManager queueManager;

    private PCFAgent agent;

    public synchronized MQQueueManager getQueueManager() throws MQException {
        if (queueManager == null) { // create new
            final Properties spec = new Properties();
            spec.put(USER_ID_PROPERTY, "Igor@localhost");
            queueManager = bind("QM01", "SVRCON_WSO2"); //remote("QM01", "SVRCON_WSO2", "localhost", 1414, spec);
        }
        return queueManager;

    }

    public synchronized PCFAgent getAgent() throws MQException {
        if (agent == null) {
            agent = agent(getQueueManager());
        }
        return agent;


    }

}
