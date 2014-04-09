package com.lux.wso2.stream.spec;

import com.lux.wso2.stream.StreamDefinitionBuilder;

/**
 * Created by Igor on 02.04.2014.
 */
public class WMBEventsStreamDefinitionBuilder extends StreamDefinitionBuilder {

    public WMBEventsStreamDefinitionBuilder() {
        super();
    }

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
        return "1.2.1";
    }

    @Override
    public String getDescription() {
        return "WebSphere Message Broker monitoring events " + getStreamVerision();
    }

    @Override
    protected String defineMetaData() {
        return  "          {'name':'productVersion','type':'STRING'}," + // eventData.attributes
                "          {'name':'eventSchemaVersion','type':'STRING'}," +
                "          {'name':'eventSourceAddress','type':'STRING'}," +
                "          {'name':'eventName','type':'STRING'}," +         //eventData.eventIdentity
                "          {'name':'severity','type':'STRING'}," +
                "          {'name':'priority','type':'STRING'}," +
                "          {'name':'successDisposition','type':'STRING'}," +
                "          {'name':'creationTime','type':'LONG'}," +        //eventData.eventSequence
                "          {'name':'counter','type':'INT'}," +
                "          {'name':'broker','type':'STRING'}," +              //messageFlowData.broker.name
                "          {'name':'executionGroup','type':'STRING'}," +      //messageFlowData.executionGroup.name
                "          {'name':'messageFlow','type':'STRING'}," +         //messageFlowData.messageFlow.name
                "          {'name':'nodeLabel','type':'STRING'}," +           //messageFlowData.node
                "          {'name':'nodeType','type':'STRING'}," +
                "          {'name':'terminal','type':'STRING'}";
    }

    @Override
    protected String definePayloadData() {
        return  "          {'name':'bitstream','type':'STRING'}," +
                "          {'name':'encodingType','type':'STRING'}";
    }

    @Override
    protected String defineCorrelationData() {
        return  "          {'name':'localTransactionId','type':'STRING'}," + //eventData.eventCorrelation
                "          {'name':'parentTransactionId', 'type':'STRING'}," +
                "          {'name':'globalTransactionId', 'type':'STRING'}";
    }
}
