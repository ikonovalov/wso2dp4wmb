package com.lux.wso2.infrastructure;

import com.lux.wso2.stream.Endpoint;
import com.lux.wso2.exceptions.CommunicationException;
import com.lux.wso2.exceptions.InfrastructureException;
import com.lux.wso2.exceptions.WrongCredentialException;
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

    /**
     * Get DataPublisher from cache. This is thread-safe method.
     * @param endpoint as a cache's key
     * @return new DataPublisher or stored DataPublisher
     * @throws MalformedURLException
     * @throws AgentException
     * @throws AuthenticationException
     * @throws TransportException
     */
    public DataPublisher get(final Endpoint endpoint) throws MalformedURLException, WrongCredentialException, InfrastructureException, CommunicationException {
        rwLock.readLock().lock();
        long dpBuildTime = System.currentTimeMillis();
        DataPublisher dp = dpCache.get(endpoint);
        if (dp == null) {
            rwLock.readLock().unlock();
            rwLock.writeLock().lock();
            if (dpCache.get(endpoint) == null) {

                LOG.info("Creating new DataPublisher for " + endpoint);
                try {
                    dp = new DataPublisher(endpoint.getUrl(), endpoint.getUsername(), endpoint.getPassword(), AgentHolder.INSTANCE.get());
                } catch (AuthenticationException e) {
                    throw new WrongCredentialException(e);
                } catch (AgentException e) {
                    throw new InfrastructureException(e);
                } catch (TransportException e) {
                    throw new CommunicationException(e);
                }
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
