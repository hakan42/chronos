package com.gurkensalat.chronos;

import opc.PixelStrip;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("hourHandLayer")
public class HourHandLayer extends AbstractLedLayer implements LedLayer
{
    private final static Logger LOGGER = LoggerFactory.getLogger(HourHandLayer.class);

    // TODO make this configurable
    private int hourHandColor = GREEN;

    // For unit testing...
    private int pixelNumber;

    public void prepare(PixelStrip strip, DateTime now)
    {
        int hour = now.hourOfDay().get();
        if (hour > 11)
        {
            hour = hour - 12;
        }

        int minute = now.minuteOfHour().get();

        pixelNumber = hour * 5 + (int) (minute / 12);
        LOGGER.debug("Now is {} / {} -> pixel is {}", hour, minute, pixelNumber);


        strip.setPixelColor(pixelNumber, hourHandColor);
    }

    // For unit testing...
    protected int getHourHandPixelNumber()
    {
        return pixelNumber;
    }
}
