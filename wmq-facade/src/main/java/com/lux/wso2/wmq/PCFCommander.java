package com.lux.wso2.wmq;

import com.ibm.mq.pcf.PCFAgent;

/**
 * Created by Igor on 22.05.2014.
 */
public class PCFCommander {

    private final PCFAgent agent;

    public PCFCommander(PCFAgent agent) {
        this.agent = agent;
    }
}
