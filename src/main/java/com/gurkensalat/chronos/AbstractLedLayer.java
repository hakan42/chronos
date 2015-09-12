package com.gurkensalat.chronos;

import opc.Animation;

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
}
