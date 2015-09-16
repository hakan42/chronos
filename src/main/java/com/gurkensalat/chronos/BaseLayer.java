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

    @Value("${chronos.layer.base.red:255}")
    private int red;

    @Value("${chronos.layer.base.green:0}")
    private int green;

    @Value("${chronos.layer.base.blue:0}")
    private int blue;

    private int color = RED;

    public void prepare(PixelStrip strip, DateTime now)
    {
        for (int i = 0; i < 60; i++)
        {
            if (i == 0 || i == 15 || i == 30 || i == 45)
            {
                strip.setPixelColor(i, color);
            }
            else
            {
                strip.setPixelColor(i, BLACK);
            }
        }
    }

    @PostConstruct
    private void calculateColor()
    {
        color = makeColor(red, green, blue);
    }

    public void save(BufferedWriter writer) throws IOException
    {
        super.save(writer);

        writer.write("chronos.layer.base.red=" + red);
        writer.newLine();

        writer.write("chronos.layer.base.green=" + green);
        writer.newLine();

        writer.write("chronos.layer.base.blue=" + blue);
        writer.newLine();

        writer.newLine();
    }
}
