package io.ghostwriter.rt.tracer.writer;


public interface TracerWriter {

    void writeEntering(String msg);

    void writeReturning(String msg);

    void writeExiting(String msg);

    void writeValueChange(String msg);

    void writeError(String msg);

    void writeTimeout(String msg);

}
