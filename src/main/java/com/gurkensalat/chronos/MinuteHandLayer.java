package com.gurkensalat.chronos;

import opc.PixelStrip;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinuteHandLayer extends AbstractLedLayer implements LedLayer
{
    private final static Logger LOGGER = LoggerFactory.getLogger(MinuteHandLayer.class);

    // TODO make this configurable
    private int minuteHandColor = BLUE;

    // For unit testing...
    private int pixelNumber;

    public void prepare(PixelStrip strip, DateTime now)
    {
        int minute = now.minuteOfHour().get();

        pixelNumber = minute;
        LOGGER.debug("Now is {} -> pixel is {}", minute, pixelNumber);


        strip.setPixelColor(pixelNumber, minuteHandColor);
    }

    // For unit testing...
    protected int getHourHandPixelNumber()
    {
        return pixelNumber;
    }
}
