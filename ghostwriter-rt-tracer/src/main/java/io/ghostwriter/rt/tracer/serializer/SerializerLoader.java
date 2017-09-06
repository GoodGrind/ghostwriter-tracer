package io.ghostwriter.rt.tracer.serializer;


import io.ghostwriter.rt.tracer.GhostWriterTracer;

import java.util.ServiceLoader;

public class SerializerLoader {

    public static TracerSerializer load() {
        ServiceLoader<TracerSerializer> serviceLoader =
                ServiceLoader.load(TracerSerializer.class, GhostWriterTracer.class.getClassLoader());

        TracerSerializer loadedSerializer = null;
        for (TracerSerializer serializer : serviceLoader) {
            // TODO: add logging for displaying the found implementations
            loadedSerializer = serializer;
        }

        // no imp. found, revert to default
        if (loadedSerializer == null) {
            // TODO: add logging for showing that we reverted to the default one
            loadedSerializer = new StringSerializer();
        }

        return loadedSerializer;
    }

}
