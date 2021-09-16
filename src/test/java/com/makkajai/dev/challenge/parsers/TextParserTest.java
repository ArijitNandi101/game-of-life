package com.makkajai.dev.challenge.parsers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.makkajai.dev.challenge.ds.planar.Vec2i;

import org.junit.jupiter.api.Test;

class TextParserTest {
    
    @Test
    void testParseCoordinate() {

        List<String> rawCoordinateStrings = List.of("1, 2", " 2,-2", "-17, -12 ");
        assertDoesNotThrow(() -> {
            List<Vec2i> coordinates = TextParser.parseCoordinates(rawCoordinateStrings);
            assertNotNull(coordinates);
            assertEquals(3, coordinates.size());

            assertEquals(new Vec2i(1, 2), coordinates.get(0));
            assertEquals(new Vec2i(2, -2), coordinates.get(1));
            assertEquals(new Vec2i(-17, -12), coordinates.get(2));
        });
    }

    @Test
    void testParseCoordinatExceptional() {

        assertThrows(NumberFormatException.class, () -> {
            TextParser.parseCoordinates(List.of("1, 2", " 2,- 2", "-17, -12 "));
        });

        assertThrows(NumberFormatException.class, () -> {
            TextParser.parseCoordinates(List.of("1, 2", " 2,-2", "-17 -12 "));
        });

        assertThrows(NumberFormatException.class, () -> {
            TextParser.parseCoordinates(List.of("1, 2", " 2,-2", "Bad_Value, -12 "));
        });
    }
}
