package com.lux.wso2;

/**
 * Created by Igor on 02.04.2014.
 */
public class StreamDefinitionBuilder {

    public final String streamName = "bam_wmb_events";

    public String streamVersion = "1.0.0";

    public String define() {
        return "{" +
                "  'name':'" + getStreamName() + "'," +
                "  'version':'" + getStreamVerision() + "'," +
                "  'nickName': 'WMBEvents'," +
                "  'description': 'WebSphere Message Broker monitoring events'," +
                "  'metaData':[" +
                "          {'name':'productVersion','type':'STRING'}," + // eventData.attributes
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
                "          {'name':'terminal','type':'STRING'}" +
                "  ]," +
                "  'payloadData':[" +
                "          {'name':'bitstream','type':'STRING'}," +
                "          {'name':'encodingType','type':'STRING'}" +
                "  ]," +
                "  'correlationData':[" +                                   //eventData.eventCorrelation
                "          {'name':'localTransactionId','type':'STRING'}," +
                "          {'name':'parentTransactionId', 'type':'STRING'}," +
                "          {'name':'globalTransactionId', 'type':'STRING'}" +
                "  ]" +
                "}";

    }

    public String getStreamName() {
        return streamName;
    }

    public String getStreamVerision() {
        return streamVersion;
    }

    public String getStreamQualifiedName() {
        return getStreamName() + ":" + getStreamVerision();
    }

}