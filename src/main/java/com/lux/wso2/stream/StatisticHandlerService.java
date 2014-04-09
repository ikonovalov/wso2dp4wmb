package com.lux.wso2.stream;

/**
 * Created by Igor on 09.04.2014.
 */
public interface StatisticHandlerService<T> {

    public void publish(T statistocContainer);

}
