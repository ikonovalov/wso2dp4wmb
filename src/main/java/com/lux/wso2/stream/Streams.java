package com.lux.wso2.stream;

import com.lux.wso2.DataPublisherHolder;
import com.lux.wso2.Endpoint;
import org.apache.log4j.Logger;
import org.wso2.carbon.databridge.agent.thrift.DataPublisher;
import org.wso2.carbon.databridge.agent.thrift.exception.AgentException;
import org.wso2.carbon.databridge.commons.exception.*;

import java.net.MalformedURLException;

/**
 * Created by Igor on 07.04.2014.
 */
public final class Streams {

    private static final Logger LOG = Logger.getLogger(Streams.class);

    public static Stream find(final DataPublisher dataPublisher, final StreamDefinitionBuilder definitionBuilder) throws AgentException {
        return new Stream(dataPublisher.findStreamId(definitionBuilder.getStreamName(), definitionBuilder.getStreamVerision()), definitionBuilder.getStreamQualifiedName()).setDataPublisher(dataPublisher);
    }

    public static Stream define(final DataPublisher dataPublisher, final StreamDefinitionBuilder definitionBuilder) throws StreamDefinitionException, DifferentStreamDefinitionAlreadyDefinedException, MalformedStreamDefinitionException, AgentException {
        return new Stream(dataPublisher.defineStream(definitionBuilder.define()), definitionBuilder.getStreamQualifiedName()).setDataPublisher(dataPublisher);
    }

    public static void stop(final DataPublisher dataPublisher) {
        dataPublisher.stop();
    }

    public static Stream defineIfNotExists(final Endpoint endpoint, final StreamDefinitionBuilder definitionBuilder) throws AgentException, MalformedStreamDefinitionException, StreamDefinitionException, DifferentStreamDefinitionAlreadyDefinedException, TransportException, AuthenticationException, MalformedURLException {
        final DataPublisher dataPublisher = DataPublisherHolder.INSTANCE.get(endpoint);
        long findStreamTimeStart = System.currentTimeMillis();
        Stream stream = Streams.find(dataPublisher, definitionBuilder);
        if (stream.undefined()) {
            LOG.info("Can't find stream. Define new stream '" + stream.getName() + "'");
            LOG.info("Define new stream\n\n" + definitionBuilder.define());
            stream = Streams.define(dataPublisher, definitionBuilder);
            LOG.info("New stream defined in " + (System.currentTimeMillis() - findStreamTimeStart) + "ms");
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Stream " + stream.getId() + " already exists. Timing " + (System.currentTimeMillis() - findStreamTimeStart) + "ms");
            }
        }
        return stream;
    }
}
