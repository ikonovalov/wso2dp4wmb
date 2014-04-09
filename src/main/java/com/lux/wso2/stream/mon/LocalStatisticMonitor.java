package com.lux.wso2.stream.mon;

import com.lux.wso2.stream.StatisticHandlerService;
import org.apache.log4j.Logger;
import org.wso2.carbon.databridge.commons.Event;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Igor on 07.04.2014.
 */
public class LocalStatisticMonitor implements StatisticMonitor {

    private static class PeriodicAverageCounter<T> {

        private final TimeUnit timeUnit;

        private final long period;

        private final AtomicInteger counter;

        private final Timer timer;

        private double lastAverage;

        private StatisticHandlerService<String> statHandler;

        public PeriodicAverageCounter setStatisticHandeler(StatisticHandlerService<String> statHandler) {
            this.statHandler = statHandler;
            return this;
        }

        /**
         * Create new periodic statistic counter.
         * @param timer - execution timer instance
         * @param timeUnit - time units for calculation average value
         * @param period - milliseconds. ATTENTION: <b>period</b> must be a multiple of the <b>timeUnit</b>
         */
        private PeriodicAverageCounter(final Timer timer, final TimeUnit timeUnit, long period) {
            this.timeUnit = timeUnit;
            this.period = period;
            this.timer = timer;
            this.counter = new AtomicInteger(0);
        }

        /**
         * Submit event collecting task
         * @return current instance (like chain builder)
         */
        PeriodicAverageCounter start() {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    int currentValue = getCounterValue();
                    long periodInDestinationUtits = timeUnit.convert(period, TimeUnit.MILLISECONDS);
                    lastAverage = currentValue/periodInDestinationUtits;
                    if (statHandler != null) {
                        statHandler.publish("Periodic statistic: Average throughput " + lastAverage + " in " + periodInDestinationUtits + " events " + timeUnit.name().toLowerCase());
                    }
                    reset();
                }
            }, period, period);
            LOG.info("Periodic monitoring started with AVG task for " + timeUnit.convert(period, TimeUnit.MILLISECONDS) + " " + timeUnit.name().toLowerCase());
            return this;
        }

        /**
         * Increment counter value.
         */
        void increment() {
            counter.incrementAndGet();
        }

        /**
         * Reset counter value.
         */
        void reset() {
            counter.lazySet(0);
        }

        /**
         * Get current counter value.
         * @return current counter value.
         */
        public int getCounterValue() {
            return counter.get();
        }

        /**
         * Get average counter value for <b>period</b>.
         * @return average counter value.
         */
        public double getAverageForPeriod() {
            return lastAverage;
        }
    }

    private static final Logger LOG = Logger.getLogger(LocalStatisticMonitor.class);

    private final AtomicLong eventCounter = new AtomicLong(0L);

    private final AtomicLong eventFailCounter = new AtomicLong(0L);

    private final long creationTime;

    private final AtomicLong lastPublishedEventTime = new AtomicLong(0L);

    private final Timer timer;

    private Map<String, PeriodicAverageCounter> counterMonitoringMap = new HashMap<>();

    LocalStatisticMonitor(Properties properties) {
        super();
        creationTime = System.currentTimeMillis();
        timer = new Timer("LOCAL_STAT_MON_TIMER", true);
    }

    LocalStatisticMonitor() {
        super();
        creationTime = System.currentTimeMillis();
        timer = new Timer("LOCAL_STAT_MON_TIMER", true);
        counterMonitoringMap.put("MINUTES_MON", new PeriodicAverageCounter(timer, TimeUnit.MINUTES, 60000).setStatisticHandeler(new StatisticHandlerService<String>() {
            @Override
            public void publish(String statistocContainer) {
                LOG.debug(statistocContainer);
            }
        }).start());
    }

    @Override
    public void onEventPushed(final Event event) {
        eventCounter.incrementAndGet();
        lastPublishedEventTime.lazySet(System.currentTimeMillis());
        for (PeriodicAverageCounter periodicCounterEntry : counterMonitoringMap.values()) {
            periodicCounterEntry.increment();
        }
    }

    @Override
    public void onEventPublishFailure(Event event) {
        eventFailCounter.incrementAndGet();
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
                "eventFailureCounter=" + eventFailCounter +
                '}';
    }
}
