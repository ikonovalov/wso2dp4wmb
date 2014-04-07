package com.lux.wso2.stream;

/**
 * Created by Igor on 07.04.2014.
 */
public class NOPStatisticMonitor implements StatisticMonitor {

    @Override
    public void eventPushed() {
        // just nop
    }

    @Override
    public long getTotalEvents() {
        return -1;
    }


}
