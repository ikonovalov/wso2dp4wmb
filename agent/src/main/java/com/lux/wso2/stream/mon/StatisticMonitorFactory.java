package com.lux.wso2.stream.mon;

/**
 * Created by Igor on 09.04.2014.
 */
public class StatisticMonitorFactory {

    public static StatisticMonitor create(Class clazz) throws IllegalAccessException, InstantiationException {
        if (StatisticMonitor.class.isAssignableFrom(clazz)) {
            Object instance = clazz.newInstance();
            return (StatisticMonitor) instance;
        } else {
            throw new InstantiationException("Can't create " + StatisticMonitor.class.getSimpleName() + " from " + clazz.getName());
        }
    }

    public static StatisticMonitor create(String clazzName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        return create(Class.forName(clazzName));
    }

}
