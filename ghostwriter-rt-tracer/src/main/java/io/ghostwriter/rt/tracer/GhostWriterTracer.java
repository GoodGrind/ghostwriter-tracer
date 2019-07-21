package io.ghostwriter.rt.tracer;

import io.ghostwriter.Tracer;
import io.ghostwriter.rt.tracer.writer.TracerWriter;
import io.ghostwriter.rt.tracer.writer.WriterLoader;

import java.util.Objects;

public class GhostWriterTracer implements Tracer {

    private final TracerWriter writer;

    public GhostWriterTracer(TracerWriter writer) {
        this.writer = Objects.requireNonNull(writer);
    }

    public GhostWriterTracer() {
        this(WriterLoader.load());
    }

    @Override
    public void entering(Object source, String method, Object... params) {
        writer.writeEntering(source, method, params);
    }

    @Override
    public void exiting(Object source, String method) {
        writer.writeExiting(source, method);
    }

    @Override
    public void valueChange(Object source, String method, String variable, Object value) {
        writer.writeValueChange(source, method, variable, value);
    }

    @Override
    public <T> void returning(Object source, String method, T returnValue) {
        writer.writeReturning(source, method, returnValue);
    }

    @Override
    public void onError(Object source, String method, Throwable error) {
        writer.writeError(source, method, error);
    }

}
