package com.makkajai.dev.challenge.parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.makkajai.dev.challenge.ds.planar.Vec2i;


/**
 * This class constains static utility parsing functions for various forms of input.
 */
public class TextParser {

    /**
     * This function takes in raw strings describing cartesian coordinates and
     * parses then to produce a list of
     * {@link com.makkajai.dev.challenge.ds.planar.Vec2i} representing these
     * coordinates.
     * 
     * @param rawCoordinateStrings the raw strings containing the coordinate data.
     *          It is expected to b in the form of "x_coordinate, y_Coordinate".
     * @return the list of cartsian coordinates as
     *         {@link com.makkajai.dev.challenge.ds.planar.Vec2i}
     * @throws IOException in case it tries to parse an invalid raw coordinate sting
     */
    public static List<Vec2i> parseCoordinates(List<String> rawCoordinateStrings) throws IOException {
        return rawCoordinateStrings.stream()
            .map(rawCoordinateString -> rawCoordinateString.split("\\s*,\\s*"))
            .map(tokens -> new Vec2i(
                Integer.parseInt(tokens[0].trim()), 
                Integer.parseInt(tokens[1].trim()))
            ).collect(Collectors.toList());
    }
    
}
