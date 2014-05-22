package com.lux.wso2.wmq;

import com.ibm.mq.pcf.MQCFIN;
import com.ibm.mq.pcf.MQCFST;
import com.ibm.mq.pcf.PCFParameter;

import static com.ibm.mq.constants.CMQC.MQCA_Q_NAME;
import static com.ibm.mq.constants.CMQC.MQIA_Q_TYPE;
import static com.ibm.mq.constants.CMQC.MQQT_ALL;
import static com.ibm.mq.constants.CMQCFC.MQCMD_INQUIRE_Q_NAMES;

/**
 * Created by Igor on 22.05.2014.
 */
public class PCFQueryLocalQueues extends PCFCommand {

    private final PCFParameter[] parameters = {new MQCFST(MQCA_Q_NAME, "*"), new MQCFIN(MQIA_Q_TYPE, MQQT_ALL)};

    /**
     * Select all local queues.
     */
    public PCFQueryLocalQueues() {
        super(MQCMD_INQUIRE_Q_NAMES, new PCFParameter[] {new MQCFST(MQCA_Q_NAME, "*"), new MQCFIN(MQIA_Q_TYPE, MQQT_ALL)});
    }

}
