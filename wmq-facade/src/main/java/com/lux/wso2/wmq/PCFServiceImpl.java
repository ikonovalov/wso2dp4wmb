package com.lux.wso2.wmq;

import com.ibm.mq.pcf.PCFAgent;

/**
 * Created by Igor on 22.05.2014.
 */
public class PCFServiceImpl implements PCFService {

    private final PCFAgent agent;

    public PCFServiceImpl(PCFAgent agent) {
        this.agent = agent;
    }
}
