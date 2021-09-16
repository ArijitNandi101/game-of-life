package com.makkajai.dev.challenge;

import java.io.IOException;
import java.util.List;

import com.makkajai.dev.challenge.ds.planar.Vec2i;
import com.makkajai.dev.challenge.io.ConsoleInputReader;
import com.makkajai.dev.challenge.parsers.TextParser;

public class ApplicationMain {
    public static void main( String[] args )
    {

        Simulator gameOfLife = new Simulator();
        List<Vec2i> coordinates = null;
        
        try (ConsoleInputReader consoleInputReader = new ConsoleInputReader()) {
            List<String> rawCoordStrings = consoleInputReader.read();

            coordinates = TextParser.parseCoordinates(rawCoordStrings);
        } catch(IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        
        gameOfLife.tick(coordinates).stream().forEach(System.out::println);
    }
}
