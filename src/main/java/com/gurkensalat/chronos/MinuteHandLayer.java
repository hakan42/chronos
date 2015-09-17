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
@Qualifier("minuteHandLayer")
public class MinuteHandLayer extends AbstractLedLayer implements LedLayer
{
    private final static Logger LOGGER = LoggerFactory.getLogger(MinuteHandLayer.class);

    // blue
    @Value("${chronos.layer.minutehand.red:0}")
    private int red;

    @Value("${chronos.layer.minutehand.green:0}")
    private int green;

    @Value("${chronos.layer.minutehand.blue:255}")
    private int blue;

    @Value("${chronos.layer.minutehand.brightness:255}")
    private int brightness;

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

    @PostConstruct
    private void calculateColor()
    {
        minuteHandColor = makeColor(red, green, blue);
        minuteHandColor = fadeColor(minuteHandColor, brightness);
    }

    public void save(BufferedWriter writer) throws IOException
    {
        super.save(writer);

        writer.write("chronos.layer.minutehand.red=" + red);
        writer.newLine();

        writer.write("chronos.layer.minutehand.green=" + green);
        writer.newLine();

        writer.write("chronos.layer.minutehand.blue=" + blue);
        writer.newLine();

        writer.write("chronos.layer.minutehand.brightness=" + brightness);
        writer.newLine();

        writer.newLine();
    }
}
