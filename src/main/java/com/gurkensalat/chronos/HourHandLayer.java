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
@Qualifier("hourHandLayer")
@RestController
@RequestMapping("/rest/hourHand")
public class HourHandLayer extends AbstractLedLayer implements LedLayer
{
    private final static Logger LOGGER = LoggerFactory.getLogger(HourHandLayer.class);

    // red
    @Value("${chronos.layer.hourhand.red:255}")
    private int red;

    @Value("${chronos.layer.hourhand.green:0}")
    private int green;

    @Value("${chronos.layer.hourhand.blue:0}")
    private int blue;

    @Value("${chronos.layer.hourhand.brightness:255}")
    private int brightness;

    private int hourHandColor = GREEN;

    // For unit testing...
    private int pixelNumber;

    public void prepare(PixelStrip strip, DateTime now)
    {
        int hour = now.hourOfDay().get();
        if (hour > 11)
        {
            hour = hour - 12;
        }

        int minute = now.minuteOfHour().get();

        pixelNumber = hour * 5 + (int) (minute / 12);
        LOGGER.debug("Now is {} / {} -> pixel is {}", hour, minute, pixelNumber);

        strip.setPixelColor(pixelNumber, hourHandColor);
    }

    // For unit testing...
    protected int getHourHandPixelNumber()
    {
        return pixelNumber;
    }

    @RequestMapping(value = "/color", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<String> configureColor(@RequestParam(value = "red", required = false) String redParam,
                                             @RequestParam(value = "green", required = false) String greenParam,
                                             @RequestParam(value = "blue", required = false) String blueParam)
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

        ResponseEntity<String> entity = prepareRESTResponse(red, green, blue, brightness, hourHandColor);
        return entity;
    }

    @PostConstruct
    private void calculateColor()
    {
        hourHandColor = makeColor(red, green, blue);
        hourHandColor = fadeColor(hourHandColor, brightness);
    }

    public void save(BufferedWriter writer) throws IOException
    {
        super.save(writer);

        writer.write("chronos.layer.hourhand.red=" + red);
        writer.newLine();

        writer.write("chronos.layer.hourhand.green=" + green);
        writer.newLine();

        writer.write("chronos.layer.hourhand.blue=" + blue);
        writer.newLine();

        writer.write("chronos.layer.hourhand.brightness=" + brightness);
        writer.newLine();

        writer.newLine();
    }
}
