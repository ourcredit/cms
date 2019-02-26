package com.monkey.web.config;

import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.TimeZone;

@Configuration
public class UTCTimeZoneConfiguration implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        System.setProperty("user.timezone", "GMT+8");
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }
    public void contextDestroyed(ServletContextEvent event) {}
}
