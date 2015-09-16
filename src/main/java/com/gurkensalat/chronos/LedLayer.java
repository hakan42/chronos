package com.gurkensalat.chronos;

import opc.PixelStrip;
import org.joda.time.DateTime;

import java.io.BufferedWriter;
import java.io.IOException;

public interface LedLayer
{
    void prepare(PixelStrip strip, DateTime now);

    void save(BufferedWriter writer) throws IOException;
}
