package com.gurkensalat.chronos;

import opc.Animation;
import opc.PixelStrip;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BaseLayer extends AbstractLedLayer
{
    private final static Logger LOGGER = LoggerFactory.getLogger(BaseLayer.class);

    public void prepare(PixelStrip strip, DateTime now)
    {
        for (int i = 0; i < 60; i++)
        {
            int color = BLACK;
            if (i == 0 || i == 15 || i == 30 || i == 45)
            {
                color = RED;
            }

            strip.setPixelColor(i, color);
        }
    }

}
