package com.makkajai.dev.challenge.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConsoleInputReader {
    
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public List<String> read() throws IOException {
        System.out.println("enter input:");

        List<String> input = new ArrayList<>();
        while (true) {
            String line = reader.readLine().trim();
            if (line.isBlank())
                break;
            input.add(line);
        }
        return input;
    }
}
