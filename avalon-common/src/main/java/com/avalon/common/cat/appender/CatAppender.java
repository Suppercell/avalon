package com.avalon.common.cat.appender;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Trace;

/**
 * 自定义Appender，继承 AbstractAppender 只需要覆盖自已想要的方法即可<br>
 * 类上面的注解是用来设置配置文件中的标签。
 */
@Plugin(name = "CatAppender", category = "Core", elementType = "appender", printObject = true)
public class CatAppender extends AbstractAppender {

    protected CatAppender(String name, Filter filter, Layout<? extends Serializable> layout,
                          boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
    }

    @Override
    public void append(LogEvent event) {

        boolean isTraceMode = Cat.getManager().isTraceMode();
        Level level = event.getLevel();

        if (level.isMoreSpecificThan(Level.ERROR)) {
            logError(event);
        } else if (isTraceMode) {
            logTrace(event);
        }
    }

    private String buildExceptionStack(Throwable exception) {
        if (exception != null) {
            StringWriter writer = new StringWriter(2048);
            exception.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        } else {
            return "";
        }
    }

    private void logError(LogEvent event) {
        Throwable exception = event.getThrown();

        if (exception != null) {
            Object message = event.getMessage();

            if (message != null) {
                Cat.logError(String.valueOf(message), exception);
            } else {
                Cat.logError(exception);
            }
        } else {
            Object message = event.getMessage();
            if (message != null) {
                Cat.logError(String.valueOf(message), new RuntimeException(String.valueOf(message)));
            }
        }
    }

    private void logTrace(LogEvent event) {
        String type = "Log4j2";
        String name = event.getLevel().toString();
        Object message = event.getMessage();
        String data;

        if (message instanceof Throwable) {
            data = buildExceptionStack((Throwable) message);
        } else {
            data = event.getMessage().toString();
        }

        Throwable info = event.getThrown();

        if (info != null) {
            data = data + '\n' + buildExceptionStack(info);
        }
        Cat.logTrace(type, name, Trace.SUCCESS, data);
    }

    @PluginFactory
    public static CatAppender createAppender(@PluginAttribute("name") String name,
                                             @PluginElement("Filter") final Filter filter,
                                             @PluginElement("Layout") Layout<? extends Serializable> layout) {
        if (name == null) {
            LOGGER.error("No name provided for MyCustomAppenderImpl");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new CatAppender(name, filter, layout, true);
    }

}