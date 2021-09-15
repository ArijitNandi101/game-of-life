package com.makkajai.dev.challenge;

import java.io.IOException;
import java.util.List;
import com.makkajai.dev.challenge.ds.Vec2i;
import com.makkajai.dev.challenge.io.ConsoleInputReader;
import com.makkajai.dev.challenge.parsers.TextParser;

public class ApplicationMain {
    public static void main( String[] args )
    {
        ConsoleInputReader consoleInputReader = new ConsoleInputReader();

        Simulator gameOfLife = new Simulator();
        List<Vec2i> coordinates = null;
        try {
            List<String> rawCoordStrings = consoleInputReader.read();
            coordinates = TextParser.parseCoordinates(rawCoordStrings);
            
        } catch(IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        
        gameOfLife.tick(coordinates);
        gameOfLife.displayCurrentState();
    }
}
