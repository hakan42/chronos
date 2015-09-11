package com.gurkensalat.chronos;

import opc.PixelStrip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChaseLayer implements LedLayer
{
    private final static Logger LOGGER = LoggerFactory.getLogger(ChaseLayer.class);

    /**
     * @formatter:off
     #!/usr/bin/env python

     # Light each LED in sequence, and repeat.

     import opc, time, random

     numLEDs = 60
     client = opc.Client('127.0.0.1:7890')

     while True:
     for i in range(numLEDs):
     pixels = [ (0,0,0) ] * numLEDs
     pixels[0] = (128, 0, 0)
     pixels[i] = (0, random.randint(0, 256), random.randint(0, 256))
     client.put_pixels(pixels)
     time.sleep(0.1)
     * @formatter:on
     */

     /*
      * @formatter:off
      *
      * int color = 0xFF0000;  // red
      * strip.setPixelColor(3, color);
      * strip.setPixelColor(5, 0x888800); // yellow
      * strip.setPixelColor(7, 0x00FF00); // green
      *
      * server.show();        // Display the pixel changes
      * Thread.sleep(5000);   // Wait five seconds
      *
      * @formatter:on
      */

    private static int currentLed = 0;

    public void prepare(PixelStrip strip)
    {
        if (currentLed >= 60)
        {
            currentLed = 0;
        }

        for (int i = 1; i < 60; i++)
        {
            if (i == 0)
            {
                strip.setPixelColor(i, 0xFF0000);
            }
            else if (i == currentLed)
            {
                int r = 0;
                int g = 42;
                int b = 42;

                strip.setPixelColor(i, r * 256 * 256 + g * 256 + b);
            }
            else
            {
                strip.setPixelColor(i, 0x000000);
            }
        }
    }

}
