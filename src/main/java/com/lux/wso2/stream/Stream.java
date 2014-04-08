package com.lux.wso2.stream;

import com.lux.wso2.exceptions.InfrastructureException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.wso2.carbon.databridge.agent.thrift.DataPublisher;
import org.wso2.carbon.databridge.agent.thrift.exception.AgentException;
import org.wso2.carbon.databridge.commons.Event;

/**
 * Created by Igor on 07.04.2014.
 */
public class Stream {

    private static final Logger LOG = Logger.getLogger(Stream.class);

    private final String id;

    private boolean undefined = true;

    private DataPublisher dataPublisher;

    private final StatisticMonitor monitor;

    protected Stream(final String streamId) {
        id = streamId;
        if (StringUtils.isNotEmpty(streamId)) {
            undefined = false;
        }
        monitor = new LocalStatisticMonitor();
    }

    public StatisticMonitor getStatisticMonitor() {
        return monitor;
    }

    public final DataPublisher getDataPublisher() {
        return dataPublisher;
    }

    public void stop() {
        Streams.stop(dataPublisher);
    }

    Stream setDataPublisher(final DataPublisher dataPublisher) {
        this.dataPublisher = dataPublisher;
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

    public void publish(final Object[] metadata, final Object[] correlation, final Object[] payload) throws InfrastructureException {
        final Event wmbEvent = new Event(getId(), System.currentTimeMillis(), metadata, correlation, payload);
        try {
            getDataPublisher().publish(wmbEvent);
            monitor.eventPushed();
        } catch (AgentException e) {
            throw new InfrastructureException(e);
        }
    }


}
