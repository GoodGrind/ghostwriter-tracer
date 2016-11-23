package io.ghostwriter.rt.tracer;


import io.ghostwriter.rt.tracer.writer.TracerWriter;

public class StringTracerWriter implements TracerWriter {

    private StringBuilder sb = new StringBuilder();

    @Override
    public void writeEntering(String msg) {
        sb.append(msg);
    }

    @Override
    public void writeReturning(String msg) {
        sb.append(msg);
    }

    @Override
    public void writeExiting(String msg) {
        sb.append(msg);
    }

    @Override
    public void writeValueChange(String msg) {
        sb.append(msg);
    }

    @Override
    public void writeError(String msg) {
        sb.append(msg);
    }

    @Override
    public void writeTimeout(String msg) {
        sb.append(msg);
    }

    @Override
    public String toString() {
        return sb.toString();
    }

}
