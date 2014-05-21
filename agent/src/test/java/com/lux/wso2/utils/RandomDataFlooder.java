package com.lux.wso2.utils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by Igor on 08.04.2014.
 */
public class RandomDataFlooder {

    private final Random rnd = new SecureRandom();

    private final String[] eventSourceAddress = {"MQ Input.transaction.Start", "MQ Input.transaction.End", "MQ Output.terminal.in"};
    private final String[] eventName = {"MQ Input.TransactionStart", "MQ Input.transaction.End", "MQ Output.InTerminal"};

    private final String[] flowName = {"FirstWSO2WireTap"};

    protected Object[] newDataArrays() {
        int eventRndIndex = rnd.nextInt(eventSourceAddress.length);
        int flowNameIndex = rnd.nextInt(flowName.length);

        Object[] meta = new Object[]{
                "9001",
                "6.1.0.3",
                eventSourceAddress[eventRndIndex],
                eventName[eventRndIndex],
                "",
                "",
                "",
                System.currentTimeMillis(), //creationTime
                eventRndIndex,
                "BRK01",
                "default",
                flowName[flowNameIndex],
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

        return new Object[]{meta, correlation, payload};
    }

}
