package com.lux.wso2;

import com.lux.wso2.stream.StreamDefinitionBuilder;
import com.lux.wso2.stream.StreamDefinitionBuilderFactory;
import com.lux.wso2.stream.WBMEventsStreamDefinitionBuilder;
import org.apache.log4j.Logger;
import org.wso2.carbon.databridge.agent.thrift.Agent;
import org.wso2.carbon.databridge.agent.thrift.DataPublisher;
import org.wso2.carbon.databridge.agent.thrift.exception.AgentException;
import org.wso2.carbon.databridge.commons.Event;
import org.wso2.carbon.databridge.commons.exception.*;

import java.net.MalformedURLException;
import java.net.SocketException;

/**
 * Hello world!
 */
public class WMBEventsAdapter {

    private static final Logger LOG = Logger.getLogger(WMBEventsAdapter.class);

    public static void main(String[] args)
            throws AgentException, MalformedStreamDefinitionException,
            StreamDefinitionException, DifferentStreamDefinitionAlreadyDefinedException,
            MalformedURLException,
            NoStreamDefinitionExistException,
            AuthenticationException,
            TransportException, SocketException {


        Agent agent = AgentHolder.INSTANCE.get();
        String host = "s540";

        String url = getProperty("url", "tcp://" + host + ":" + "7611");
        String username = getProperty("username", "admin");
        String password = getProperty("password", "admin");

        //create data publisher
        long dpBuildTime = System.currentTimeMillis();
        LOG.info("Creating DataPublisher...");
        DataPublisher dataPublisher = new DataPublisher(url, username, password, agent);
        LOG.info("DataPublisher created in " + (dpBuildTime = (System.currentTimeMillis() - dpBuildTime)) + "ms");

        long findStreamTime = System.currentTimeMillis();
        LOG.info("Finding stream...");
        StreamDefinitionBuilder streamDefinitionBuilder = StreamDefinitionBuilderFactory.createFor("WMBEvent");
        String streamId = dataPublisher.findStreamId(streamDefinitionBuilder.getStreamName(), streamDefinitionBuilder.getStreamVerision());

        if (streamId == null) {
            LOG.info("Can't find stream. Define new stream '" + streamDefinitionBuilder.getStreamQualifiedName() + "'");
            LOG.info("Define new stream\n\n" + streamDefinitionBuilder.define());
            streamId = dataPublisher.defineStream(streamDefinitionBuilder.define());
        } else {
            LOG.info("Stream " + streamId + " already exists");
        }

        //Publish event for a valid stream
        if (!"".equals(streamId)) {
            long stime = System.currentTimeMillis();
            final int messageCount = 1000;
            long totalTime = System.currentTimeMillis();
            for (int i = 0; i < messageCount; i++) {
                publishEvents(dataPublisher, streamId);
                if (i % 1000 == 0) {
                    LOG.debug("Write " + (i) + " events in " + (System.currentTimeMillis() - stime) + "ms");
                    stime = System.currentTimeMillis();
                }
            }
            LOG.info("Write " + messageCount + " messages in " + (System.currentTimeMillis() - totalTime) + "ms");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }

            dataPublisher.stop();
        }
    }

    private static void publishEvents(DataPublisher dataPublisher, String streamId) throws AgentException {


        Object[] meta = new Object[]{
                        "9001",
                        "6.1.0.3",
                        "MQ Input.transaction.Start",
                "MQ Input.TransactionStart",
                "",
                "",
                "",
                System.currentTimeMillis(), //creationTime
                Integer.valueOf(1),
                "BRK01",
                "default",
                "FirstWSO2WireTap",
                "MQ Input",
                "ComIbmMQInputNode",
                "" // terminal
        };


        Object[] payload = new Object[]{
                       "",
                       ""
        };

        Object[] correlation =  new Object[] {
                "7619af93-b275-405b-a8d3-ec7f3a558b97-3",
                "",
                ""
        };

        Event wmbEvent = new Event(streamId, System.currentTimeMillis(), meta, correlation, payload);
        dataPublisher.publish(wmbEvent);


    }

    private static String getProperty(String name, String def) {
        String result = System.getProperty(name);
        if (result == null || result.length() == 0 || result == "") {
            result = def;
        }
        return result;
    }
}