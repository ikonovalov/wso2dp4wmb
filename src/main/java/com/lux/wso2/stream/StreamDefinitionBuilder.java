package com.lux.wso2.stream;

/**
 * Created by Igor on 02.04.2014.
 */
public class StreamDefinitionBuilder {

    private final String streamName = "DefaultStreamName";

    private final String streamVersion = "";

    private final String nickName = "";

    private final String description = "WebSphere Message Broker monitoring events";

    public final String define() {
        return "{" +
                "  'name':'" + getStreamName() + "'," +
                "  'version':'" + getStreamVerision() + "'," +
                "  'nickName': '" + getNickName() + "'," +
                "  'description': '" + getDescription() + "'," +
                "  'metaData':          [" + defineMetaData() + "]," +
                "  'payloadData':       [" + definePayloadData() + "]," +
                "  'correlationData':   [" + defineCorrelationData() + "]" +
                "}";

    }

    protected String getDescription() {
        return description;
    }

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

    protected String definePayloadData() {
        return  "          {'name':'bitstream','type':'STRING'}," +
                "          {'name':'encodingType','type':'STRING'}";
    }

    protected String defineCorrelationData() {
        return  "          {'name':'localTransactionId','type':'STRING'}," + //eventData.eventCorrelation
                "          {'name':'parentTransactionId', 'type':'STRING'}," +
                "          {'name':'globalTransactionId', 'type':'STRING'}";
    }

    public String getStreamName() {
        return streamName;
    }

    public String getStreamVerision() {
        return streamVersion;
    }

    public final String getStreamQualifiedName() {
        return getStreamName() + ":" + getStreamVerision();
    }

    public String getNickName() {
        return nickName;
    }

}