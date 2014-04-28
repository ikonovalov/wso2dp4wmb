package com.lux.wso2;

import com.lux.wso2.exceptions.CommunicationException;
import com.lux.wso2.exceptions.InfrastructureException;
import com.lux.wso2.exceptions.StreamException;
import com.lux.wso2.exceptions.WrongCredentialException;
import com.lux.wso2.stream.Endpoint;
import com.lux.wso2.stream.Stream;
import com.lux.wso2.stream.StreamDefinitionBuilderFactory;
import com.lux.wso2.stream.Streams;
import org.apache.log4j.Logger;

import java.net.MalformedURLException;

/**
 * Hello world!
 */
public class WMBEventsAdapter {

    private static final Logger LOG = Logger.getLogger(WMBEventsAdapter.class);

    public static void main(String[] args) throws MalformedURLException, StreamException, CommunicationException, InfrastructureException, WrongCredentialException {


        String host = "s540";
        String url = getProperty("url", "tcp://" + host + ":" + "7611");
        String username = getProperty("username", "admin");
        String password = getProperty("password", "admin");

        Endpoint endpoint = new Endpoint(url, username, password);

        Stream stream = Streams.defineIfNotExists(endpoint, StreamDefinitionBuilderFactory.createFor("WMBEvent"));

        //Publish event for a valid stream
        if (stream.defined()) {
            long startTime = System.currentTimeMillis();
            final int messageCount = 2000;
            long totalTime = System.currentTimeMillis();
            for (int i = 0; i < messageCount; i++) {
                publishEvents(stream);
                if (i % 1000 == 0) {
                    LOG.debug("Write " + (i) + " events in " + (System.currentTimeMillis() - startTime) + "ms");
                    startTime = System.currentTimeMillis();
                }
                silentSleep(80);
            }
            LOG.info("Write " + messageCount + " messages in " + (System.currentTimeMillis() - totalTime) + "ms");
            silentSleep(3000);

            stream.stop();
        }
        LOG.info("Statistic:\n" + stream.getStatisticMonitor());
    }

    private static void silentSleep(long period) {
        try {
            Thread.sleep(period);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void publishEvents(Stream stream) throws InfrastructureException {


        Object[] meta = new Object[]{
                        "9001",
                        "6.1.0.3",
                        "MQ Input.transaction.Start",
                "MQ Input.TransactionStart",
                "",
                "",
                "",
                System.currentTimeMillis(), //creationTime
                1,
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

        stream.publish(meta, correlation, payload);

    }

    private static String getProperty(String name, String def) {
        String result = System.getProperty(name);
        if (result == null || result.length() == 0 || result.equals("")) {
            result = def;
        }
        return result;
    }
}
