package com.makkajai.dev.challenge;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import com.makkajai.dev.challenge.ds.planar.Vec2i;
import com.makkajai.dev.challenge.io.ConsoleInputReader;
import com.makkajai.dev.challenge.parsers.TextParser;

import lombok.extern.java.Log;

@Log
public class ApplicationMain {
    public static void main( String[] args )
    {

        LOG.info("Starting simulation.");

        Simulator gameOfLife = new Simulator();
        List<Vec2i> coordinates = null;
        
        try (ConsoleInputReader consoleInputReader = new ConsoleInputReader()) {
            System.out.println("enter seed coordinates:");
            List<String> rawCoordStrings = consoleInputReader.read();

            coordinates = TextParser.parseCoordinates(rawCoordStrings);
        } catch(IOException | NumberFormatException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            System.exit(1);
        }
        
        gameOfLife.tick(coordinates).stream().forEach(System.out::println);
    }
}
