package io.ghostwriter.rt.tracer.writer;


import io.ghostwriter.rt.tracer.GhostWriterTracer;

import java.util.ServiceLoader;


public class WriterLoader {

    public static TracerWriter load() {
        final ServiceLoader<TracerWriter> serviceLoader =
                ServiceLoader.load(TracerWriter.class, GhostWriterTracer.class.getClassLoader());

        TracerWriter loadedWriter = null;
        for (TracerWriter writer : serviceLoader) {
            // TODO: add logging for displaying the found implementations
            loadedWriter = writer;
        }

        // no imp. found, revert to default
        if (loadedWriter == null) {
            // TODO: add logging for showing that we reverted to the default one
            loadedWriter = new SystemOutWriter();
        }

        return loadedWriter;
    }

}
