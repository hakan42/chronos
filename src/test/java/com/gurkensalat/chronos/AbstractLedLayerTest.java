package com.gurkensalat.chronos;

import opc.PixelStrip;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AbstractLedLayerTest
{
    private TestableLedLayer testable;

    private DateTime timeToBeTested;

    @Before
    public void setUp()
    {
        testable = new TestableLedLayer();
        timeToBeTested = DateTime.now().withMillis(0);
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void colorPartDataOutOfRange4711()
    {
        int actual = testable.colorPart("4711");
        assertEquals("Input is out of range", -1, actual);
    }

    @Test
    public void colorPartDataOutOfRangeMinusThree()
    {
        int actual = testable.colorPart("-3");
        assertEquals("Input is out of range", -1, actual);
    }

    @Test
    public void colorPartDataAlphanumerical()
    {
        int actual = testable.colorPart("abcxyz");
        assertEquals("Input is out of range", -1, actual);
    }

    @Test
    public void colorPartDataNull()
    {
        int actual = testable.colorPart(null);
        assertEquals("Input is out of range", -1, actual);
    }

    @Test
    public void colorPartData33()
    {
        int actual = testable.colorPart("33");
        assertEquals("Input is out of range", 33, actual);
    }

    private class TestableLedLayer extends AbstractLedLayer
    {
        public void prepare(PixelStrip strip, DateTime now)
        {

        }
    }
}

