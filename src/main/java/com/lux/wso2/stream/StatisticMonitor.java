package com.lux.wso2.stream;

/**
 * Created by Igor on 07.04.2014.
 */
public interface StatisticMonitor {

    public abstract void eventPushed();

    public abstract long getTotalEvents();
}
