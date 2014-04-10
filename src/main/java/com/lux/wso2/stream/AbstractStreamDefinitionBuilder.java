package com.lux.wso2.stream;

import org.wso2.carbon.databridge.commons.utils.DataBridgeCommonsUtils;

/**
 * Created by Igor on 02.04.2014.
 */
public abstract class AbstractStreamDefinitionBuilder implements StreamDefinitionBuilder {

    @Override
    public final String define() {
        return "{" +
                "  'name':'" + getStreamName() + "'," +
                "  'version':'" + getStreamVersion() + "'," +
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


    @Override
    public final String getStreamId() {
        return DataBridgeCommonsUtils.generateStreamId(getStreamName(), getStreamVersion());
    }

    /**
     * Use name as default NickName
     * @return
     */
    public String getNickName() {
        return getStreamName();
    }

}