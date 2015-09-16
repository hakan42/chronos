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

    // Greenyellow
    @Value("${chronos.layer.base_main.red:173}")
    private int red_main;

    @Value("${chronos.layer.base_main.green:255}")
    private int green_main;

    @Value("${chronos.layer.base_main.blue:47}")
    private int blue_main;

    // yellow
    @Value("${chronos.layer.base_secondary.red:255}")
    private int red_secondary;

    @Value("${chronos.layer.base_secondary.green:255}")
    private int green_secondary;

    @Value("${chronos.layer.base_secondary.blue:0}")
    private int blue_secondary;

    private int color_main = RED;

    private int color_secondary = RED;

    public void prepare(PixelStrip strip, DateTime now)
    {
        for (int i = 0; i < 60; i++)
        {
            if (i == 0 || i == 15 || i == 30 || i == 45)
            {
                strip.setPixelColor(i, color_main);
            }
            else if (i % 5 == 0)
            {
                strip.setPixelColor(i, color_secondary);
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
        color_main = makeColor(red_main, green_main, blue_main);
        color_secondary = makeColor(red_secondary, green_secondary, blue_secondary);
    }

    public void save(BufferedWriter writer) throws IOException
    {
        super.save(writer);

        writer.write("chronos.layer.base_main.red=" + red_main);
        writer.newLine();

        writer.write("chronos.layer.base_main.green=" + green_main);
        writer.newLine();

        writer.write("chronos.layer.base_main.blue=" + blue_main);
        writer.newLine();

        writer.write("chronos.layer.base_secondary.red=" + red_secondary);
        writer.newLine();

        writer.write("chronos.layer.base_secondary.green=" + green_secondary);
        writer.newLine();

        writer.write("chronos.layer.base_secondary.blue=" + blue_secondary);
        writer.newLine();

        writer.newLine();
    }
}
