package com.gurkensalat.chronos;

import opc.Animation;
import opc.PixelStrip;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChaseLayer extends AbstractLedLayer
{
    private final static Logger LOGGER = LoggerFactory.getLogger(ChaseLayer.class);

    private static int currentLed = 0;

    public void prepare(PixelStrip strip, DateTime now)
    {
        currentLed++;
        if (currentLed >= 60)
        {
            currentLed = 0;
        }

        for (int i = 0; i < 60; i++)
        {
            int color = BLACK;
            if (i == 0 || i == 15 || i == 30 || i == 45)
            {
                color = RED;
            }
            else if (i == currentLed)
            {
                int r = (int)(Math.random() * 256);
                int g = (int)(Math.random() * 256);
                int b = (int)(Math.random() * 256);

                color = Animation.makeColor(r, g, b);
            }

            strip.setPixelColor(i, color);
        }
    }

}
