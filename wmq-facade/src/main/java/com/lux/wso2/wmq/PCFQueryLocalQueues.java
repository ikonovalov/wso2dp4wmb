package com.lux.wso2.wmq;

import com.ibm.mq.pcf.MQCFIN;
import com.ibm.mq.pcf.MQCFST;
import com.ibm.mq.pcf.PCFParameter;

import static com.ibm.mq.constants.CMQC.*;

/**
 * Created by Igor on 22.05.2014.
 */
public class PCFQueryLocalQueues extends PCFQueryQueue {

    private final PCFParameter[] defaultParameters = {new MQCFST(MQCA_Q_NAME, "*"), new MQCFIN(MQIA_Q_TYPE, MQQT_LOCAL)};

    public PCFQueryLocalQueues() {
        super();
        setParameters(defaultParameters);
    }

    public PCFQueryLocalQueues(final String filter) {
        super();
        defaultParameters[0] = new MQCFST(MQCA_Q_NAME, filter);
        setParameters(defaultParameters);
    }

}
