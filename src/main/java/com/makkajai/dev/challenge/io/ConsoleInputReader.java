package com.makkajai.dev.challenge.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.java.Log;

@Log
public class ConsoleInputReader implements Closeable {
    
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public List<String> read() throws IOException {

        List<String> input = new ArrayList<>();
        while (true) {
            String line = reader.readLine().trim();
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
