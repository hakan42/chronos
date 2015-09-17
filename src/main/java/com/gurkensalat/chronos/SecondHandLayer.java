package com.gurkensalat.chronos;

import opc.PixelStrip;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.IOException;

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

    @Value("${chronos.layer.secondhand.brightness:255}")
    private int brightness;

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
        secondHandColor = fadeColor(secondHandColor, brightness);
    }

    public void save(BufferedWriter writer) throws IOException
    {
        super.save(writer);

        writer.write("chronos.layer.secondhand.red=" + red);
        writer.newLine();

        writer.write("chronos.layer.secondhand.green=" + green);
        writer.newLine();

        writer.write("chronos.layer.secondhand.blue=" + blue);
        writer.newLine();

        writer.write("chronos.layer.secondhand.brightness=" + brightness);
        writer.newLine();

        writer.newLine();
    }
}
