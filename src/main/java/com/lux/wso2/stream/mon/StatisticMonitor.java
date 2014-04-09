package com.lux.wso2.stream.mon;

import org.wso2.carbon.databridge.commons.Event;

/**
 * Created by Igor on 07.04.2014.
 */
public interface StatisticMonitor {

    public abstract void onEventPushed(Event event);

    public abstract void onEventPublishFailure(Event event);

    public abstract long getTotalEvents();
}
