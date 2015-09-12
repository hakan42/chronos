package com.gurkensalat.chronos;

import opc.OpcClient;
import opc.OpcDevice;
import opc.PixelStrip;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BaseLayerTest
{
    private PixelStrip strip;

    private BaseLayer testable;

    private DateTime timeToBeTested;

    @Before
    public void setUp()
    {
        // TODO this should be mockable...
        OpcClient server = new OpcClient("127.0.0.1", 7890);
        OpcDevice fadecandy = server.addDevice();
        strip = fadecandy.addPixelStrip(0, 60);

        testable = new BaseLayer();
        timeToBeTested = DateTime.now().withMillis(0);
    }

    @After
    public void tearDown()
    {
    }
}
