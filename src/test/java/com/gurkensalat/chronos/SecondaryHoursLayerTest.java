package com.gurkensalat.chronos;

import opc.OpcClient;
import opc.OpcDevice;
import opc.PixelStrip;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;

public class SecondaryHoursLayerTest
{
    private PixelStrip strip;

    private SecondaryHoursLayer testable;

    private DateTime timeToBeTested;

    @Before
    public void setUp()
    {
        // TODO this should be mockable...
        OpcClient server = new OpcClient("127.0.0.1", 7890);
        OpcDevice fadecandy = server.addDevice();
        strip = fadecandy.addPixelStrip(0, 60);

        testable = new SecondaryHoursLayer();
        timeToBeTested = DateTime.now().withMillis(0);
    }

    @After
    public void tearDown()
    {
    }
}
