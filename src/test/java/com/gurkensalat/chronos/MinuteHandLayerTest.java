package com.gurkensalat.chronos;

import opc.OpcClient;
import opc.OpcDevice;
import opc.PixelStrip;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MinuteHandLayerTest
{
    private PixelStrip strip;

    private MinuteHandLayer testable;

    private DateTime timeToBeTested;

    @Before
    public void setUp()
    {
        // TODO this should be mockable...
        OpcClient server = new OpcClient("127.0.0.1", 7890);
        OpcDevice fadecandy = server.addDevice();
        strip = fadecandy.addPixelStrip(0, 60);

        testable = new MinuteHandLayer();
        timeToBeTested = DateTime.now().withMillis(0);
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void testPrepare0000()
    {
        DateTime now = timeToBeTested.withHourOfDay(0).withMinuteOfHour(0);
        testable.prepare(strip, now);
        assertEquals(0, testable.getHourHandPixelNumber());
    }

    @Test
    public void testPrepare1330()
    {
        DateTime now = timeToBeTested.withHourOfDay(13).withMinuteOfHour(30);
        testable.prepare(strip, now);
        assertEquals(30, testable.getHourHandPixelNumber());
    }

    @Test
    public void testPrepare1638()
    {
        DateTime now = timeToBeTested.withHourOfDay(16).withMinuteOfHour(38);
        testable.prepare(strip, now);
        assertEquals(38, testable.getHourHandPixelNumber());
    }

    @Test
    public void testPrepare2359()
    {
        DateTime now = timeToBeTested.withHourOfDay(23).withMinuteOfHour(59);
        testable.prepare(strip, now);
        assertEquals(59, testable.getHourHandPixelNumber());
    }
}
