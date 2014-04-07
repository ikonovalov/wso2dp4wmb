package com.lux.wso2.stream;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Igor on 07.04.2014.
 */
public class LocalStatisticMonitor implements StatisticMonitor {

    private AtomicLong eventCounter = new AtomicLong(0L);

    private final long creationTime;

    private AtomicLong lastPublishedEventTime = new AtomicLong(0L);

    LocalStatisticMonitor() {
        super();
        creationTime = System.currentTimeMillis();
    }

    @Override
    public void eventPushed() {
        eventCounter.incrementAndGet();
        lastPublishedEventTime.lazySet(System.currentTimeMillis());
    }

    @Override
    public long getTotalEvents() {
        return eventCounter.get();
    }

    @Override
    public String toString() {
        return "LocalStatisticMonitor{" +
                "eventCounter=" + eventCounter +
                ", creationTime=" + new Date(creationTime) +
                ", lastPublishedEventTime=" + new Date(lastPublishedEventTime.get()) +
                '}';
    }
}
