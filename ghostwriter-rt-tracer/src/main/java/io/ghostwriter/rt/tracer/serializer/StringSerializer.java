package io.ghostwriter.rt.tracer.serializer;


public class StringSerializer implements TracerSerializer {

    @Override
    public String serialize(Object value) {
        return String.valueOf(value);
    }

}
