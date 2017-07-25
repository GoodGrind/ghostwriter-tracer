package io.ghostwriter.rt.tracer.serializer;

// tag::serializer[]
public interface TracerSerializer {

    String entering(Object source, String method, Object... params);

    String exiting(Object source, String method);

    String valueChange(Object source, String method, String variable, Object value);

    String returning(Object source, String method, Object returnValue);

    String onError(Object source, String method, Throwable error);

    String timeout(Object source, String method, long timeoutThreshold, long timeout);

}
// end::serializer[]
