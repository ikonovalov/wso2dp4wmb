package com.lux.wso2.stream;

import com.lux.wso2.stream.spec.WBMEventsStreamDefinitionBuilder;

/**
 * Created by Igor on 02.04.2014.
 */
public class StreamDefinitionBuilderFactory {


    public static StreamDefinitionBuilder createFor(final String builderName) {
        switch (builderName) {
            case "WMBEvent":{
                return new WBMEventsStreamDefinitionBuilder();
            }
            default: {
                return new StreamDefinitionBuilder();
            }
        }
    }
}
