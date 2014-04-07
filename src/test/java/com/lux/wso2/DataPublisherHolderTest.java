package com.lux.wso2;

import org.junit.Test;
import org.wso2.carbon.databridge.agent.thrift.DataPublisher;
import org.wso2.carbon.databridge.agent.thrift.exception.AgentException;
import org.wso2.carbon.databridge.commons.exception.AuthenticationException;
import org.wso2.carbon.databridge.commons.exception.TransportException;

import java.net.MalformedURLException;

import static org.junit.Assert.*;

/**
 * Created by Igor on 07.04.2014.
 */
public class DataPublisherHolderTest extends EndpointSampleConnectionSettings {

    @Test // check with same endpoint
    public void checkDataPublisherIsSame() throws MalformedURLException, AgentException, AuthenticationException, TransportException {

        Endpoint endpoint = new Endpoint(url, username, password);
        DataPublisher dataPublisher1 = DataPublisherHolder.INSTANCE.get(endpoint);

        DataPublisher dataPublisher2 = DataPublisherHolder.INSTANCE.get(endpoint);

        assertSame("Two DataPublisher from cache is same (Endpoint is same instance)", dataPublisher1, dataPublisher2);
    }

    @Test // check with same endpoint
    public void checkDataPublisherIsSame2() throws MalformedURLException, AgentException, AuthenticationException, TransportException {

        Endpoint endpoint1 = new Endpoint(url, username, password);
        DataPublisher dataPublisher1 = DataPublisherHolder.INSTANCE.get(endpoint1);

        Endpoint endpoint2 = new Endpoint(url, username, password);
        DataPublisher dataPublisher2 = DataPublisherHolder.INSTANCE.get(endpoint2);

        assertNotSame("Endpoint has same instances. It's wrong scenario", endpoint1, endpoint2);
        assertSame("Two DataPublisher from cache is same (Endpoint is same bus instance is differend)", dataPublisher1, dataPublisher2);
    }
}
