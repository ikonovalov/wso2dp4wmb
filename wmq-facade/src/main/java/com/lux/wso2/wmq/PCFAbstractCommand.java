package com.lux.wso2.wmq;

import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.pcf.MQCFH;
import com.ibm.mq.pcf.PCFAgent;
import com.ibm.mq.pcf.PCFParameter;

import java.io.IOException;
import java.util.logging.Logger;

import static com.ibm.mq.constants.CMQCFC.MQCFT_RESPONSE;

/**
 * Created by Igor on 22.05.2014.
 */
public abstract class PCFAbstractCommand<R> implements PCFCommand{

    private static final Logger LOG = Logger.getLogger(PCFAbstractCommand.class.getName());

    private int command = -1;

    private PCFParameter[] parameters = null;

    protected PCFAbstractCommand(int command, final PCFParameter[] parameters) {
        super();
        this.command = command;
        this.parameters = parameters;
    }

    protected PCFAbstractCommand() {
        super();
    }

    protected final void setCommand(int command) {
        this.command = command;
    }

    protected final void setParameters(PCFParameter[] parameters) {
        this.parameters = parameters;
    }

    public final PCFParameter[] getParameters() {
        return parameters.clone();
    }

    public final int getCommand() {
        return command;
    }

    public abstract R handleResult(final MQMessage[] mqMessages) throws MQException, IOException;

    public R execute(final PCFAgent agent) throws MQException, IOException {
        final MQMessage[] responses = agent.send(getCommand(), getParameters());
        final MQCFH qmCFH = new MQCFH(responses[0]);
        if (qmCFH.reason == 0 && qmCFH.type == MQCFT_RESPONSE) {
            return handleResult(responses);
        } else {
            LOG.severe(qmCFH.toString());
            for (int i = 0; i < qmCFH.parameterCount; i++) {
                LOG.severe(PCFParameter.nextParameter(responses[0]).toString());
            }
            throw new IllegalArgumentException("Wrong arguments was passed to PCF. " + qmCFH.toString());
        }
    }
}
