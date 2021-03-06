package com.gurkensalat.chronos;

import opc.Animation;
import opc.OpcClient;
import opc.OpcDevice;
import opc.PixelStrip;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;

@Component
public class LayerListRunner extends Animation implements CommandLineRunner, ApplicationListener<ApplicationContextEvent>
{
    private final static Logger LOGGER = LoggerFactory.getLogger(LayerListRunner.class);

    private static Thread thisThread = null;

    private static boolean running = true;

    @Resource(name = "baseLayer")
    private LedLayer baseLayer;

    @Resource(name = "mainHoursLayer")
    private LedLayer mainHoursLayer;

    @Resource(name = "secondaryHoursLayer")
    private LedLayer secondaryHoursLayer;

    @Resource(name = "hourHandLayer")
    private LedLayer hourHandLayer;

    @Resource(name = "minuteHandLayer")
    private LedLayer minuteHandLayer;

    @Resource(name = "secondHandLayer")
    private LedLayer secondHandLayer;

    private LayerList layers = new LayerList();

    public void run(String... args) throws Exception
    {
        LOGGER.info("Running my additional runner");

        // TODO make address data configurable
        OpcClient server = new OpcClient("127.0.0.1", 7890);
        OpcDevice fadecandy = server.addDevice();
        PixelStrip strip = fadecandy.addPixelStrip(0, 60);
        strip.setAnimation(this);

        LOGGER.info("Server config: {}", server.getConfig());

        layers = new LayerList();

        if (baseLayer != null)
        {
            layers.add(baseLayer);
        }

        if (mainHoursLayer != null)
        {
            layers.add(mainHoursLayer);
        }

        if (secondaryHoursLayer != null)
        {
            layers.add(secondaryHoursLayer);
        }

        if (hourHandLayer != null)
        {
            layers.add(hourHandLayer);
        }

        if (minuteHandLayer != null)
        {
            layers.add(minuteHandLayer);
        }

        if (secondHandLayer != null)
        {
            layers.add(secondHandLayer);
        }

        LOGGER.info("Layers: {}", layers);

        // TODO make time zone configurable
        DateTimeZone zone = DateTimeZone.forID("Europe/Berlin");

        thisThread = Thread.currentThread();
        while (running)
        {
            try
            {
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                DateTime now = DateTime.now(zone);
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

    public LayerList getLayers()
    {
        return layers;
    }
}
