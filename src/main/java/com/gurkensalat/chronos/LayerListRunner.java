package com.gurkensalat.chronos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

@Component
public class LayerListRunner implements CommandLineRunner, ApplicationListener<ApplicationContextEvent>
{
    private final static Logger LOGGER = LoggerFactory.getLogger(LayerListRunner.class);

    private static Thread thisThread = null;

    private static boolean running = true;


    public void run(String... args) throws Exception
    {
        LOGGER.info("Running my additional runner");

        thisThread = Thread.currentThread();
        while (running)
        {
            LOGGER.info("Doing some work...");

            try
            {
                Thread.sleep(15 * 1000);
            }
            catch (InterruptedException ie)
            {
                // We don't want to continue looping if death was requested
                running = false;
            }
        }

        LOGGER.info("Finished my additional runner");
    }

    public void onApplicationEvent(ApplicationContextEvent event)
    {
        LOGGER.info("Context received event {}", event);
        if (event instanceof ContextStoppedEvent || event instanceof ContextClosedEvent)
        {
            // No clue why onApplicationEvent(ContextStoppedEvent) is not called, fake it here...
            running = false;
            LOGGER.info("Set running to {}", running);
            thisThread.interrupt();
        }
    }
}
