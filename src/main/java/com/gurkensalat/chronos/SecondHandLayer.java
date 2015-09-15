package com.gurkensalat.chronos;

import opc.Animation;
import opc.PixelStrip;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SecondHandLayer extends AbstractLedLayer implements LedLayer
{
    private final static Logger LOGGER = LoggerFactory.getLogger(SecondHandLayer.class);

    // TODO make this configurable
    private int secondHandColor = BLUE;

    // For unit testing...
    private int pixelNumber;

    public void prepare(PixelStrip strip, DateTime now)
    {
        int second = now.secondOfMinute().get();

        pixelNumber = second;

        int r = (int)(Math.random() * 256);
        int g = (int)(Math.random() * 256);
        int b = (int)(Math.random() * 256);

        secondHandColor = makeColor(r, g, b);

        strip.setPixelColor(pixelNumber, secondHandColor);
    }

    // For unit testing...
    protected int getHourHandPixelNumber()
    {
        return pixelNumber;
    }
}
