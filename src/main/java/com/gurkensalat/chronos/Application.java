package com.gurkensalat.chronos;

import org.apache.commons.lang3.CharEncoding;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Slf4jRequestLog;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class Application
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(Application.class);
    }

    @Bean
    public ResourceBundleMessageSource messageSource()
    {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/messages");
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setDefaultEncoding(CharEncoding.UTF_8);
        return messageSource;
    }

    @Bean
    public EmbeddedServletContainerFactory jettyConfigBean()
    {
        JettyEmbeddedServletContainerFactory jef = new JettyEmbeddedServletContainerFactory();

        jef.addServerCustomizers(new JettyServerCustomizer()
        {
            public void customize(Server server)
            {
                HandlerCollection handlers = new HandlerCollection();
                for (Handler handler : server.getHandlers())
                {
                    handlers.addHandler(handler);
                }

                {
                    RequestLogHandler reqLogs = new RequestLogHandler();

                    Slf4jRequestLog reqLogImpl = new Slf4jRequestLog();
                    reqLogImpl.setExtended(false);
                    reqLogs.setRequestLog(reqLogImpl);

                    handlers.addHandler(reqLogs);
                }

                {
                    RequestLogHandler reqLogs = new RequestLogHandler();

                    String logdir = "./env.PWD_IS_UNDEFINED/logs/";

                    NCSARequestLog reqLogImpl = new NCSARequestLog(logdir + "access-yyyy_mm_dd.log");
                    reqLogImpl.setRetainDays(30);
                    reqLogImpl.setAppend(true);
                    reqLogImpl.setExtended(false);
                    // TODO make log time zone configurable
                    reqLogImpl.setLogTimeZone("GMT+1");
                    reqLogs.setRequestLog(reqLogImpl);

                    handlers.addHandler(reqLogs);
                }

                server.setHandler(handlers);
            }
        });

        return jef;
    }
}
