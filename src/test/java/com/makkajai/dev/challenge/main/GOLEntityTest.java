package com.makkajai.dev.challenge.main;

import java.util.UUID;

import com.makkajai.dev.challenge.ds.planar.Vec2i;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GOLEntityTest {
   
    @Test
    void testConstructor() {
        GOLEntity2D entity = new GOLEntity2D(10, -5);
        assertEquals(UUID.nameUUIDFromBytes("{x: 10, y: -5}".getBytes()), entity.getId());
        assertEquals(new Vec2i(10, -5), entity.getPosition());

        assertEquals(8, entity.getNeighbourPositions().size());

        assertTrue(entity.getNeighbourPositions().contains(new Vec2i(9, -4)));
        assertTrue(entity.getNeighbourPositions().contains(new Vec2i(9, -5)));
        assertTrue(entity.getNeighbourPositions().contains(new Vec2i(9, -6)));
        assertTrue(entity.getNeighbourPositions().contains(new Vec2i(10, -4)));
        assertTrue(entity.getNeighbourPositions().contains(new Vec2i(10, -6)));
        assertTrue(entity.getNeighbourPositions().contains(new Vec2i(11, -4)));
        assertTrue(entity.getNeighbourPositions().contains(new Vec2i(11, -5)));
        assertTrue(entity.getNeighbourPositions().contains(new Vec2i(11, -6)));

        assertFalse(entity.getNeighbourPositions().contains(new Vec2i(10, -5)));
    }

    @Test
    void testConstructorVec2i() {
        GOLEntity2D entity = new GOLEntity2D(new Vec2i(10, -5));
        assertEquals(UUID.nameUUIDFromBytes(new Vec2i(10, -5).toString().getBytes()), entity.getId());
        assertEquals(new Vec2i(10, -5), entity.getPosition());

        assertEquals(8, entity.getNeighbourPositions().size());

        assertTrue(entity.getNeighbourPositions().contains(new Vec2i(9, -4)));
        assertTrue(entity.getNeighbourPositions().contains(new Vec2i(9, -5)));
        assertTrue(entity.getNeighbourPositions().contains(new Vec2i(9, -6)));
        assertTrue(entity.getNeighbourPositions().contains(new Vec2i(10, -4)));
        assertTrue(entity.getNeighbourPositions().contains(new Vec2i(10, -6)));
        assertTrue(entity.getNeighbourPositions().contains(new Vec2i(11, -4)));
        assertTrue(entity.getNeighbourPositions().contains(new Vec2i(11, -5)));
        assertTrue(entity.getNeighbourPositions().contains(new Vec2i(11, -6)));

        assertFalse(entity.getNeighbourPositions().contains(new Vec2i(10, -5)));
    }

    @Test
    void testToString() {
        assertEquals(
            String.format("{id: %s, position: %s }",UUID.nameUUIDFromBytes(new Vec2i(10, -5).toString().getBytes()), new Vec2i(10, -5)),
            new GOLEntity2D(10, -5).toString()
        );
    }

    @Test
    void testEquals() {
        assertTrue(new GOLEntity2D(10, -5).equals(new GOLEntity2D(10, -5)));
        assertFalse(new GOLEntity2D(10, -5).equals(new GOLEntity2D(10, 5)));
        assertFalse(new GOLEntity2D(10, -5).equals(new GOLEntity2D(-10, -5)));
    }

    @Test
    void testHashCode() {
        assertEquals(new GOLEntity2D(10, -5).hashCode(), new GOLEntity2D(10, -5).hashCode());
        assertNotEquals(new GOLEntity2D(10, -5).hashCode(), new GOLEntity2D(10, 5).hashCode());
        assertNotEquals(new GOLEntity2D(10, -5).hashCode(), new GOLEntity2D(-10, -5).hashCode());
    }

}
