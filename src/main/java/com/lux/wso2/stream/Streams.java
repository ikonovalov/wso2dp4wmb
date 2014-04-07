package com.lux.wso2.stream;

import org.apache.log4j.Logger;
import org.wso2.carbon.databridge.agent.thrift.DataPublisher;
import org.wso2.carbon.databridge.agent.thrift.exception.AgentException;
import org.wso2.carbon.databridge.commons.exception.DifferentStreamDefinitionAlreadyDefinedException;
import org.wso2.carbon.databridge.commons.exception.MalformedStreamDefinitionException;
import org.wso2.carbon.databridge.commons.exception.StreamDefinitionException;

/**
 * Created by Igor on 07.04.2014.
 */
public final class Streams {

    private static final Logger LOG = Logger.getLogger(Streams.class);

    public static Stream find(final DataPublisher dataPublisher, final StreamDefinitionBuilder definitionBuilder) throws AgentException {
        return new Stream(dataPublisher.findStreamId(definitionBuilder.getStreamName(), definitionBuilder.getStreamVerision()), definitionBuilder.getStreamQualifiedName()).setPublisher(dataPublisher);
    }

    public static Stream define(final DataPublisher dataPublisher, final StreamDefinitionBuilder definitionBuilder) throws StreamDefinitionException, DifferentStreamDefinitionAlreadyDefinedException, MalformedStreamDefinitionException, AgentException {
        return new Stream(dataPublisher.defineStream(definitionBuilder.define()), definitionBuilder.getStreamQualifiedName()).setPublisher(dataPublisher);
    }

    public static Stream defineIfNotExists(final DataPublisher dataPublisher, final StreamDefinitionBuilder definitionBuilder) throws AgentException, MalformedStreamDefinitionException, StreamDefinitionException, DifferentStreamDefinitionAlreadyDefinedException {
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
