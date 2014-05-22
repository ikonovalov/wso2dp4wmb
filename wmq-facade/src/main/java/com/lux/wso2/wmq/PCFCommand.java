package com.lux.wso2.wmq;

import com.ibm.mq.pcf.PCFParameter;

/**
 * Created by Igor on 22.05.2014.
 */
public abstract class PCFCommand {

    private int command = -1;

    private PCFParameter[] parameters = null;

    protected PCFCommand(int command, final PCFParameter[] parameters) {
        this.command = command;
        this.parameters = parameters;
    }

    protected void setCommand(int command) {
        this.command = command;
    }

    protected void setParameters(PCFParameter[] parameters) {
        this.parameters = parameters;
    }
}
