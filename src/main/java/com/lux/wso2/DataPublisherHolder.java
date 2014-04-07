package com.lux.wso2;

import org.apache.log4j.Logger;
import org.wso2.carbon.databridge.agent.thrift.DataPublisher;
import org.wso2.carbon.databridge.agent.thrift.exception.AgentException;
import org.wso2.carbon.databridge.commons.exception.AuthenticationException;
import org.wso2.carbon.databridge.commons.exception.TransportException;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Igor on 07.04.2014.
 */
public enum DataPublisherHolder {

    INSTANCE;

    private static final Logger LOG = Logger.getLogger(DataPublisherHolder.class);

    private final static String keySeparator = "#";

    private final static String EMPTY = "EMPTY";

    private Map<Endpoint, DataPublisher> dpCache = new HashMap<>();

    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public DataPublisher get(final Endpoint endpoint) throws MalformedURLException, AgentException, AuthenticationException, TransportException {
        rwLock.readLock().lock();
        long dpBuildTime = System.currentTimeMillis();
        DataPublisher dp = dpCache.get(endpoint);
        if (dp == null) {
            rwLock.readLock().unlock();
            rwLock.writeLock().lock();
            if (dpCache.get(endpoint) == null) {

                LOG.info("Creating new DataPublisher for " + endpoint);
                dp = new DataPublisher(endpoint.getUrl(), endpoint.getUsername(), endpoint.getPassword(), AgentHolder.INSTANCE.get());
                dpCache.put(endpoint, dp);
                LOG.info("DataPublisher created in " + (System.currentTimeMillis() - dpBuildTime) + "ms");
            }
            rwLock.readLock().lock();
            rwLock.writeLock().unlock();
        } else {
            LOG.debug("DataPublisher is already registred for " + endpoint + ". Timing " + (System.currentTimeMillis() - dpBuildTime) + "ms");
        }
        rwLock.readLock().unlock();
        return dp;
    }

}
