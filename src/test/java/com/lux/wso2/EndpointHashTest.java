package com.lux.wso2;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Igor on 07.04.2014.
 */
public class EndpointHashTest extends EndpointSampleConnectionSettings{

    @Test
    public void checkEnpointHashAndEquals() {
        Endpoint e1 = new Endpoint(url, username, password);
        Endpoint e2 = new Endpoint(url, username, password);
        assertEquals("Two same Endpoints has differend hashCodes ", e1.hashCode(), e2.hashCode());
        assertTrue("Two same Endpoints isn't equals", e1.equals(e2));

        Endpoint e3 = new Endpoint(url, username, password + "1");
        assertFalse("Two different Endpoints has same hashCodes", e1.hashCode() == e3.hashCode());

    }

}
