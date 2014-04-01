package com.lux.wso2;

import org.apache.log4j.Logger;
import org.wso2.carbon.databridge.agent.thrift.Agent;
import org.wso2.carbon.databridge.agent.thrift.conf.AgentConfiguration;

/**
 * Created by Igor on 01.04.2014.
 */
public enum AgentHolder {

    INSTANCE;

    final Agent agent;

    private AgentHolder() {
        final Logger LOG = Logger.getLogger(AgentHolder.class);
        LOG.debug("Creating WSO2-WMB Agent");
        final AgentConfiguration agentConfiguration = new AgentConfiguration();
        String currentDir = System.getProperty("user.dir");

        // this is the bug CEP-656
        agentConfiguration.setTrustStore("wso2carbon");
        agentConfiguration.setTrustStorePassword(currentDir + "/src/main/resources/client-truststore.jks");

        agent = new Agent(agentConfiguration);
        LOG.debug("Agent created");
    }

    public Agent get() {
        return agent;
    }

}
