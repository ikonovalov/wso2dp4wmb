package com.lux.wso2;

import com.lux.wso2.stream.mon.LocalStatisticMonitor;
import com.lux.wso2.stream.mon.NOPStatisticMonitor;
import com.lux.wso2.stream.mon.StatisticMonitor;
import com.lux.wso2.stream.mon.StatisticMonitorFactory;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Igor on 09.04.2014.
 */
public class StatisticMonitorFactoryTest {

    @Test
    public void createLocalMonitor() throws InstantiationException, IllegalAccessException {
        StatisticMonitor mon = StatisticMonitorFactory.create(LocalStatisticMonitor.class);
        assertTrue(mon instanceof LocalStatisticMonitor);
    }

    @Test
    public void createLocalMonitorByName() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        StatisticMonitor mon = StatisticMonitorFactory.create(LocalStatisticMonitor.class.getName());
        assertTrue(mon instanceof LocalStatisticMonitor);
    }

    @Test
    public void createLocalMonitorWrong() throws InstantiationException, IllegalAccessException {
        StatisticMonitor mon = StatisticMonitorFactory.create(NOPStatisticMonitor.class);
        assertFalse(mon instanceof LocalStatisticMonitor);
    }

    @Test(expected = InstantiationException.class)
    public void createLocalMonitorWrong2() throws InstantiationException, IllegalAccessException {
        StatisticMonitorFactory.create(String.class);
    }

    @Test
    public void createNOPMonitorWrong() throws InstantiationException, IllegalAccessException {
        StatisticMonitor mon = StatisticMonitorFactory.create(NOPStatisticMonitor.class);
        assertTrue(mon instanceof NOPStatisticMonitor);
    }
}
