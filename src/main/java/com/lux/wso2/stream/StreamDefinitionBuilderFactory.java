package com.lux.wso2.stream;

import com.lux.wso2.exceptions.StreamException;
import com.lux.wso2.stream.spec.WMBEventsStreamDefinitionBuilder;

/**
 * Created by Igor on 02.04.2014.
 */
public class StreamDefinitionBuilderFactory {


    public static StreamDefinitionBuilder createFor(final String builderName) throws StreamException {
        switch (builderName) {
            case "WMBEvent":{
                return new WMBEventsStreamDefinitionBuilder();
            }
            default: {
                throw new StreamException("Stream definition builder with name " + builderName + " not registered.");
            }
        }
    }
}
