package com.lux.wso2.wmq;

import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;

import java.util.Properties;
import java.util.logging.Logger;

import static com.ibm.mq.constants.CMQC.*;

/**
 * Created by Igor on 22.05.2014.
 */
public class ConnectionUtils {

    private static final Logger LOG = Logger.getLogger(ConnectionUtils.class.getName());

    private static MQQueueManager makeQMGR(final String qmgrName, final Properties spec) throws MQException {
        return new MQQueueManager(qmgrName, spec);
    }

    /**
     * Connect to local QMGR in BINDING mode.
     * @param qmgrName
     * @param channel
     * @return
     * @throws MQException
     */
    public static MQQueueManager bind(final String qmgrName, final String channel) throws MQException {
        final Properties properties = new Properties();
        return bind(qmgrName, channel, properties);
    }

    /**
     * Connect to local QMGR in BINDING mode.
     * @param qmgrName
     * @param channel
     * @param properties
     * @return
     * @throws MQException
     */
    public static MQQueueManager bind(final String qmgrName, final String channel, Properties properties) throws MQException {
        if (properties == null)
            properties = new Properties();
        properties.put(CHANNEL_PROPERTY, channel);
        properties.put(TRANSPORT_PROPERTY, TRANSPORT_MQSERIES_BINDINGS);
        MQQueueManager queueManager = makeQMGR(qmgrName, properties);
        return queueManager;
    }

    public static MQQueueManager remote(final String qmgrName, final String channel, final String host, final int port) throws MQException {
        final Properties spec = new Properties();
        return remote(qmgrName, channel, host, port, spec);
    }

    /**
     * Connect to QMGR via TCP/IP.
     * @param qmgrName - Queue manager name
     * @param channel - SVRCONN type channel name
     * @param host - hostname or IP address
     * @param port - port
     * @param spec - additional connection properties
     * @return
     * @throws MQException
     */
    public static MQQueueManager remote(final String qmgrName, final String channel, final String host, final int port, Properties spec) throws MQException {
        if (spec == null)
            spec = new Properties();
        spec.put(HOST_NAME_PROPERTY, host);
        spec.put(PORT_PROPERTY, port);
        spec.put(CHANNEL_PROPERTY, channel);
        spec.put(TRANSPORT_PROPERTY, TRANSPORT_MQSERIES_CLIENT);
        return makeQMGR(qmgrName, spec);
    }

    /**
     * Disconnect from queue manager.
     * @param manager
     */
    public static void disconnect(final MQQueueManager manager) {
        try {
            if (manager != null && manager.isConnected()) {
                manager.disconnect();
            }
        } catch (MQException mqe) {
            LOG.severe(mqe.getMessage());
        }
    }

}
