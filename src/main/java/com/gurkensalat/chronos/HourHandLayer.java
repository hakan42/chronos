package com.gurkensalat.chronos;

import opc.PixelStrip;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Qualifier("hourHandLayer")
public class HourHandLayer extends AbstractLedLayer implements LedLayer
{
    private final static Logger LOGGER = LoggerFactory.getLogger(HourHandLayer.class);

    @Value("${chronos.layer.hourhand.red:0}")
    private int red;

    @Value("${chronos.layer.hourhand.green:255}")
    private int green;

    @Value("${chronos.layer.hourhand.blue:0}")
    private int blue;

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

    @PostConstruct
    private void calculateColor()
    {
        hourHandColor = makeColor(red, green, blue);

        LOGGER.info("red:   {}", red);
        LOGGER.info("green: {}", green);
        LOGGER.info("blue:  {}", blue);
        LOGGER.info("color: {}", hourHandColor);
    }
}
