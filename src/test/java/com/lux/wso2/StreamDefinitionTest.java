package com.lux.wso2;

import com.google.gson.Gson;
import com.lux.wso2.exceptions.StreamException;
import com.lux.wso2.stream.StreamDefinitionBuilder;
import com.lux.wso2.stream.StreamDefinitionBuilderFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.wso2.carbon.databridge.commons.StreamDefinition;

import static org.junit.Assert.*;

/**
 * Created by Igor on 10.04.2014.
 */
public class StreamDefinitionTest {

    private static final Logger LOG = Logger.getLogger(StreamDefinitionTest.class);

    @Test
    public void convertToGson() throws StreamException {
        Gson gson = new Gson();

        final StreamDefinitionBuilder sdb = StreamDefinitionBuilderFactory.createFor("WMBEvent");
        final String stringDefinition = sdb.define();
        final StreamDefinition wso2SD = gson.fromJson(stringDefinition, StreamDefinition.class);
        assertTrue(wso2SD.getName().equals(sdb.getStreamName()));
        assertTrue(wso2SD.getVersion().equals(sdb.getStreamVersion()));
        if (LOG.isDebugEnabled()) {
            LOG.debug(gson.toJson(wso2SD.getMetaData()));
        }
    }
}
