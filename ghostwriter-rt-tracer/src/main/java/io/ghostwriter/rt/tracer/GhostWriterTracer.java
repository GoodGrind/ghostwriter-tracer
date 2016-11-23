package io.ghostwriter.rt.tracer;

import io.ghostwriter.Tracer;
import io.ghostwriter.rt.tracer.serializer.StringSerializer;
import io.ghostwriter.rt.tracer.serializer.TracerSerializer;
import io.ghostwriter.rt.tracer.writer.TracerWriter;

import java.util.Objects;

public class GhostWriterTracer implements Tracer {

    private final TracerSerializer serializer;

    private final TracerWriter writer;

    private final Indentation indentation;

    public GhostWriterTracer(TracerSerializer serializer, TracerWriter writer) {
        this.serializer = Objects.requireNonNull(serializer);
        this.writer = Objects.requireNonNull(writer);
        this.indentation = new Indentation();
    }

    public GhostWriterTracer() {
        this(new StringSerializer(), null);
    }

    @Override
    public void entering(Object source, String method, Object... params) {
        final int DEFAULT_CAPACITY = 255;
        StringBuffer sb = new StringBuffer(DEFAULT_CAPACITY);

        Class<?> contextClass;
        if (source instanceof Class<?>) {
            contextClass = (Class<?>) source;
        }
        else {
            contextClass = source.getClass();
        }
        assert contextClass != null;

        final String context = serializer.serialize(contextClass.getCanonicalName());

        indentation.apply(sb).append(context).append(".").append(method).append("(");
        appendParameters(sb, params).append(") {\n");

        writer.writeEntering(sb.toString());

        indentation.indent();
    }

    @Override
    public void exiting(Object source, String method) {
        final int DEFAULT_CAPACITY = 32;
        final StringBuffer sb = new StringBuffer(DEFAULT_CAPACITY);

        indentation.dedent();
        indentation.apply(sb).append("}\n");

        writer.writeExiting(sb.toString());
    }

    @Override
    public void valueChange(Object source, String method, String variable, Object value) {
        final int DEFAULT_CAPACITY = 64;
        final StringBuffer sb = new StringBuffer(DEFAULT_CAPACITY);

        final String strValue = serializer.serialize(value);
        indentation.apply(sb).append(variable).append(" = ").append(strValue).append("\n");

        writer.writeValueChange(sb.toString());
    }

    @Override
    public <T> void returning(Object source, String method, T returnValue) {
        final int DEFAULT_CAPACITY = 64;
        final StringBuffer sb = new StringBuffer(DEFAULT_CAPACITY);

        final String value = serializer.serialize(returnValue);
        indentation.apply(sb).append("<return> ").append(value).append("\n");

        writer.writeReturning(sb.toString());
    }

    @Override
    public void onError(Object source, String method, Throwable error) {
        final int DEFAULT_CAPACITY = 128;
        final StringBuffer sb = new StringBuffer(DEFAULT_CAPACITY);

        final String errorStr = serializer.serialize(error);
        indentation.apply(sb).append("<error> ").append(errorStr);

        writer.writeError(sb.toString());
    }

    @Override
    public void timeout(Object source, String method, long timeoutThreshold, long timeout) {
        final int DEFAULT_CAPACITY = 64;
        final StringBuffer sb = new StringBuffer(DEFAULT_CAPACITY);
        indentation.apply(sb).append("<timeout> ").append(timeout).append("\n");

        writer.writeTimeout(sb.toString());
    }

    private StringBuffer appendParameters(StringBuffer sb, Object[] params) {
        final int NUMBER_OF_ENTRIES_PER_PARAMETERS = 2;

        for (int i = 0; i < params.length; i += NUMBER_OF_ENTRIES_PER_PARAMETERS) {
            Object parameterName = params[i];
            Object parameterValue = params[i + 1];
            String name = serializer.serialize(parameterName);
            String value = serializer.serialize(parameterValue);
            sb.append(name).append(" = ").append(value);

            boolean isFinalParameter = i == (params.length - NUMBER_OF_ENTRIES_PER_PARAMETERS);
            if (!isFinalParameter) {
                sb.append(", ");
            }
        }

        return sb;
    }

}
