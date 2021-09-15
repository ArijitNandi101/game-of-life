package com.makkajai.dev.challenge.parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.makkajai.dev.challenge.ds.Vec2i;



public class TextParser {

    public static List<Vec2i> parseCoordinates(List<String> rawCoordinateStrings) throws IOException {
        
        List<Vec2i> coordinates = new ArrayList<>();
        for(String rawCoordinateString: rawCoordinateStrings) {    
            String[] tokens = rawCoordinateString.split("\\s*,\\s*");
            coordinates.add(new Vec2i(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1])));            
        }
        return coordinates;
        // return List.of(new Vec2i(1, 1), new Vec2i(1, 2), new Vec2i(2, 1), new Vec2i(2, 2));
        // return List.of(new Vec2i(-3, 0), new Vec2i(-3, -1), new Vec2i(-3, 1));
        // return List.of(new Vec2i(0, 1), new Vec2i(1, 0), new Vec2i(2, 1), new Vec2i(0, 2), new Vec2i(1, 2));
    }
    
}
