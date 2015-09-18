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
@Qualifier("minuteHandLayer")
@RestController
@RequestMapping("/rest/minuteHand")
public class MinuteHandLayer extends AbstractLedLayer implements LedLayer
{
    private final static Logger LOGGER = LoggerFactory.getLogger(MinuteHandLayer.class);

    // blue
    @Value("${chronos.layer.minutehand.red:0}")
    private int red;

    @Value("${chronos.layer.minutehand.green:0}")
    private int green;

    @Value("${chronos.layer.minutehand.blue:255}")
    private int blue;

    @Value("${chronos.layer.minutehand.brightness:255}")
    private int brightness;

    private int minuteHandColor = BLUE;

    // For unit testing...
    private int pixelNumber;

    public void prepare(PixelStrip strip, DateTime now)
    {
        int minute = now.minuteOfHour().get();

        pixelNumber = minute;
        LOGGER.debug("Now is {} -> pixel is {}", minute, pixelNumber);


        strip.setPixelColor(pixelNumber, minuteHandColor);
    }

    // For unit testing...
    protected int getHourHandPixelNumber()
    {
        return pixelNumber;
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

        ResponseEntity<String> entity = prepareRESTResponse(red, green, blue, brightness, minuteHandColor);
        return entity;
    }

    @PostConstruct
    private void calculateColor()
    {
        minuteHandColor = makeColor(red, green, blue);
        minuteHandColor = fadeColor(minuteHandColor, brightness);
    }

    public void save(BufferedWriter writer) throws IOException
    {
        super.save(writer);

        writer.write("chronos.layer.minutehand.red=" + red);
        writer.newLine();

        writer.write("chronos.layer.minutehand.green=" + green);
        writer.newLine();

        writer.write("chronos.layer.minutehand.blue=" + blue);
        writer.newLine();

        writer.write("chronos.layer.minutehand.brightness=" + brightness);
        writer.newLine();

        writer.newLine();
    }
}
