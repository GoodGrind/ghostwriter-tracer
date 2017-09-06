package io.ghostwriter.rt.tracer.writer;

import io.ghostwriter.rt.tracer.serializer.SerializerLoader;
import io.ghostwriter.rt.tracer.serializer.StringSerializer;
import io.ghostwriter.rt.tracer.serializer.TracerSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Writes all event entries to an SLF4J handler.
 * The used log level will be 'trace' and the event source class will be used as the log handler.
 */
public class Slf4JWriter implements TracerWriter {

    private final TracerSerializer serializer;

    public Slf4JWriter() {
        final TracerSerializer loaded = SerializerLoader.load();

        final boolean defaultSerializerLoaded = loaded instanceof StringSerializer;
        if (defaultSerializerLoaded) {
            // for SLF4J output we don't need the formatting feature because that leads to additional empty lines
            serializer = new StringSerializer(true, false);
        } else {
            serializer = loaded;
        }
    }

    public Slf4JWriter(TracerSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public void writeEntering(Object source, String method, Object... params) {
        final Logger logger = loggerForEventSource(source);
        if (logger.isTraceEnabled()) {
            logger.trace(serializer.entering(source, method, params));
        }
    }

    @Override
    public void writeReturning(Object source, String method, Object returnValue) {
        final Logger logger = loggerForEventSource(source);
        if (logger.isTraceEnabled()) {
            logger.trace(serializer.returning(source, method, returnValue));
        }
    }

    @Override
    public void writeExiting(Object source, String method) {
        final Logger logger = loggerForEventSource(source);
        if (logger.isTraceEnabled()) {
            logger.trace(serializer.exiting(source, method));
        }
    }

    @Override
    public void writeValueChange(Object source, String method, String variable, Object value) {
        final Logger logger = loggerForEventSource(source);
        if (logger.isTraceEnabled()) {
            logger.trace(serializer.valueChange(source, method, variable, value));
        }
    }

    @Override
    public void writeError(Object source, String method, Throwable error) {
        final Logger logger = loggerForEventSource(source);
        if (logger.isTraceEnabled()) {
            logger.trace(serializer.onError(source, method, error));
        }
    }

    @Override
    public void writeTimeout(Object source, String method, long timeoutThreshold, long timeout) {
        final Logger logger = loggerForEventSource(source);
        if (logger.isTraceEnabled()) {
            logger.trace(serializer.timeout(source, method, timeoutThreshold, timeout));
        }
    }

    private static Logger loggerForEventSource(Object source) {
        Objects.requireNonNull(source);

        Class<?> clazz;
        if (source instanceof Class<?>) {
            clazz = (Class<?>) source;
        } else {
            clazz = source.getClass();
        }

        return LoggerFactory.getLogger(clazz);
    }

}