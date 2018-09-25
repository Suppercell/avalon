package com.avalon.common.listener.log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContextEvent;

import org.apache.logging.log4j.web.Log4jServletContextListener;
import org.springframework.util.ResourceUtils;

public class Log4j2ConfigListener extends Log4jServletContextListener {

    private final static String LOG4J_PROPERTIES_PATH = "log4jProperties";

    @Override
    public void contextInitialized(final ServletContextEvent event) {
        initLogProperty(event);
        super.contextInitialized(event);
    }

    private void initLogProperty(ServletContextEvent event) {
        String resourceLocation = event.getServletContext().getInitParameter(LOG4J_PROPERTIES_PATH);
        Properties prop = new Properties();
        try {
            File file = ResourceUtils.getFile(resourceLocation);
            prop.load(new FileInputStream(file));
        } catch (IOException e) {
            throw new IllegalArgumentException("日志加载配置文件失败！path= " + resourceLocation, e);
        }
        Enumeration<Object> en = prop.keys();
        while (en.hasMoreElements()) {
            String name = en.nextElement().toString();
            String value = prop.getProperty(name);
            System.setProperty(name, value);
        }

    }
}
