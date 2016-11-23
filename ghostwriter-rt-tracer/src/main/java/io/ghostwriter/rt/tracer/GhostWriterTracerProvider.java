package io.ghostwriter.rt.tracer;

import io.ghostwriter.TracerProvider;

public class GhostWriterTracerProvider implements TracerProvider<GhostWriterTracer> {

    private final GhostWriterTracer ghostWriterTracer = new GhostWriterTracer();

    @Override
    public GhostWriterTracer getTracer() {
        return ghostWriterTracer;
    }

}
