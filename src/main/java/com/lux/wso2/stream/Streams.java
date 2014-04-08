package com.lux.wso2.stream;

import com.lux.wso2.exceptions.CommunicationException;
import com.lux.wso2.exceptions.InfrastructureException;
import com.lux.wso2.exceptions.StreamException;
import com.lux.wso2.exceptions.WrongCredentialException;
import com.lux.wso2.infrastructure.DataPublisherHolder;
import org.apache.log4j.Logger;
import org.wso2.carbon.databridge.agent.thrift.DataPublisher;
import org.wso2.carbon.databridge.agent.thrift.exception.AgentException;
import org.wso2.carbon.databridge.commons.exception.DifferentStreamDefinitionAlreadyDefinedException;
import org.wso2.carbon.databridge.commons.exception.MalformedStreamDefinitionException;
import org.wso2.carbon.databridge.commons.exception.StreamDefinitionException;

import java.net.MalformedURLException;

/**
 * Created by Igor on 07.04.2014.
 */
public final class Streams {

    private static final Logger LOG = Logger.getLogger(Streams.class);

    /**
     * Find existing stream definition.
     * @param dataPublisher associated DataPublisher
     * @param definitionBuilder stread definition metadata holder
     * @return Stream
     * @throws AgentException
     */
    public static Stream find(final DataPublisher dataPublisher, final StreamDefinitionBuilder definitionBuilder) throws InfrastructureException {
        try {
            return new Stream(dataPublisher.findStreamId(definitionBuilder.getStreamName(), definitionBuilder.getStreamVerision())).setDataPublisher(dataPublisher);
        } catch (AgentException e) {
            throw new InfrastructureException(e);
        }
    }

    /**
     * Define new stream.
     * @param dataPublisher
     * @param definitionBuilder
     * @return
     * @throws InfrastructureException
     * @throws StreamException
     */
    public static Stream define(final DataPublisher dataPublisher, final StreamDefinitionBuilder definitionBuilder) throws InfrastructureException, StreamException {
        try {
            return new Stream(dataPublisher.defineStream(definitionBuilder.define())).setDataPublisher(dataPublisher);
        } catch (AgentException e) {
            throw new InfrastructureException(e);
        }  catch (StreamDefinitionException | DifferentStreamDefinitionAlreadyDefinedException | MalformedStreamDefinitionException e) {
            throw new StreamException(e);
        }
    }

    /**
     * Stop DataPublisher thread and related.
     * @param dataPublisher
     */
    public static void stop(final DataPublisher dataPublisher) {
        dataPublisher.stop();
    }

    /**
     * Find + define if not exists
     * @param endpoint
     * @param definitionBuilder
     * @return
     * @throws MalformedURLException
     * @throws CommunicationException
     * @throws InfrastructureException
     * @throws WrongCredentialException
     * @throws StreamException
     */
    public static Stream defineIfNotExists(final Endpoint endpoint, final StreamDefinitionBuilder definitionBuilder) throws MalformedURLException, CommunicationException, InfrastructureException, WrongCredentialException, StreamException {
        final DataPublisher dataPublisher = DataPublisherHolder.INSTANCE.get(endpoint);
        long findStreamTimeStart = System.currentTimeMillis();
        Stream stream = Streams.find(dataPublisher, definitionBuilder);
        if (stream.undefined()) {
            LOG.info("Can't find stream. Define new stream '" + definitionBuilder.getStreamQualifiedName() + "'");
            LOG.debug("Define new stream\n\n" + definitionBuilder.define());
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
