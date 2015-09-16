package com.gurkensalat.chronos;

import opc.Animation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public abstract class AbstractLedLayer implements LedLayer
{
    public static int BLACK = makeColor(0, 0, 0);

    public static int RED = makeColor(255, 0, 0);

    public static int GREEN = makeColor(0, 255, 0);

    public static int BLUE = makeColor(0, 0, 255);

    public static int makeColor(int red, int green, int blue)
    {
        return Animation.makeColor(red, green, blue);
    }

    public void save(BufferedWriter writer) throws IOException
    {
        writer.write("#");
        writer.newLine();

        writer.write("# " + this.getClass().getName());
        writer.newLine();

        writer.write("#");
        writer.newLine();
    }
}
