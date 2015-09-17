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
@Qualifier("secondaryHoursLayer")
public class SecondaryHoursLayer extends AbstractLedLayer implements LedLayer
{
    private final static Logger LOGGER = LoggerFactory.getLogger(SecondaryHoursLayer.class);

    // Greenyellow
    @Value("${chronos.layer.secondaryhours.red:173}")
    private int red;

    @Value("${chronos.layer.secondaryhours.green:255}")
    private int green;

    @Value("${chronos.layer.secondaryhours.blue:47}")
    private int blue;

    @Value("${chronos.layer.secondaryhours.brightness:255}")
    private int brightness;

    private int secondaryHoursColor = GREEN;

    public void prepare(PixelStrip strip, DateTime now)
    {
        // 0 is main hour
        strip.setPixelColor(5, secondaryHoursColor);
        strip.setPixelColor(10, secondaryHoursColor);
        // 15 is main hour
        strip.setPixelColor(20, secondaryHoursColor);
        strip.setPixelColor(25, secondaryHoursColor);
        // 30 is main hour
        strip.setPixelColor(35, secondaryHoursColor);
        strip.setPixelColor(40, secondaryHoursColor);
        // 45 is main hour
        strip.setPixelColor(50, secondaryHoursColor);
        strip.setPixelColor(55, secondaryHoursColor);
    }

    @PostConstruct
    private void calculateColor()
    {
        secondaryHoursColor = makeColor(red, green, blue);
        secondaryHoursColor = fadeColor(secondaryHoursColor, brightness);
    }

    public void save(BufferedWriter writer) throws IOException
    {
        super.save(writer);

        writer.write("chronos.layer.secondaryhours.red=" + red);
        writer.newLine();

        writer.write("chronos.layer.secondaryhours.green=" + green);
        writer.newLine();

        writer.write("chronos.layer.secondaryhours.blue=" + blue);
        writer.newLine();

        writer.write("chronos.layer.secondaryhours.brightness=" + brightness);
        writer.newLine();

        writer.newLine();
    }
}
