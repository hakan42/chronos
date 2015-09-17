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
@Qualifier("baseLayer")
public class BaseLayer extends AbstractLedLayer
{
    private final static Logger LOGGER = LoggerFactory.getLogger(BaseLayer.class);

    // yellow
    @Value("${chronos.layer.background.red:255}")
    private int red;

    @Value("${chronos.layer.background.green:255}")
    private int green;

    @Value("${chronos.layer.background.blue:0}")
    private int blue;

    @Value("${chronos.layer.background.brightness:255}")
    private int brightness;

    private int color = RED;

    public void prepare(PixelStrip strip, DateTime now)
    {
        for (int i = 0; i < 60; i++)
        {
            strip.setPixelColor(i, color);
        }
    }

    @PostConstruct
    private void calculateColor()
    {
        color = makeColor(red, green, blue);
        color = fadeColor(color, brightness);
    }

    public void save(BufferedWriter writer) throws IOException
    {
        super.save(writer);

        writer.write("chronos.layer.background.red=" + red);
        writer.newLine();

        writer.write("chronos.layer.background.green=" + green);
        writer.newLine();

        writer.write("chronos.layer.background.blue=" + blue);
        writer.newLine();

        writer.write("chronos.layer.background.brightness=" + brightness);
        writer.newLine();

        writer.newLine();
    }
}
