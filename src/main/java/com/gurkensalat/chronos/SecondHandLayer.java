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
@Qualifier("secondHandLayer")
public class SecondHandLayer extends AbstractLedLayer implements LedLayer
{
    private final static Logger LOGGER = LoggerFactory.getLogger(SecondHandLayer.class);

    @Value("${chronos.layer.secondhand.red:0}")
    private int red;

    @Value("${chronos.layer.secondhand.green:255}")
    private int green;

    @Value("${chronos.layer.secondhand.blue:0}")
    private int blue;

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

    @PostConstruct
    private void calculateColor()
    {
        secondHandColor = makeColor(red, green, blue);
    }
}
