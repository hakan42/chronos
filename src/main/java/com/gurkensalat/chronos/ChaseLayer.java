package com.gurkensalat.chronos;

public class ChaseLayer
{
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
}
