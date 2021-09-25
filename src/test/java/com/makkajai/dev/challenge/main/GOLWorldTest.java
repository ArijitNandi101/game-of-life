package com.makkajai.dev.challenge.main;

import com.makkajai.dev.challenge.ds.planar.Vec2i;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GOLWorldTest {

    @Test
    void testConstructor() {
        GOLWorld golWorld = new GOLWorld();

        assertEquals(0, golWorld.getEntities().size());
    }

    @Test
    void testAddEntity() {
        GOLEntity e1 = new GOLEntity(new Vec2i(2, 7));
        GOLEntity e2 = new GOLEntity(new Vec2i(-2, 5));
        GOLEntity e3 = new GOLEntity(new Vec2i(2, 7));
        GOLWorld golWorld = new GOLWorld();
        golWorld.addEntity(e1);
        assertEquals(1, golWorld.getEntities().size());

        golWorld.addEntity(e2);
        assertEquals(2, golWorld.getEntities().size());

        golWorld.addEntity(e3);
        assertEquals(2, golWorld.getEntities().size());

        golWorld.addEntity(e1);
        assertEquals(2, golWorld.getEntities().size());
    }

    @Test
    void testRemoveEntity() {
        GOLEntity e1 = new GOLEntity(new Vec2i(2, 7));
        GOLEntity e2 = new GOLEntity(new Vec2i(-2, 5));
        GOLEntity e3 = new GOLEntity(new Vec2i(2, 7));
        GOLWorld golWorld = new GOLWorld();
        golWorld.addEntity(e1);
        golWorld.addEntity(e2);
        golWorld.addEntity(e3);
        assertEquals(2, golWorld.getEntities().size());

        golWorld.removeEntity(e2);
        assertEquals(1, golWorld.getEntities().size());

        golWorld.removeEntity(e1);
        assertEquals(0, golWorld.getEntities().size());

        golWorld.removeEntity(e2);
        assertEquals(0, golWorld.getEntities().size());
    }

    @Test
    void testGetEntities() {
        GOLEntity e1 = new GOLEntity(new Vec2i(2, 7));
        GOLEntity e2 = new GOLEntity(new Vec2i(-2, 5));
        GOLEntity e3 = new GOLEntity(new Vec2i(2, 7));
        GOLEntity e4 = new GOLEntity(new Vec2i(-14, 12));
        GOLWorld golWorld = new GOLWorld();
        golWorld.addEntity(e1);
        golWorld.addEntity(e2);
        golWorld.addEntity(e3);
        golWorld.addEntity(e4);
        golWorld.addEntity(e3);
        golWorld.addEntity(e2);

        assertEquals(3, golWorld.getEntities().size());
        assertTrue(golWorld.getEntities().contains(e1));
        assertTrue(golWorld.getEntities().contains(e2));
        assertTrue(golWorld.getEntities().contains(e4));
    }

    @Test
    void testEntityAddNighbour() {
        GOLEntity e1 = new GOLEntity(new Vec2i(2, 7));
        GOLEntity e2 = new GOLEntity(new Vec2i(1, 6));
        GOLEntity e3 = new GOLEntity(new Vec2i(2, 6));
        GOLEntity e4 = new GOLEntity(new Vec2i(-2, 12));
        GOLWorld golWorld = new GOLWorld();
        golWorld.addEntity(e1);
        golWorld.addEntity(e2);
        golWorld.addEntity(e3);
        golWorld.addEntity(e4);
        golWorld.addEntity(e3);
        golWorld.addEntity(e2);

        assertEquals(2, e1.getNeighbours().size());
        assertTrue(e1.getNeighbours().contains(e2));
        assertTrue(e1.getNeighbours().contains(e3));
        assertFalse(e1.getNeighbours().contains(e4));
        assertFalse(e1.getNeighbours().contains(e1));

        assertEquals(2, e2.getNeighbours().size());
        assertTrue(e2.getNeighbours().contains(e1));
        assertTrue(e2.getNeighbours().contains(e3));
        assertFalse(e2.getNeighbours().contains(e4));
        assertFalse(e2.getNeighbours().contains(e2));

        assertEquals(2, e3.getNeighbours().size());
        assertTrue(e3.getNeighbours().contains(e1));
        assertTrue(e3.getNeighbours().contains(e2));
        assertFalse(e3.getNeighbours().contains(e4));
        assertFalse(e3.getNeighbours().contains(e3));

        assertEquals(0, e4.getNeighbours().size());
    }
}
