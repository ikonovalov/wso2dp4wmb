package com.lux.wso2.stream.mon;

import org.wso2.carbon.databridge.commons.Event;

/**
 * Created by Igor on 07.04.2014.
 */
public final class NOPStatisticMonitor implements StatisticMonitor {

    NOPStatisticMonitor() {
        super();
    }

    @Override
    public void onEventPushed(final Event event) {
        // just nop
    }

    @Override
    public void onEventPublishFailure(Event event) {

    }

    @Override
    public long getTotalEvents() {
        return -1;
    }


}
