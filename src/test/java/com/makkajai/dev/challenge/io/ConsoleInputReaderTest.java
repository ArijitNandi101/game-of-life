package com.makkajai.dev.challenge.io;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ConsoleInputReaderTest {

    static InputStream sysInBackup = System.in; 
    
    @Test
    void testValidInput() {
        System.setIn(new ByteArrayInputStream("1, 2\n 2, -1 \n\n".getBytes()));

        assertDoesNotThrow(() -> {
            ConsoleInputReader reader = new ConsoleInputReader();

            List<String> lines = reader.read();
            assertNotNull(lines);
            assertEquals(2, lines.size());
            reader.close();
        });

        System.setIn(new ByteArrayInputStream("1, 2\n Test_Value, -1\n word \n\n".getBytes()));

        assertDoesNotThrow(() -> {
            ConsoleInputReader reader = new ConsoleInputReader();

            List<String> lines = reader.read();
            assertNotNull(lines);
            assertEquals(3, lines.size());
            reader.close();
        });
    }

    @AfterEach
    void reset() {
        System.setIn(sysInBackup);
    }
}
