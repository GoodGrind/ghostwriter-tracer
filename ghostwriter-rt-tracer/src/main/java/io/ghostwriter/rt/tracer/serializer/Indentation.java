package io.ghostwriter.rt.tracer.serializer;


final class Indentation {

    public static final String DEFAULT_INDENTATION_STRING = "   ";

    private final ThreadLocal<Integer> indentationLevel = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    private final String indentationString;

    public Indentation() {
        this(DEFAULT_INDENTATION_STRING);
    }

    public Indentation(String indentationString) {
        this.indentationString = indentationString;
    }

    public void indent() {
        final int currentLevel = indentationLevel.get();
        indentationLevel.set(currentLevel + 1);
    }

    public void dedent() {
        final int currentLevel = indentationLevel.get();
        final int newLevel = currentLevel == 0 ? currentLevel : currentLevel - 1;
        indentationLevel.set(newLevel);
    }

    public StringBuffer apply(StringBuffer stringBuffer) {
        final int level = indentationLevel.get();
        for (int i = 0; i < level; ++i) {
            stringBuffer.append(indentationString);
        }

        return stringBuffer;
    }

}
