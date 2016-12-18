package io.ghostwriter.rt.tracer.serializer;


public class StringSerializer implements TracerSerializer {

    private final Indentation indentation;

    private final boolean doIndent;

    public StringSerializer(boolean doIndent) {
        this.indentation = new Indentation();
        this.doIndent = doIndent;
    }

    public StringSerializer() {
        this(true);
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

        final String context = serialize(contextClass.getCanonicalName());

        if (doIndent) {
            indentation.apply(sb);
        }

        sb.append(context).append(".").append(method).append("(");
        appendParameters(sb, params).append(") {\n");

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
        sb.append("}\n");

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
        sb.append(variable).append(" = ").append(strValue).append("\n");

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
        sb.append("<return> ").append(value).append("\n");

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
        sb.append("<error> ").append(errorStr);

        return sb.toString();
    }

    @Override
    public String timeout(Object source, String method, long timeoutThreshold, long timeout) {
        final int DEFAULT_CAPACITY = 64;
        final StringBuffer sb = new StringBuffer(DEFAULT_CAPACITY);

        if (doIndent) {
            indentation.apply(sb);
        }
        sb.append("<timeout> ").append(timeout).append("\n");

        return sb.toString();
    }

    private StringBuffer appendParameters(StringBuffer sb, Object[] params) {
        final int NUMBER_OF_ENTRIES_PER_PARAMETERS = 2;

        for (int i = 0; i < params.length; i += NUMBER_OF_ENTRIES_PER_PARAMETERS) {
            Object parameterName = params[i];
            Object parameterValue = params[i + 1];
            String name = serialize(parameterName);
            String value = serialize(parameterValue);
            sb.append(name).append(" = ").append(value);

            boolean isFinalParameter = i == (params.length - NUMBER_OF_ENTRIES_PER_PARAMETERS);
            if (!isFinalParameter) {
                sb.append(", ");
            }
        }

        return sb;
    }

    protected String serialize(Object value) {
        return String.valueOf(value);
    }

}
