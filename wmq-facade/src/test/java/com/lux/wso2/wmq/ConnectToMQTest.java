package com.lux.wso2.wmq;

import com.ibm.mq.*;
import org.junit.Test;

import java.util.Properties;

import static com.ibm.mq.constants.MQConstants.*;

/**
 * Created by Igor on 21.05.2014.
 */
public class ConnectToMQTest {

    @Test
    public void connect() throws MQException {
        MQQueueManager qMgr = null;
        try {
            final Properties properties = new Properties();

            properties.put(HOST_NAME_PROPERTY, "s540");
            properties.put(PORT_PROPERTY, 1414);
            properties.put(CHANNEL_PROPERTY, "SVRCON_WSO2");
            properties.put(TRANSPORT_PROPERTY, TRANSPORT_MQSERIES_CLIENT);
            properties.put(USER_ID_PROPERTY, "Igor@localhost");


            qMgr = new MQQueueManager("QM01", properties);
            System.out.println("CCSID = " + qMgr.getCharacterSet());

            MQQueue queue = qMgr.accessQueue("Q1", MQOO_INQUIRE + MQOO_INPUT_SHARED);
            System.out.println("Depth = " + queue.getCurrentDepth());

            final MQMessage msg = new MQMessage();
            if (get(msg,queue)) {
                System.out.println("Message read");
            } else {
                System.out.println("Empty queue");
            }

        } catch (MQException e) {
            e.printStackTrace();
        } finally {
            if (qMgr != null)
                qMgr.close();
        }
    }

    public static boolean get(MQMessage message, MQQueue queue) throws MQException {
        final MQGetMessageOptions opts = new MQGetMessageOptions();
        opts.waitInterval = 1000;
        opts.options = MQGMO_WAIT;
        try {
            queue.get(message, opts);
        } catch (MQException e) {
            if (e.getReason() == MQRC_NO_MSG_AVAILABLE) {
                return false;
            } else {
                throw e;
            }
        }
        return true;
    }

}
