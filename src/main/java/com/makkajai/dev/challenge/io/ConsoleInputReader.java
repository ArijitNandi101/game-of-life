package com.makkajai.dev.challenge.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.java.Log;

/**
 * This class is used to read inputs from the console.
 */
@Log
public class ConsoleInputReader implements Closeable {
    
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Reads inputs from the console (i.e. the std input stream) using a
     * {@link java.io.BufferedReader} one line at a time until a blank or empty line
     * is encountered where it stops reading.
     * 
     * @return The {@link java.util.List} of lines read as {@link String}.
     * @throws IOException in case something unxpected occurs while reading the input
     */
    public List<String> read() throws IOException {

        List<String> input = new ArrayList<>();
        while (true) {
            String line = reader.readLine();

            //stop reading from the console input stream if the last line read
            // is blank or empty.
            if (line.isBlank()) break;
            input.add(line);
        }

        LOG.info(String.format("Read %d lines from console.", input.size()));

        return input;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
