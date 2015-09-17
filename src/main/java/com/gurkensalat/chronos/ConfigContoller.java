package com.gurkensalat.chronos;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

@Controller
public class ConfigContoller
{
    private final static Logger LOGGER = LoggerFactory.getLogger(ConfigContoller.class);

    @Resource
    private LayerListRunner layerListRunner;

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<String> save()
    {
        try
        {
            OutputStream os = FileUtils.openOutputStream(new File("application.properties"));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
            for (LedLayer layer : layerListRunner.getLayers())
            {
                layer.save(writer);
            }
            writer.close();
            ;
            os.close();
        }
        catch (Exception e)
        {
            LOGGER.error("While saving configuration", e);
        }

        String result = "{\"message\": \"Saved config data...\"}";
        ResponseEntity<String> entity = new ResponseEntity<String>(result, HttpStatus.OK);
        return entity;
    }
}
