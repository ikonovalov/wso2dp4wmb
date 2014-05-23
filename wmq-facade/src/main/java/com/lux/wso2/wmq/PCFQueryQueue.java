package com.lux.wso2.wmq;

import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.pcf.MQCFIN;
import com.ibm.mq.pcf.MQCFSL;
import com.ibm.mq.pcf.MQCFST;
import com.ibm.mq.pcf.PCFParameter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.ibm.mq.constants.CMQC.*;
import static com.ibm.mq.constants.CMQCFC.MQCMD_INQUIRE_Q_NAMES;

/**
 * Created by Igor on 23.05.2014.
 */
public class PCFQueryQueue extends PCFAbstractCommand<List<String>> {

    private static final Logger LOG = Logger.getLogger(PCFQueryQueue.class.getName());

    /**
     * All queues types and name's filter = *
     */
    private final PCFParameter[] defaultParameters = {new MQCFST(MQCA_Q_NAME, "*"), new MQCFIN(MQIA_Q_TYPE, MQQT_ALL)};

    /**
     * Select all local queues.
     */
    public PCFQueryQueue() {
        super();
        setCommand(MQCMD_INQUIRE_Q_NAMES);
        setParameters(defaultParameters);
    }

    @Override
    public List<String> handleResult(MQMessage[] mqMessages) throws MQException, IOException {
        final List<String> queuesNames = new ArrayList<>();
        final MQCFSL cfsl = new MQCFSL(mqMessages[0]);
        for (String queueName : cfsl.strings) {
            queuesNames.add(queueName);
        }
        return queuesNames;
    }

}
