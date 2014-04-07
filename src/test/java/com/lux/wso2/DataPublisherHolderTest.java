package com.lux.wso2;

import com.lux.wso2.exceptions.CommunicationException;
import com.lux.wso2.exceptions.InfrastructureException;
import com.lux.wso2.exceptions.WrongCredentialException;
import com.lux.wso2.infrastructure.DataPublisherHolder;
import org.junit.Test;
import org.wso2.carbon.databridge.agent.thrift.DataPublisher;

import java.net.MalformedURLException;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * Created by Igor on 07.04.2014.
 */
public class DataPublisherHolderTest extends EndpointSampleConnectionSettings {

    @Test // check with same endpoint
    public void checkDataPublisherIsSame() throws MalformedURLException, CommunicationException, InfrastructureException, WrongCredentialException {

        Endpoint endpoint = new Endpoint(url, username, password);
        DataPublisher dataPublisher1 = DataPublisherHolder.INSTANCE.get(endpoint);

        DataPublisher dataPublisher2 = DataPublisherHolder.INSTANCE.get(endpoint);

        assertSame("Two DataPublisher from cache is same (Endpoint is same instance)", dataPublisher1, dataPublisher2);
    }

    @Test // check with same endpoint
    public void checkDataPublisherIsSame2() throws MalformedURLException, CommunicationException, InfrastructureException, WrongCredentialException {

        Endpoint endpoint1 = new Endpoint(url, username, password);
        DataPublisher dataPublisher1 = DataPublisherHolder.INSTANCE.get(endpoint1);

        Endpoint endpoint2 = new Endpoint(url, username, password);
        DataPublisher dataPublisher2 = DataPublisherHolder.INSTANCE.get(endpoint2);

        assertNotSame("Endpoint has same instances. It's wrong scenario", endpoint1, endpoint2);
        assertSame("Two DataPublisher from cache is same (Endpoint is same bus instance is differend)", dataPublisher1, dataPublisher2);
    }
}
