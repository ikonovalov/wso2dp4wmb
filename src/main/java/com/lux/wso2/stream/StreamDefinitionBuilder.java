package com.lux.wso2.stream;

/**
 * Created by Igor on 02.04.2014.
 */
public abstract class StreamDefinitionBuilder {

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
        return "";
    }

    protected abstract String defineMetaData();

    protected abstract String definePayloadData();

    protected abstract String defineCorrelationData();

    public abstract String getStreamName();

    public abstract String getStreamVerision();

    public final String getStreamQualifiedName() {
        return getStreamName() + ":" + getStreamVerision();
    }

    public String getNickName() {
        return getStreamName();
    }

}