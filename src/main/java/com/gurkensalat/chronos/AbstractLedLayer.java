package com.gurkensalat.chronos;

import opc.Animation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public abstract class AbstractLedLayer implements LedLayer
{
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractLedLayer.class);

    public static int BLACK = makeColor(0, 0, 0);

    public static int RED = makeColor(255, 0, 0);

    public static int GREEN = makeColor(0, 255, 0);

    public static int BLUE = makeColor(0, 0, 255);

    public static int fadeColor(int color, int brightness)
    {
        return Animation.fadeColor(color, brightness);
    }

    public static int makeColor(int red, int green, int blue)
    {
        return Animation.makeColor(red, green, blue);
    }

    public int colorPart(String data)
    {
        int result = -1;
        if (isNotEmpty(data))
        {
            try
            {
                result = Integer.parseInt(data);

                if (result > 255)
                {
                    result = -1;
                }

                if (result < 0)
                {
                    result = -1;
                }
            }
            catch (NumberFormatException nfe)
            {
                LOGGER.error("While decoding color part {}", data, nfe);
            }
        }

        return result;
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
