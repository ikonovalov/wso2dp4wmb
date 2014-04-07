package com.lux.wso2.stream;

import org.wso2.carbon.databridge.agent.thrift.DataPublisher;
import org.wso2.carbon.databridge.agent.thrift.exception.AgentException;
import org.wso2.carbon.databridge.commons.Event;

/**
 * Created by Igor on 07.04.2014.
 */
public class Stream {

    private final String id;

    private final String name;

    private boolean undefined = true;

    private DataPublisher publisher;

    protected Stream(final String streamId, final String streamName) {
        id = streamId;
        name = streamName;
        if (!"".equals(streamId)) {
            undefined = false;
        }
    }

    public final DataPublisher getPublisher() {
        return publisher;
    }

    Stream setPublisher(final DataPublisher dataPublisher) {
        publisher = dataPublisher;
        return this;
    }

    public final String getId() {
        return id;
    }

    public boolean defined() {
        return !undefined();
    }

    public boolean undefined() {
        return undefined;
    }

    public final String getName() {
        return name;
    }

    public void publish(final Object[] metadata, final Object[] correlation, final Object[] payload) throws AgentException {
        final Event wmbEvent = new Event(getId(), System.currentTimeMillis(), metadata, correlation, payload);
        getPublisher().publish(wmbEvent);
    }


}
