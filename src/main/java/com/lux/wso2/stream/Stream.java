package com.lux.wso2.stream;

import com.lux.wso2.exceptions.InfrastructureException;
import com.lux.wso2.exceptions.StreamException;
import com.lux.wso2.stream.mon.LocalStatisticMonitor;
import com.lux.wso2.stream.mon.StatisticMonitor;
import com.lux.wso2.stream.mon.StatisticMonitorFactory;
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
        StatisticMonitor _monitor = null;
        try {
            _monitor = StatisticMonitorFactory.create(LocalStatisticMonitor.class);
        } catch (IllegalAccessException | InstantiationException e) {
            LOG.warn(e.getMessage());
            LOG.warn(StatisticMonitor.class.getName() + " DISABLED");
        }
        monitor = _monitor;
    }

    public StatisticMonitor getStatisticMonitor() {
        return monitor;
    }

    final DataPublisher getDataPublisher() {
        return dataPublisher;
    }

    public void stop() {
        Streams.stop(dataPublisher);
    }

    Stream setDataPublisher(final DataPublisher dataPublisher) throws StreamException {
        if (this.dataPublisher != null) {
            throw new StreamException("DataPublisher for " + getId() + " already set.");
        }
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
            if (monitor != null) {
                monitor.onEventPushed(wmbEvent);
            }
        } catch (AgentException e) {
            throw new InfrastructureException(e);
        }
    }


}
