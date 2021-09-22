package com.makkajai.dev.challenge;

import com.makkajai.dev.challenge.ds.planar.Vec2i;
import com.makkajai.dev.challenge.io.ConsoleInputReader;
import com.makkajai.dev.challenge.main.Simulator;
import com.makkajai.dev.challenge.parsers.TextParser;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import lombok.extern.java.Log;

/**
 * This is the entry point class. It reads and parses input from the console, 
 * initializes a {@link Simulator} with the seed and ticks once to get the new
 * locations of alive entities in the Game of Life. It then displays the result
 * in the console.
 */
@Log
 public class ApplicationMain {
     
    public static void main( String[] args )
    {

        LOG.info("Starting simulation.");

        Simulator gameOfLife = new Simulator();
        List<Vec2i> coordinates = null;
        
        // read input from the console and parse the seed coordinates
        try (ConsoleInputReader consoleInputReader = new ConsoleInputReader()) {
            System.out.println("enter seed coordinates:");
            List<String> rawCoordStrings = consoleInputReader.read();

            coordinates = TextParser.parseCoordinates(rawCoordStrings);
        } catch(IOException | NumberFormatException e) {
            // exits on Input failure
            LOG.log(Level.SEVERE, e.getMessage(), e);
            System.exit(1);
        }
        
        // provides the seed values to the simulator for the simulation to begin.
        gameOfLife.seed(coordinates);
        // ticks (updates) the world once
        gameOfLife.tick();
        // displays the new alive entity coordinates
        gameOfLife.render();
    }
}
