package com.lux.wso2.stream;

/**
 * Created by Igor on 02.04.2014.
 */
public class StreamDefinitionBuilder {

    private final String streamName = "EmptyStream";

    private final String streamVersion = "1.0.0";

    private final String nickName = "EmptyStream";

    private final String description = "Just empty stream without data fields";

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
        return  "";
    }

    protected String definePayloadData() {
        return  "";
    }

    protected String defineCorrelationData() {
        return  "";
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