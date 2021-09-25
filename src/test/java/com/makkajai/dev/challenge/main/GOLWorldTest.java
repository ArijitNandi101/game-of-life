package com.makkajai.dev.challenge.main;

import com.makkajai.dev.challenge.ds.planar.Vec2i;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GOLWorldTest {

    @Test
    void testConstructor() {
        GOLWorld GOLWorld = new GOLWorld();

        assertEquals(0, GOLWorld.getEntities().size());
    }

    @Test
    void testAddEntity() {
        GOLEntity e1 = new GOLEntity(new Vec2i(2, 7));
        GOLEntity e2 = new GOLEntity(new Vec2i(-2, 5));
        GOLEntity e3 = new GOLEntity(new Vec2i(2, 7));
        GOLWorld GOLWorld = new GOLWorld();
        GOLWorld.addEntity(e1);
        assertEquals(1, GOLWorld.getEntities().size());

        GOLWorld.addEntity(e2);
        assertEquals(2, GOLWorld.getEntities().size());

        GOLWorld.addEntity(e3);
        assertEquals(2, GOLWorld.getEntities().size());

        GOLWorld.addEntity(e1);
        assertEquals(2, GOLWorld.getEntities().size());
    }

    @Test
    void testRemoveEntity() {
        GOLEntity e1 = new GOLEntity(new Vec2i(2, 7));
        GOLEntity e2 = new GOLEntity(new Vec2i(-2, 5));
        GOLEntity e3 = new GOLEntity(new Vec2i(2, 7));
        GOLWorld GOLWorld = new GOLWorld();
        GOLWorld.addEntity(e1);
        GOLWorld.addEntity(e2);
        GOLWorld.addEntity(e3);
        assertEquals(2, GOLWorld.getEntities().size());

        GOLWorld.removeEntity(e2);
        assertEquals(1, GOLWorld.getEntities().size());

        GOLWorld.removeEntity(e1);
        assertEquals(0, GOLWorld.getEntities().size());

        GOLWorld.removeEntity(e2);
        assertEquals(0, GOLWorld.getEntities().size());
    }

    @Test
    void testGetEntities() {
        GOLEntity e1 = new GOLEntity(new Vec2i(2, 7));
        GOLEntity e2 = new GOLEntity(new Vec2i(-2, 5));
        GOLEntity e3 = new GOLEntity(new Vec2i(2, 7));
        GOLEntity e4 = new GOLEntity(new Vec2i(-14, 12));
        GOLWorld GOLWorld = new GOLWorld();
        GOLWorld.addEntity(e1);
        GOLWorld.addEntity(e2);
        GOLWorld.addEntity(e3);
        GOLWorld.addEntity(e4);
        GOLWorld.addEntity(e3);
        GOLWorld.addEntity(e2);

        assertEquals(3, GOLWorld.getEntities().size());
        assertTrue(GOLWorld.getEntities().contains(e1));
        assertTrue(GOLWorld.getEntities().contains(e2));
        assertTrue(GOLWorld.getEntities().contains(e4));
    }
}
