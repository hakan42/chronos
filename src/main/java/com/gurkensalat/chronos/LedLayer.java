package com.gurkensalat.chronos;

import opc.PixelStrip;
import org.joda.time.DateTime;

public interface LedLayer
{
    void prepare(PixelStrip strip, DateTime now);
}
