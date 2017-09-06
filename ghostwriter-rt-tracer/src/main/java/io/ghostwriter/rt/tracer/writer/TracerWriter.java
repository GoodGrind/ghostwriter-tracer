package io.ghostwriter.rt.tracer.writer;

// tag::writer[]
public interface TracerWriter {

    void writeEntering(Object source, String method, Object... params);

    void writeReturning(Object source, String method, Object returnValue);

    void writeExiting(Object source, String method);

    void writeValueChange(Object source, String method, String variable, Object value);

    void writeError(Object source, String method, Throwable error);

    void writeTimeout(Object source, String method, long timeoutThreshold, long timeout);

}
// end::writer[]
