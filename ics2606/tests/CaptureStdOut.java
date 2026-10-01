package ics2606.mp2;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * Swaps System.out for an in-memory stream so tests can assert on exact
 * console output, then restores the real stream even if the test fails.
 */
public final class CaptureStdOut {

    private final PrintStream originalOut;
    private final ByteArrayOutputStream buffer;

    private CaptureStdOut() {
        this.originalOut = System.out;
        this.buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.buffer, true, StandardCharsets.UTF_8));
    }

    public static CaptureStdOut start() {
        return new CaptureStdOut();
    }

    /** Everything written to System.out since start(), verbatim. */
    public String captured() {
        System.out.flush();
        try {
            return this.buffer.toString(StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public String stop() {
        String captured = captured();
        System.setOut(this.originalOut);
        return captured;
    }
}