package com.lux.wso2.wmq;

import com.ibm.mq.MQException;
import com.ibm.mq.pcf.PCFAgent;
import com.ibm.mq.pcf.PCFParameter;

import java.io.IOException;

/**
 * Created by Igor on 23.05.2014.
 */
public interface PCFCommand<R> {

    R execute(final PCFAgent agent) throws MQException, IOException;

    PCFParameter[] getParameters();

    int getCommand();


}
