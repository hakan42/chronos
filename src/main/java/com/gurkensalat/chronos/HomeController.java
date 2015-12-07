package com.gurkensalat.chronos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
class HomeController
{
    private final static Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Resource(name = "hourHandLayer")
    private LedLayer hourHandLayer;

    @Resource(name = "minuteHandLayer")
    private LedLayer minuteHandLayer;

    @Resource(name = "secondHandLayer")
    private LedLayer secondHandLayer;

    @Resource(name = "mainHoursLayer")
    private LedLayer mainHoursLayer;

    @Resource(name = "secondaryHoursLayer")
    private LedLayer secondaryHoursLayer;

    @RequestMapping("/")
    public String index(Model model)
    {
        model.addAttribute("hourHand", hourHandLayer);
        model.addAttribute("minuteHand", minuteHandLayer);
        model.addAttribute("secondHand", secondHandLayer);

        model.addAttribute("mainHours", mainHoursLayer);
        model.addAttribute("secondaryHours", secondaryHoursLayer);

        return "index";
    }
}
