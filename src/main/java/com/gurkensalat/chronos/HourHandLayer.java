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
@Qualifier("hourHandLayer")
public class HourHandLayer extends AbstractLedLayer implements LedLayer
{
    private final static Logger LOGGER = LoggerFactory.getLogger(HourHandLayer.class);

    // red
    @Value("${chronos.layer.hourhand.red:255}")
    private int red;

    @Value("${chronos.layer.hourhand.green:0}")
    private int green;

    @Value("${chronos.layer.hourhand.blue:0}")
    private int blue;

    @Value("${chronos.layer.hourhand.brightness:255}")
    private int brightness;

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
        hourHandColor = fadeColor(hourHandColor, brightness);
    }

    public void save(BufferedWriter writer) throws IOException
    {
        super.save(writer);

        writer.write("chronos.layer.hourhand.red=" + red);
        writer.newLine();

        writer.write("chronos.layer.hourhand.green=" + green);
        writer.newLine();

        writer.write("chronos.layer.hourhand.blue=" + blue);
        writer.newLine();

        writer.write("chronos.layer.hourhand.brightness=" + brightness);
        writer.newLine();

        writer.newLine();
    }
}
