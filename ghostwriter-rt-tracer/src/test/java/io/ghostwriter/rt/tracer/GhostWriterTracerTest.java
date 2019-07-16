package io.ghostwriter.rt.tracer;


import io.ghostwriter.rt.tracer.serializer.StringSerializer;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class GhostWriterTracerTest {

    @Test
    public void testMethodCallTracing() {
        StringTracerWriter tracerWriter = new StringTracerWriter(new StringSerializer(true, true));
        GhostWriterTracer tracer = new GhostWriterTracer(tracerWriter);

        tracer.entering(this, "testMethodCallTracing", "c", 123, "d", 444);
        tracer.valueChange(this, "testMethodCallTracing", "say", "hello");
        tracer.returning(this, "testMethodCallTracing", "goodbye");
        tracer.exiting(this, "testMethodCallTracing");

        final String repr = tracerWriter.toString();
        assertTrue(repr.equals(
                "io.ghostwriter.rt.tracer.GhostWriterTracerTest.testMethodCallTracing(c = 123, d = 444) {\n" +
                "   say = \"hello\"\n" +
                "   return \"goodbye\"\n" +
                "}\n"));
    }

    @Test
    public void testNestedMethodCalls() {
        StringTracerWriter tracerWriter = new StringTracerWriter(new StringSerializer(true, true));
        GhostWriterTracer tracer = new GhostWriterTracer(tracerWriter);

        tracer.entering(this, "method1", "a", 1, "b", 2);
        tracer.entering(this, "method2");
        tracer.entering(this, "method3");
        tracer.returning(this, "method3", 23);
        tracer.exiting(this, "method3");
        tracer.exiting(this, "method2");
        tracer.exiting(this, "method1");

        final String repr = tracerWriter.toString();
        assertTrue(repr.equals(
                "io.ghostwriter.rt.tracer.GhostWriterTracerTest.method1(a = 1, b = 2) {\n" +
                "   io.ghostwriter.rt.tracer.GhostWriterTracerTest.method2() {\n" +
                "      io.ghostwriter.rt.tracer.GhostWriterTracerTest.method3() {\n" +
                "         return 23\n" +
                "      }\n" +
                "   }\n" +
                "}\n"));
    }

    @Test
    public void testDisabledIndentation() {
        StringTracerWriter tracerWriter = new StringTracerWriter(new StringSerializer(false, true));
        GhostWriterTracer tracer = new GhostWriterTracer(tracerWriter);

        tracer.entering(this, "method1", "a", 1, "b", 2);
        tracer.entering(this, "method2");
        tracer.entering(this, "method3");
        tracer.returning(this, "method3", 23);
        tracer.exiting(this, "method3");
        tracer.exiting(this, "method2");
        tracer.exiting(this, "method1");

        final String repr = tracerWriter.toString();
        assertTrue(repr.equals(
                "io.ghostwriter.rt.tracer.GhostWriterTracerTest.method1(a = 1, b = 2) {\n" +
                "io.ghostwriter.rt.tracer.GhostWriterTracerTest.method2() {\n" +
                "io.ghostwriter.rt.tracer.GhostWriterTracerTest.method3() {\n" +
                "return 23\n" +
                "}\n" +
                "}\n" +
                "}\n"));
    }

    @Test
    public void testExceptionsInToStringAreHandled() {
        StringTracerWriter tracerWriter = new StringTracerWriter(new StringSerializer(true, true));
        GhostWriterTracer tracer = new GhostWriterTracer(tracerWriter);

        tracer.entering(this, "testMethodCallTracing", "faultyToString", new FaultyToString());

        final String repr = tracerWriter.toString();
        assertEquals(repr, "io.ghostwriter.rt.tracer.GhostWriterTracerTest.testMethodCallTracing(faultyToString = ???) {\n");
    }

    @Test
    public void testUnwrapArrays() {
        StringTracerWriter tracerWriter = new StringTracerWriter(new StringSerializer(true, true));
        GhostWriterTracer tracer = new GhostWriterTracer(tracerWriter);

        String[] arr = {"a", "b", "c"};
        tracer.entering(this, "testMethodCallTracing", "arr", arr);

        final String repr = tracerWriter.toString();
        assertEquals(repr, "io.ghostwriter.rt.tracer.GhostWriterTracerTest.testMethodCallTracing(arr = [a, b, c]) {\n");
    }

}
