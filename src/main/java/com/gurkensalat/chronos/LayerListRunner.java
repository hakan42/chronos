package com.gurkensalat.chronos;

import opc.Animation;
import opc.OpcClient;
import opc.OpcDevice;
import opc.PixelStrip;
import org.joda.time.DateTime;
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
import org.springframework.util.StopWatch;

@Component
public class LayerListRunner extends Animation implements CommandLineRunner, ApplicationListener<ApplicationContextEvent>
{
    private final static Logger LOGGER = LoggerFactory.getLogger(LayerListRunner.class);

    private static Thread thisThread = null;

    private static boolean running = true;


    public void run(String... args) throws Exception
    {
        LOGGER.info("Running my additional runner");

        // TODO make address data configurable
        OpcClient server = new OpcClient("127.0.0.1", 7890);
        OpcDevice fadecandy = server.addDevice();
        PixelStrip strip = fadecandy.addPixelStrip(0, 60);
        strip.setAnimation(this);

        LOGGER.info("Server config: {}", server.getConfig());

        LayerList layers = new LayerList();
        layers.add(new ChaseLayer());
        layers.add(new HourHandLayer());
        layers.add(new MinuteHandLayer());

        thisThread = Thread.currentThread();
        while (running)
        {
            try
            {
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                DateTime now = DateTime.now();
                for (LedLayer layer : layers)
                {
                    layer.prepare(strip, now);
                }
                stopWatch.stop();

                LOGGER.debug("Preparation of strips took {} msec", stopWatch.getTotalTimeMillis());
                server.show();

                // Sleep for half a second...
                Thread.sleep(500);
            }
            catch (InterruptedException ie)
            {
                // We don't want to continue looping if death was requested
                running = false;
            }
        }

        server.clear(); // Set all pixels to black
        server.show();  // Show the darkened pixels

        server.close();

        LOGGER.info("Finished my additional runner");
    }

    @Override
    public void reset(PixelStrip strip)
    {

    }

    @Override
    public boolean draw(PixelStrip strip)
    {
        return true;
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
