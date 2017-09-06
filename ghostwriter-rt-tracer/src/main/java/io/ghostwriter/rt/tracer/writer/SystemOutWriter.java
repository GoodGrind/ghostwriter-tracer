package io.ghostwriter.rt.tracer.writer;


import io.ghostwriter.rt.tracer.serializer.SerializerLoader;
import io.ghostwriter.rt.tracer.serializer.TracerSerializer;

public class SystemOutWriter implements TracerWriter {

    private final TracerSerializer serializer;

    public SystemOutWriter() {
        this(SerializerLoader.load());
    }

    public SystemOutWriter(TracerSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public void writeEntering(Object source, String method, Object... params) {
        System.out.println(serializer.entering(source, method, params));
    }

    @Override
    public void writeReturning(Object source, String method, Object returnValue) {
        System.out.println(serializer.returning(source, method, returnValue));
    }

    @Override
    public void writeExiting(Object source, String method) {
        System.out.println(serializer.exiting(source, method));
    }

    @Override
    public void writeValueChange(Object source, String method, String variable, Object value) {
        System.out.println(serializer.valueChange(source, method, variable, value));
    }

    @Override
    public void writeError(Object source, String method, Throwable error) {
        System.out.println(serializer.onError(source, method, error));
    }

    @Override
    public void writeTimeout(Object source, String method, long timeoutThreshold, long timeout) {
        System.out.println(serializer.timeout(source, method, timeoutThreshold, timeout));
    }

}
