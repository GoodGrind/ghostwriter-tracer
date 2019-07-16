package io.ghostwriter.rt.tracer;


public class FaultyToString {

    @Override
    public String toString() {
        throw new RuntimeException("Boom");
    }

}
