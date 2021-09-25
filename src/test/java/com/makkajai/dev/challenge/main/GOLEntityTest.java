package com.makkajai.dev.challenge.main;

import java.util.UUID;

import com.makkajai.dev.challenge.ds.planar.Vec2i;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GOLEntityTest {

    @Test
    void testConstructor() {
        GOLEntity entity = new GOLEntity(10, -5);
        assertEquals(UUID.nameUUIDFromBytes("{x: 10, y: -5}".getBytes()), entity.getId());
        assertNotNull(entity.getPosition());
        assertEquals(new Vec2i(10, -5), entity.getPosition());
        assertEquals(GOLEntity.State.DEAD, entity.getState());
        assertNotNull(entity.getNeighbours());
        assertEquals(0, entity.getNeighbours().size());
        assertEquals(0, entity.getAliveNeighbourCount());
    }

    @Test
    void testConstructorVec2i() {
        GOLEntity entity = new GOLEntity(new Vec2i(10, -5));
        assertEquals(UUID.nameUUIDFromBytes(new Vec2i(10, -5).toString().getBytes()), entity.getId());
        assertEquals(new Vec2i(10, -5), entity.getPosition());

        assertNotNull(entity.getPosition());
        assertEquals(new Vec2i(10, -5), entity.getPosition());
        assertEquals(GOLEntity.State.DEAD, entity.getState());
        assertNotNull(entity.getNeighbours());
        assertEquals(0, entity.getNeighbours().size());
        assertEquals(0, entity.getAliveNeighbourCount());
    }

    @Test
    void testAddNeighbours() {
        GOLEntity entity = new GOLEntity(new Vec2i(10, -5));
        assertEquals(0, entity.getNeighbours().size());

        entity.addNeighbour(new GOLEntity(new Vec2i(9, -6)));
        entity.addNeighbour(new GOLEntity(new Vec2i(9, -5)));
        entity.addNeighbour(new GOLEntity(new Vec2i(10, -4)));
        entity.addNeighbour(new GOLEntity(new Vec2i(9, -4)));
        entity.addNeighbour(new GOLEntity(new Vec2i(10, -6)));
        entity.addNeighbour(new GOLEntity(new Vec2i(11, -4)));
        entity.addNeighbour(new GOLEntity(new Vec2i(11, -5)));
        entity.addNeighbour(new GOLEntity(new Vec2i(11, -6)));

        assertEquals(8, entity.getNeighbours().size());

        assertTrue(entity.getNeighbours().contains(new GOLEntity(new Vec2i(9, -4))));
        assertTrue(entity.getNeighbours().contains(new GOLEntity(new Vec2i(9, -5))));
        assertTrue(entity.getNeighbours().contains(new GOLEntity(new Vec2i(9, -6))));
        assertTrue(entity.getNeighbours().contains(new GOLEntity(new Vec2i(10, -4))));
        assertTrue(entity.getNeighbours().contains(new GOLEntity(new Vec2i(10, -6))));
        assertTrue(entity.getNeighbours().contains(new GOLEntity(new Vec2i(11, -4))));
        assertTrue(entity.getNeighbours().contains(new GOLEntity(new Vec2i(11, -5))));
        assertTrue(entity.getNeighbours().contains(new GOLEntity(new Vec2i(11, -6))));
    }

    @Test
    void removeAddNeighbours() {
        GOLEntity entity = new GOLEntity(new Vec2i(10, -5));
        assertEquals(0, entity.getNeighbours().size());

        entity.addNeighbour(new GOLEntity(new Vec2i(9, -6)));
        entity.addNeighbour(new GOLEntity(new Vec2i(9, -5)));
        entity.addNeighbour(new GOLEntity(new Vec2i(10, -4)));
        entity.addNeighbour(new GOLEntity(new Vec2i(9, -4)));
        entity.addNeighbour(new GOLEntity(new Vec2i(10, -6)));
        entity.addNeighbour(new GOLEntity(new Vec2i(11, -4)));
        entity.addNeighbour(new GOLEntity(new Vec2i(11, -5)));
        entity.addNeighbour(new GOLEntity(new Vec2i(11, -6)));

        assertEquals(8, entity.getNeighbours().size());

        assertTrue(entity.getNeighbours().contains(new GOLEntity(new Vec2i(9, -4))));
        assertTrue(entity.getNeighbours().contains(new GOLEntity(new Vec2i(9, -5))));
        assertTrue(entity.getNeighbours().contains(new GOLEntity(new Vec2i(9, -6))));
        assertTrue(entity.getNeighbours().contains(new GOLEntity(new Vec2i(10, -4))));
        assertTrue(entity.getNeighbours().contains(new GOLEntity(new Vec2i(10, -6))));
        assertTrue(entity.getNeighbours().contains(new GOLEntity(new Vec2i(11, -4))));
        assertTrue(entity.getNeighbours().contains(new GOLEntity(new Vec2i(11, -5))));
        assertTrue(entity.getNeighbours().contains(new GOLEntity(new Vec2i(11, -6))));

        entity.removeNeighbour(new GOLEntity(new Vec2i(9, -5)));
        assertFalse(entity.getNeighbours().contains(new GOLEntity(new Vec2i(9, -5))));

        assertEquals(7, entity.getNeighbours().size());

        entity.removeNeighbour(new GOLEntity(new Vec2i(10, -4)));
        assertFalse(entity.getNeighbours().contains(new GOLEntity(new Vec2i(10, -4))));

        assertEquals(6, entity.getNeighbours().size());


        entity.removeNeighbour(new GOLEntity(new Vec2i(9, -4)));
        assertFalse(entity.getNeighbours().contains(new GOLEntity(new Vec2i(9, -4))));

        assertEquals(5, entity.getNeighbours().size());

        entity.removeNeighbour(new GOLEntity(new Vec2i(10, -6)));
        assertFalse(entity.getNeighbours().contains(new GOLEntity(new Vec2i(10, -6))));

        assertEquals(4, entity.getNeighbours().size());

        entity.removeNeighbour(new GOLEntity(new Vec2i(11, -4)));
        assertFalse(entity.getNeighbours().contains(new GOLEntity(new Vec2i(11, -4))));

        assertEquals(3, entity.getNeighbours().size());

        entity.removeNeighbour(new GOLEntity(new Vec2i(11, -5)));
        assertFalse(entity.getNeighbours().contains(new GOLEntity(new Vec2i(11, -5))));

        assertEquals(2, entity.getNeighbours().size());

        entity.removeNeighbour(new GOLEntity(new Vec2i(11, -6)));
        assertFalse(entity.getNeighbours().contains(new GOLEntity(new Vec2i(11, -6))));

        assertEquals(1, entity.getNeighbours().size());

        entity.removeNeighbour(new GOLEntity(new Vec2i(9, -6)));
        assertFalse(entity.getNeighbours().contains(new GOLEntity(new Vec2i(9, -6))));

        assertEquals(0, entity.getNeighbours().size());
    }

    @Test
    void testIsAliveOrDead() {
        GOLEntity entity = new GOLEntity(new Vec2i(10, -5));
        entity.setState(GOLEntity.State.ALIVE);
        assertTrue(entity.isAlive());
        assertFalse(entity.isDead());

        entity.setState(GOLEntity.State.DEAD);
        assertTrue(entity.isDead());
        assertFalse(entity.isAlive());
    }

    @Test
    void testIsNeighbourOf() {
        GOLEntity entity = new GOLEntity(new Vec2i(10, -5));

        assertTrue(entity.isNeighbourOf(new GOLEntity(new Vec2i(9, -6))));
        assertTrue(entity.isNeighbourOf(new GOLEntity(new Vec2i(9, -5))));
        assertTrue(entity.isNeighbourOf(new GOLEntity(new Vec2i(10, -4))));
        assertTrue(entity.isNeighbourOf(new GOLEntity(new Vec2i(9, -4))));
        assertTrue(entity.isNeighbourOf(new GOLEntity(new Vec2i(10, -6))));
        assertTrue(entity.isNeighbourOf(new GOLEntity(new Vec2i(11, -4))));
        assertTrue(entity.isNeighbourOf(new GOLEntity(new Vec2i(11, -5))));
        assertTrue(entity.isNeighbourOf(new GOLEntity(new Vec2i(11, -6))));

        assertFalse(entity.isNeighbourOf(new GOLEntity(new Vec2i(9, -7))));
        assertFalse(entity.isNeighbourOf(new GOLEntity(new Vec2i(8, -5))));
        assertFalse(entity.isNeighbourOf(new GOLEntity(new Vec2i(10, -3))));
        assertFalse(entity.isNeighbourOf(new GOLEntity(new Vec2i(8, -4))));
        assertFalse(entity.isNeighbourOf(new GOLEntity(new Vec2i(10, -7))));
        assertFalse(entity.isNeighbourOf(new GOLEntity(new Vec2i(12, -4))));
        assertFalse(entity.isNeighbourOf(new GOLEntity(new Vec2i(12, -5))));
        assertFalse(entity.isNeighbourOf(new GOLEntity(new Vec2i(12, -6))));
    }

    @Test
    void testToString() {
        assertEquals(
            String.format(
                    "{id: %s, position: %s, state: %s}",
                    UUID.nameUUIDFromBytes(new Vec2i(10, -5).toString().getBytes()),
                    new Vec2i(10, -5),
                    GOLEntity.State.DEAD
            ),
            new GOLEntity(10, -5).toString()
        );
    }

    @Test
    void testEquals() {
        assertTrue(new GOLEntity(10, -5).equals(new GOLEntity(10, -5)));
        assertFalse(new GOLEntity(10, -5).equals(new GOLEntity(10, 5)));
        assertFalse(new GOLEntity(10, -5).equals(new GOLEntity(-10, -5)));
    }

    @Test
    void testHashCode() {
        assertEquals(new GOLEntity(10, -5).hashCode(), new GOLEntity(10, -5).hashCode());
        assertNotEquals(new GOLEntity(10, -5).hashCode(), new GOLEntity(10, 5).hashCode());
        assertNotEquals(new GOLEntity(10, -5).hashCode(), new GOLEntity(-10, -5).hashCode());
    }

}
