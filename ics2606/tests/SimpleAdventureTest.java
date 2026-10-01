package ics2606.mp2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * End-to-end regression test for the whole program.
 *
 * The expected transcript was captured from this implementation's own output,
 * not from the assignment's "Program Output of SimpleAdventure" section, which
 * was never supplied. It therefore locks in current behaviour rather than
 * independently confirming the behaviour the spec asks for. Replace
 * simple-adventure-expected-output.txt with the real section from the
 * assignment to make this an authoritative check.
 *
 * The path is relative to the project root, so run the suite from there.
 */
class SimpleAdventureTest {

    private static final Path EXPECTED =
            Path.of("ics2606", "tests", "simple-adventure-expected-output.txt");

    @Test
    @DisplayName("the full program prints exactly the expected transcript")
    void main_printsExpectedTranscript() {
        CaptureStdOut capture = CaptureStdOut.start();
        SimpleAdventure.main(new String[] {});
        String actual = capture.stop();

        assertEquals(readExpected(), actual);
    }

    private static String readExpected() {
        try {
            return Files.readString(EXPECTED, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot read " + EXPECTED.toAbsolutePath(), e);
        }
    }
}