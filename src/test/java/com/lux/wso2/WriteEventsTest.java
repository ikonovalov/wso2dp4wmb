package com.lux.wso2;

import com.lux.wso2.exceptions.CommunicationException;
import com.lux.wso2.exceptions.InfrastructureException;
import com.lux.wso2.exceptions.StreamException;
import com.lux.wso2.exceptions.WrongCredentialException;
import com.lux.wso2.stream.Endpoint;
import com.lux.wso2.stream.Stream;
import com.lux.wso2.stream.StreamDefinitionBuilderFactory;
import com.lux.wso2.stream.Streams;
import org.junit.Test;

import java.net.MalformedURLException;

/**
 * Created by Igor on 08.04.2014.
 */
public class WriteEventsTest extends RandomDataFlooder implements EndpointSampleConnectionSettings {

    @Test
    public void write() throws MalformedURLException, StreamException, CommunicationException, InfrastructureException, WrongCredentialException, InterruptedException {

        Endpoint endpoint = new Endpoint(url, username, password);
        Stream stream = Streams.defineIfNotExists(endpoint, StreamDefinitionBuilderFactory.createFor("WMBEvent"));

        Object[] dataArray = newDataArrays();

        for (int z = 0; z < 1000; z++) {
            stream.publish((Object[]) dataArray[0], (Object[]) dataArray[1], (Object[]) dataArray[2]);
        }

        stream.stop();

    }

}
