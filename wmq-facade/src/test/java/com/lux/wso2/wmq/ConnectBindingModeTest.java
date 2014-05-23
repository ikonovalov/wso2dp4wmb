package com.lux.wso2.wmq;

import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;
import org.junit.Test;

import java.util.Properties;

import static com.ibm.mq.constants.CMQC.*;
import static org.junit.Assert.*;

/**
 * Created by Igor on 22.05.2014.
 */
public class ConnectBindingModeTest {

    @Test
    public void connect() {
        final Properties properties = new Properties();
        properties.put(CHANNEL_PROPERTY, "SVRCON_WSO2");
        properties.put(TRANSPORT_PROPERTY, TRANSPORT_MQSERIES_BINDINGS);

        MQQueueManager qMgr = null;
        try {
            qMgr = ConnectionUtils.bind("QM01", "SVRCON_WSO2");
            assertTrue("MQ not binded.", qMgr != null);
        } catch (MQException e) {
            e.printStackTrace();
            fail("MQ not connected: " + e.getMessage());
        } finally {
            ConnectionUtils.disconnect(qMgr);
        }
    }
}
