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
@Qualifier("mainHoursLayer")
@RestController
@RequestMapping("/rest/mainHours")
public class MainHoursLayer extends AbstractLedLayer implements LedLayer
{
    private final static Logger LOGGER = LoggerFactory.getLogger(MainHoursLayer.class);

    // Yellow
    @Value("${chronos.layer.mainhours.red:255}")
    private int red;

    @Value("${chronos.layer.mainhours.green:255}")
    private int green;

    @Value("${chronos.layer.mainhours.blue:0}")
    private int blue;

    @Value("${chronos.layer.mainhours.brightness:255}")
    private int brightness;

    private int mainHoursColor = GREEN;

    public void prepare(PixelStrip strip, DateTime now)
    {
        strip.setPixelColor(0, mainHoursColor);
        strip.setPixelColor(15, mainHoursColor);
        strip.setPixelColor(30, mainHoursColor);
        strip.setPixelColor(45, mainHoursColor);
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

        ResponseEntity<String> entity = prepareRESTResponse(red, green, blue, brightness, mainHoursColor);
        return entity;
    }

    @PostConstruct
    private void calculateColor()
    {
        mainHoursColor = makeColor(red, green, blue);
        mainHoursColor = fadeColor(mainHoursColor, brightness);
    }

    public void save(BufferedWriter writer) throws IOException
    {
        super.save(writer);

        writer.write("chronos.layer.mainhours.red=" + red);
        writer.newLine();

        writer.write("chronos.layer.mainhours.green=" + green);
        writer.newLine();

        writer.write("chronos.layer.mainhours.blue=" + blue);
        writer.newLine();

        writer.write("chronos.layer.mainhours.brightness=" + brightness);
        writer.newLine();

        writer.newLine();
    }
}
