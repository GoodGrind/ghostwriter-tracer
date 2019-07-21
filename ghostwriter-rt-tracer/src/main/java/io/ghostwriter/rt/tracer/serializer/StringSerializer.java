package io.ghostwriter.rt.tracer.serializer;

import java.util.Arrays;


public class StringSerializer implements TracerSerializer {

    private final Indentation indentation;

    private final boolean doIndent;

    private final boolean isFormatted;

    public StringSerializer(boolean doIndent, boolean isFormatted) {
        this.indentation = new Indentation();
        this.doIndent = doIndent;
        this.isFormatted = isFormatted;
    }

    public StringSerializer() {
        this(true, false);
    }

    @Override
    public String entering(Object source, String method, Object... params) {
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

        final String context = contextClass.getCanonicalName();

        if (doIndent) {
            indentation.apply(sb);
        }

        sb.append(context).append(".").append(method).append("(");
        appendParameters(sb, params).append(") {");

        if (isFormatted) {
            sb.append('\n');
        }

        if (doIndent) {
            indentation.indent();
        }

        return sb.toString();
    }

    @Override
    public String exiting(Object source, String method) {
        final int DEFAULT_CAPACITY = 32;
        final StringBuffer sb = new StringBuffer(DEFAULT_CAPACITY);

        if (doIndent) {
            indentation.dedent();
            indentation.apply(sb);
        }
        sb.append("}");

        if (isFormatted) {
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public String valueChange(Object source, String method, String variable, Object value) {
        final int DEFAULT_CAPACITY = 64;
        final StringBuffer sb = new StringBuffer(DEFAULT_CAPACITY);

        final String strValue = serialize(value);
        if (doIndent) {
            indentation.apply(sb);
        }
        sb.append(variable).append(" = ").append(strValue);

        if (isFormatted) {
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public String returning(Object source, String method, Object returnValue) {
        final int DEFAULT_CAPACITY = 64;
        final StringBuffer sb = new StringBuffer(DEFAULT_CAPACITY);

        final String value = serialize(returnValue);
        if (doIndent) {
            indentation.apply(sb);
        }
        sb.append("return ").append(value);

        if (isFormatted) {
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public String onError(Object source, String method, Throwable error) {
        final int DEFAULT_CAPACITY = 128;
        final StringBuffer sb = new StringBuffer(DEFAULT_CAPACITY);

        if (doIndent) {
            indentation.apply(sb);
        }
        final String errorStr = serialize(error);
        sb.append("ERROR: ").append(errorStr);

        if (isFormatted) {
            sb.append("\n");
        }

        return sb.toString();
    }

    private StringBuffer appendParameters(StringBuffer sb, Object[] params) {
        final int NUMBER_OF_ENTRIES_PER_PARAMETERS = 2;

        for (int i = 0; i < params.length; i += NUMBER_OF_ENTRIES_PER_PARAMETERS) {
            Object parameterName = params[i];
            Object parameterValue = params[i + 1];
            final String name = String.valueOf(parameterName);
            final String value = serialize(parameterValue);
            sb.append(name).append(" = ").append(value);

            boolean isFinalParameter = i == (params.length - NUMBER_OF_ENTRIES_PER_PARAMETERS);
            if (!isFinalParameter) {
                sb.append(", ");
            }
        }

        return sb;
    }

    // Override if you want to have a different toString solution used, such as one based on reflection
    protected String serialize(Object value) {
        try {
            if (value instanceof Object[]) {
                return Arrays.toString((Object[])value);
            } else if (value instanceof boolean[]) {
                return Arrays.toString((boolean[])value);
            } else if (value instanceof byte[]) {
                return Arrays.toString((byte[])value);
            } else if (value instanceof short[]) {
                return Arrays.toString((short[])value);
            } else if (value instanceof char[]) {
                return Arrays.toString((char[])value);
            } else if (value instanceof int[]) {
                return Arrays.toString((int[])value);
            } else if (value instanceof long[]) {
                return Arrays.toString((long[])value);
            } else if (value instanceof float[]) {
                return Arrays.toString((float[])value);
            } else if (value instanceof double[]) {
                return Arrays.toString((double[])value);
            } else if (value instanceof String) {
                return "\"" + value + "\"";
            } else {
                return String.valueOf(value);
            }
        } catch (Throwable t) {
            return "???";
        }
    }

}
