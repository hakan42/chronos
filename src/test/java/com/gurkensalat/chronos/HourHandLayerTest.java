package com.gurkensalat.chronos;

import opc.OpcClient;
import opc.OpcDevice;
import opc.PixelStrip;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HourHandLayerTest
{
    private PixelStrip strip;

    private HourHandLayer testable;

    private DateTime timeToBeTested;

    @Before
    public void setUp()
    {
        // TODO this should be mockable...
        OpcClient server = new OpcClient("127.0.0.1", 7890);
        OpcDevice fadecandy = server.addDevice();
        strip = fadecandy.addPixelStrip(0, 60);

        testable = new HourHandLayer();
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
        assertEquals(testable.getHourHandPixelNumber(), 0);
    }

    @Test
    public void testPrepare1200()
    {
        DateTime now = timeToBeTested.withHourOfDay(12).withMinuteOfHour(0);
        testable.prepare(strip, now);
        assertEquals(testable.getHourHandPixelNumber(), 0);
    }

    @Test
    public void testPrepare2100()
    {
        DateTime now = timeToBeTested.withHourOfDay(21).withMinuteOfHour(0);
        testable.prepare(strip, now);
        assertEquals(testable.getHourHandPixelNumber(), 45);
    }

    @Test
    public void testPrepare2300()
    {
        DateTime now = timeToBeTested.withHourOfDay(23).withMinuteOfHour(0);
        testable.prepare(strip, now);
        assertEquals(testable.getHourHandPixelNumber(), 55);
    }
}