package io.ghostwriter.rt.tracer.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Writes all event entries to an SLF4J handler.
 * The used log level will be 'trace' and the even soure class will be used as the log handler.
 */
public class Slf4JWriter implements TracerWriter {

    @Override
    public void writeEntering(Object source, String msg) {
        final Logger logger = loggerForEventSource(source);
        logger.trace(msg);
    }

    @Override
    public void writeReturning(Object source, String msg) {
        final Logger logger = loggerForEventSource(source);
        logger.trace(msg);
    }

    @Override
    public void writeExiting(Object source, String msg) {
        final Logger logger = loggerForEventSource(source);
        logger.trace(msg);
    }

    @Override
    public void writeValueChange(Object source, String msg) {
        final Logger logger = loggerForEventSource(source);
        logger.trace(msg);
    }

    @Override
    public void writeError(Object source, String msg) {
        final Logger logger = loggerForEventSource(source);
        logger.trace(msg);
    }

    @Override
    public void writeTimeout(Object source, String msg) {
        final Logger logger = loggerForEventSource(source);
        logger.trace(msg);
    }

    private static Logger loggerForEventSource(Object source) {
        Objects.requireNonNull(source);

        Class<?> clazz;
        if (source instanceof Class<?>) {
            clazz = (Class<?>) source;
        }
        else {
            clazz = source.getClass();
        }

        return LoggerFactory.getLogger(clazz);
    }

}