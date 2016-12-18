package io.ghostwriter.rt.tracer.writer;


public interface TracerWriter {

    void writeEntering(Object source, String msg);

    void writeReturning(Object source, String msg);

    void writeExiting(Object source, String msg);

    void writeValueChange(Object source, String msg);

    void writeError(Object source, String msg);

    void writeTimeout(Object source, String msg);

}
