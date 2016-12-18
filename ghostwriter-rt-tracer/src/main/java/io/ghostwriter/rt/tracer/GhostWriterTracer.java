package io.ghostwriter.rt.tracer;

import io.ghostwriter.Tracer;
import io.ghostwriter.rt.tracer.serializer.StringSerializer;
import io.ghostwriter.rt.tracer.serializer.TracerSerializer;
import io.ghostwriter.rt.tracer.writer.SystemOutWriter;
import io.ghostwriter.rt.tracer.writer.TracerWriter;

import java.util.Objects;
import java.util.ServiceLoader;

public class GhostWriterTracer implements Tracer {

    private final TracerSerializer serializer;

    private final TracerWriter writer;

    public GhostWriterTracer(TracerSerializer serializer, TracerWriter writer) {
        this.serializer = Objects.requireNonNull(serializer);
        this.writer = Objects.requireNonNull(writer);
    }

    public GhostWriterTracer() {
        this(loadSearializer(), loadWriter());
    }

    @Override
    public void entering(Object source, String method, Object... params) {
        final String msg = serializer.entering(source, method, params);
        writer.writeEntering(source, msg);
    }

    @Override
    public void exiting(Object source, String method) {
        final String msg = serializer.exiting(source, method);
        writer.writeExiting(source, msg);
    }

    @Override
    public void valueChange(Object source, String method, String variable, Object value) {
        final String msg = serializer.valueChange(source, method, variable, value);
        writer.writeValueChange(source, msg);
    }

    @Override
    public <T> void returning(Object source, String method, T returnValue) {
        final String msg = serializer.returning(source, method, returnValue);
        writer.writeReturning(source, msg);
    }

    @Override
    public void onError(Object source, String method, Throwable error) {
        final String msg = serializer.onError(source, method, error);
        writer.writeError(source, msg);
    }

    @Override
    public void timeout(Object source, String method, long timeoutThreshold, long timeout) {
        final String msg = serializer.timeout(source, method, timeoutThreshold, timeout);
        writer.writeTimeout(source, msg);
    }

    private static TracerSerializer loadSearializer() {
        ServiceLoader<TracerSerializer> serviceLoader = ServiceLoader.load(TracerSerializer.class);

        TracerSerializer loadedSerializer = null;
        for (TracerSerializer serializer : serviceLoader) {
            loadedSerializer = serializer;
        }

        // no imp. found, revert to default
        if (loadedSerializer == null) {
            loadedSerializer = new StringSerializer();
        }

        return loadedSerializer;
    }

    private static TracerWriter loadWriter() {
        ServiceLoader<TracerWriter> serviceLoader = ServiceLoader.load(TracerWriter.class);

        TracerWriter loadedWriter = null;
        for (TracerWriter writer : serviceLoader) {
            loadedWriter = writer;
        }

        // no imp. found, revert to default
        if (loadedWriter == null) {
            loadedWriter = new SystemOutWriter();
        }

        return loadedWriter;
    }

}
