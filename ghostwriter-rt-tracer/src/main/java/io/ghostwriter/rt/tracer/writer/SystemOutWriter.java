package io.ghostwriter.rt.tracer.writer;


public class SystemOutWriter implements TracerWriter {

    @Override
    public void writeEntering(Object source, String msg) {
        System.out.println(msg);
    }

    @Override
    public void writeReturning(Object source, String msg) {
        System.out.println(msg);
    }

    @Override
    public void writeExiting(Object source, String msg) {
        System.out.println(msg);
    }

    @Override
    public void writeValueChange(Object source, String msg) {
        System.out.println(msg);
    }

    @Override
    public void writeError(Object source, String msg) {
        System.out.println(msg);
    }

    @Override
    public void writeTimeout(Object source, String msg) {
        System.out.println(msg);
    }

}
