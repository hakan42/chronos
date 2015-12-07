package com.gurkensalat.chronos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import opc.Animation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    public ResponseEntity<String> prepareRESTResponse(int red, int green, int blue, int brightness, int color)
    {
        Map<String, Object> userData = new HashMap<String, Object>();
        userData.put("message", "Set color data...");
        userData.put("red", red);
        userData.put("green", green);
        userData.put("blue", blue);
        userData.put("brightness", brightness);
        userData.put("color", color);

        String result = "";

        try
        {
            ObjectMapper mapper = new ObjectMapper();
            // mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            result = mapper.writeValueAsString(userData);
        }
        catch (JsonProcessingException jpe)
        {
            LOGGER.error("While proving answer...", jpe);
            result = jpe.getMessage();
        }

        ResponseEntity<String> entity = new ResponseEntity<String>(result, HttpStatus.OK);
        return entity;
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

    public int getRed()
    {
        return -1;
    }

    public int getBlue()
    {
        return -1;
    }

    public int getGreen()
    {
        return -1;
    }

    public int getBrightness()
    {
        return -1;
    }
}
