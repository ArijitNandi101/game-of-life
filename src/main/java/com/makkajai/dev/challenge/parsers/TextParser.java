package com.makkajai.dev.challenge.parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.makkajai.dev.challenge.ds.planar.Vec2i;



public class TextParser {

    public static List<Vec2i> parseCoordinates(List<String> rawCoordinateStrings) throws IOException {
        return rawCoordinateStrings.stream()
            .map(rawCoordinateString -> rawCoordinateString.split("\\s*,\\s*"))
            .map(tokens -> new Vec2i(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1])))
            .collect(Collectors.toList());
    }
    
}
