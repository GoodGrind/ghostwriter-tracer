package io.ghostwriter.rt.tracer;


import io.ghostwriter.rt.tracer.serializer.StringSerializer;
import io.ghostwriter.rt.tracer.serializer.TracerSerializer;
import io.ghostwriter.rt.tracer.writer.TracerWriter;


public class StringTracerWriter implements TracerWriter {

    private final StringBuilder sb = new StringBuilder();

    private final TracerSerializer serializer;

    public StringTracerWriter() {
        this(new StringSerializer());
    }

    public StringTracerWriter(TracerSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public void writeEntering(Object source, String method, Object... params) {
        sb.append(serializer.entering(source, method, params));
    }

    @Override
    public void writeReturning(Object source, String method, Object returnValue) {
        sb.append(serializer.returning(source, method, returnValue));
    }

    @Override
    public void writeExiting(Object source, String method) {
        sb.append(serializer.exiting(source, method));
    }

    @Override
    public void writeValueChange(Object source, String method, String variable, Object value) {
        sb.append(serializer.valueChange(source, method, variable, value));
    }

    @Override
    public void writeError(Object source, String method, Throwable error) {
        sb.append(serializer.onError(source, method, error));
    }

    @Override
    public void writeTimeout(Object source, String method, long timeoutThreshold, long timeout) {
        sb.append(serializer.timeout(source, method, timeoutThreshold, timeout));
    }

    @Override
    public String toString() {
        return sb.toString();
    }

}
