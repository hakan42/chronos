package com.gurkensalat.chronos;

import opc.PixelStrip;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.IOException;

@Component
@Qualifier("secondaryHoursLayer")
@RestController
@RequestMapping("/rest/secondaryHours")
public class SecondaryHoursLayer extends AbstractLedLayer implements LedLayer
{
    private final static Logger LOGGER = LoggerFactory.getLogger(SecondaryHoursLayer.class);

    // Greenyellow
    @Value("${chronos.layer.secondaryhours.red:173}")
    private int red;

    @Value("${chronos.layer.secondaryhours.green:255}")
    private int green;

    @Value("${chronos.layer.secondaryhours.blue:47}")
    private int blue;

    @Value("${chronos.layer.secondaryhours.brightness:255}")
    private int brightness;

    private int secondaryHoursColor = GREEN;

    public void prepare(PixelStrip strip, DateTime now)
    {
        // 0 is main hour
        strip.setPixelColor(5, secondaryHoursColor);
        strip.setPixelColor(10, secondaryHoursColor);
        // 15 is main hour
        strip.setPixelColor(20, secondaryHoursColor);
        strip.setPixelColor(25, secondaryHoursColor);
        // 30 is main hour
        strip.setPixelColor(35, secondaryHoursColor);
        strip.setPixelColor(40, secondaryHoursColor);
        // 45 is main hour
        strip.setPixelColor(50, secondaryHoursColor);
        strip.setPixelColor(55, secondaryHoursColor);
    }

    @RequestMapping(value = "/color", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    // @formatter:off
    public HttpEntity<String> configureColor(@RequestParam(value = "red", required = false) String redParam,
                                             @RequestParam(value = "green", required = false) String greenParam,
                                             @RequestParam(value = "blue", required = false) String blueParam)
    // @formatter:on
    {
        int i = -1;

        i = colorPart(redParam);
        if (i != -1)
        {
            red = i;
        }

        i = colorPart(greenParam);
        if (i != -1)
        {
            green = i;
        }

        i = colorPart(blueParam);
        if (i != -1)
        {
            blue = i;
        }

        calculateColor();

        ResponseEntity<String> entity = prepareRESTResponse(red, green, blue, brightness, secondaryHoursColor);
        return entity;
    }

    @PostConstruct
    private void calculateColor()
    {
        secondaryHoursColor = makeColor(red, green, blue);
        secondaryHoursColor = fadeColor(secondaryHoursColor, brightness);
    }

    public void save(BufferedWriter writer) throws IOException
    {
        super.save(writer);

        writer.write("chronos.layer.secondaryhours.red=" + red);
        writer.newLine();

        writer.write("chronos.layer.secondaryhours.green=" + green);
        writer.newLine();

        writer.write("chronos.layer.secondaryhours.blue=" + blue);
        writer.newLine();

        writer.write("chronos.layer.secondaryhours.brightness=" + brightness);
        writer.newLine();

        writer.newLine();
    }

    public int getRed()
    {
        return red;
    }

    public int getBlue()
    {
        return blue;
    }

    public int getGreen()
    {
        return green;
    }

    public int getBrightness()
    {
        return brightness;
    }
}
