package com.lux.wso2.stream;

/**
 * Created by Igor on 02.04.2014.
 */
public class WBMEventsStreamDefinitionBuilder extends StreamDefinitionBuilder {

    @Override
    public String getNickName() {
        return "WMBEvents";
    }

    @Override
    public String getStreamName() {
        return "bam_wmb_events";
    }

    @Override
    public String getStreamVerision() {
        return "1.0.0";
    }

    @Override
    public String getDescription() {
        return "WebSphere Message Broker monitoring events";
    }
}
