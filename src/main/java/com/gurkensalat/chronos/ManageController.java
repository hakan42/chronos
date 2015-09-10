package com.gurkensalat.chronos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class ManageController
{
    private final static Logger LOGGER = LoggerFactory.getLogger(ManageController.class);

    @RequestMapping("/manage")
    String manage()
    {
        return "manage";
    }

    @RequestMapping("/manage/properties")
    @ResponseBody
    java.util.Properties properties()
    {
        return System.getProperties();
    }
}
