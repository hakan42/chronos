package com.gurkensalat.chronos;

import opc.OpcClient;
import opc.OpcDevice;
import opc.PixelStrip;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SecondHandLayerTest
{
    private PixelStrip strip;

    private SecondHandLayer testable;

    private DateTime timeToBeTested;

    @Before
    public void setUp()
    {
        // TODO this should be mockable...
        OpcClient server = new OpcClient("127.0.0.1", 7890);
        OpcDevice fadecandy = server.addDevice();
        strip = fadecandy.addPixelStrip(0, 60);

        testable = new SecondHandLayer();
        timeToBeTested = DateTime.now().withMillis(0);
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void testPrepare000000()
    {
        DateTime now = timeToBeTested.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
        testable.prepare(strip, now);
        assertEquals(0, testable.getHourHandPixelNumber());
    }

    @Test
    public void testPrepare133042()
    {
        DateTime now = timeToBeTested.withHourOfDay(13).withMinuteOfHour(30).withSecondOfMinute(42);
        testable.prepare(strip, now);
        assertEquals(42, testable.getHourHandPixelNumber());
    }
}
