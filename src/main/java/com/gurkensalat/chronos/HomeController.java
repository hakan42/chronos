package com.gurkensalat.chronos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class HomeController
{
    private final static Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/")
    String index(Model model)
    {
        return "index";
    }
}
