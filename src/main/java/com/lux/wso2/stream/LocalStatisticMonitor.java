package com.lux.wso2.stream;

import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Igor on 07.04.2014.
 */
public class LocalStatisticMonitor implements StatisticMonitor {

    private static class PeriodicAverageCounter {

        private final TimeUnit timeUnit;

        private final long period;

        private final AtomicInteger counter;

        private final Timer timer;

        private double lastAverage;

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
         * @return
         */
        PeriodicAverageCounter start() {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    int currentValue = getCounterValue();
                    long perionInDestinationUtits = timeUnit.convert(period, TimeUnit.MILLISECONDS);
                    lastAverage = currentValue/perionInDestinationUtits;
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Periodic statistic: " + lastAverage + " in " + perionInDestinationUtits + " events " + timeUnit.name());
                    }
                    reset();
                }
            }, 0L, period);
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
         * @return
         */
        public int getCounterValue() {
            return counter.get();
        }

        /**
         * Get average counter value for <b>period</b>.
         * @return
         */
        public double getAverageForPeriod() {
            return lastAverage;
        }
    }

    private static final Logger LOG = Logger.getLogger(LocalStatisticMonitor.class);

    private AtomicLong eventCounter = new AtomicLong(0L);

    private final long creationTime;

    private final AtomicLong lastPublishedEventTime = new AtomicLong(0L);

    private final Timer timer;

    private Map<String, PeriodicAverageCounter> monitoringMap = new HashMap<>();

    LocalStatisticMonitor() {
        super();
        creationTime = System.currentTimeMillis();
        timer = new Timer("LOCAL_STAT_MON_TIMER", true);
        monitoringMap.put("MINUTES_MON", new PeriodicAverageCounter(timer, TimeUnit.MINUTES, 60000).start());
    }

    @Override
    public void eventPushed() {
        eventCounter.incrementAndGet();
        lastPublishedEventTime.lazySet(System.currentTimeMillis());
        for (PeriodicAverageCounter periodicCounterEntry : monitoringMap.values()) {
            periodicCounterEntry.increment();
        }
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
