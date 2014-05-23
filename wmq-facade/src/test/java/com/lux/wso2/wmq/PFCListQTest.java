package com.lux.wso2.wmq;

import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.pcf.*;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

import static com.ibm.mq.constants.MQConstants.*;
import static com.lux.wso2.wmq.ConnectionUtils.*;

/**
 * Created by Igor on 22.05.2014.
 */
public class PFCListQTest extends CommonMQConnection{

    @Test
    public void show() throws MQException, IOException {
        PCFAgent agent = getAgent();
        PCFParameter[] parameters =
                {
                        new MQCFST(MQCA_Q_NAME, "Q*"),
                        new MQCFIN(MQIA_Q_TYPE, MQQT_ALL)
                };
        MQMessage[] responses;
        MQCFH cfh;
        MQCFSL cfsl;

        try {
            System.out.println("Connected.");

            // Use the agent to send the request

            System.out.print("Sending PCF request... ");
            responses = agent.send(MQCMD_INQUIRE_Q_NAMES, parameters);
            System.out.println("Received reply with " + responses.length + " messages.");
            cfh = new MQCFH(responses[0]);
            put(responses[0]);
            System.out.println(cfh);
            System.out.println("\n\n\n");
            // Check the PCF header (MQCFH) in the first response message
            if (cfh.reason == 0 && cfh.type == MQCFT_RESPONSE) {
                cfsl = new MQCFSL(responses[0]);
                System.out.println(cfsl);
                System.out.println("Queue names:");

                for (int i = 0; i < cfsl.strings.length; i++) {
                    System.out.println("\t" + cfsl.strings[i]);
                }
            } else {
                System.out.println(cfh);

                // Walk through the returned parameters describing the error

                for (int i = 0; i < cfh.parameterCount; i++) {
                    System.out.println(PCFParameter.nextParameter(responses[0]));
                }
            }

            // Disconnect

            System.out.print("Disconnecting... ");
            disconnect(agent);
            System.out.println("Done.");
        } catch (ArrayIndexOutOfBoundsException abe) {
            System.out.println("Usage: \n" +
                    "\tjava ListQueueNames queue-manager\n" +
                    "\tjava ListQueueNames host port channel");
        } catch (NumberFormatException nfe) {

        } catch (MQException mqe) {
            System.err.println(mqe);
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

    public static void put(final MQMessage message) throws MQException, IOException {
        final Properties spec = new Properties();
        spec.put(USER_ID_PROPERTY, "Igor@localhost");

        MQQueue queue = remote("QM01", "SVRCON_WSO2", "localhost", 1414, spec).accessQueue("Q1", MQOO_OUTPUT);


        MQPutMessageOptions putSpec = new MQPutMessageOptions();
        putSpec.options =  putSpec.options | MQPMO_NEW_MSG_ID | MQPMO_NO_SYNCPOINT;

        queue.put(message, putSpec);


    }
}
