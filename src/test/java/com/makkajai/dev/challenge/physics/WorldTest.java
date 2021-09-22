package com.makkajai.dev.challenge.physics;

import com.makkajai.dev.challenge.ds.planar.Vec2i;
import com.makkajai.dev.challenge.systems.entitySystem.IEntity;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorldTest {

    @Getter
    class Entity implements IEntity {

        private UUID id;
        private Vec2i position;

        public Entity(Vec2i position) {
            this.id = UUID.randomUUID();
            this.position = position;
        }
    }

    @Test
    void testConstructor() {
        World<Entity> world = new World();

        assertNotNull(world.getTopLeft());
        assertNotNull(world.getBottomRight());
        assertEquals(0, world.getEntities().size());
    }

     @Test
     void testResetBounds() {
         World<Entity> world = new World();

         assertEquals(new Vec2i(Integer.MAX_VALUE, Integer.MAX_VALUE), world.getTopLeft());
         assertEquals(new Vec2i(Integer.MIN_VALUE, Integer.MIN_VALUE), world.getBottomRight());

         world.addEntity(new Entity(new Vec2i()));
         assertEquals(new Vec2i(-1, -1), world.getTopLeft());
         assertEquals(new Vec2i(1, 1), world.getBottomRight());

         world.resetBounds();


         assertEquals(new Vec2i(Integer.MAX_VALUE, Integer.MAX_VALUE), world.getTopLeft());
         assertEquals(new Vec2i(Integer.MIN_VALUE, Integer.MIN_VALUE), world.getBottomRight());

     }

     @Test
     void testExpandBounds() {
         World<Entity> world = new World();
         world.resetBounds();

         world.expandBounds(new Vec2i(1, 2));
         assertEquals(new Vec2i(0, 1), world.getTopLeft());
         assertEquals(new Vec2i(2, 3), world.getBottomRight());

         world.expandBounds(new Vec2i(-3, 0));
         assertEquals(new Vec2i(-4, -1), world.getTopLeft());
         assertEquals(new Vec2i(2, 3), world.getBottomRight());
     }

     @Test
    void testBoundRanges() {
         World<Entity> world = new World();

         assertEquals(2, world.getXBoundRange());
         assertEquals(2, world.getYBoundRange());

         world.addEntity(new Entity(new Vec2i()));
         assertEquals(new Vec2i(-1, -1), world.getTopLeft());
         assertEquals(new Vec2i(1, 1), world.getBottomRight());

         assertEquals(3, world.getXBoundRange());
         assertEquals(3, world.getYBoundRange());

         world.addEntity(new Entity(new Vec2i(-2, 0)));
         assertEquals(new Vec2i(-3, -1), world.getTopLeft());
         assertEquals(new Vec2i(1, 1), world.getBottomRight());

         assertEquals(5, world.getXBoundRange());
         assertEquals(3, world.getYBoundRange());

         world.addEntity(new Entity(new Vec2i()));
         assertEquals(new Vec2i(-3, -1), world.getTopLeft());
         assertEquals(new Vec2i(1, 1), world.getBottomRight());

         assertEquals(5, world.getXBoundRange());
         assertEquals(3, world.getYBoundRange());

         world.addEntity(new Entity(new Vec2i(10, 17)));
         assertEquals(new Vec2i(-3, -1), world.getTopLeft());
         assertEquals(new Vec2i(11, 18), world.getBottomRight());

         assertEquals(15, world.getXBoundRange());
         assertEquals(20, world.getYBoundRange());
    }

    @Test
    void testAddEntity() {
        Entity e1 = new Entity(new Vec2i(2, 7));
        Entity e2 = new Entity(new Vec2i(-2, 5));
        Entity e3 = new Entity(new Vec2i(2, 7));
        World<Entity> world = new World();
        world.addEntity(e1);
        assertEquals(1, world.getEntities().size());

        world.addEntity(e2);
        assertEquals(2, world.getEntities().size());

        world.addEntity(e3);
        assertEquals(3, world.getEntities().size());

        world.addEntity(e1);
        assertEquals(3, world.getEntities().size());
    }

    @Test
    void testRemoveEntity() {
        Entity e1 = new Entity(new Vec2i(2, 7));
        Entity e2 = new Entity(new Vec2i(-2, 5));
        Entity e3 = new Entity(new Vec2i(2, 7));
        World<Entity> world = new World();
        world.addEntity(e1);
        world.addEntity(e2);
        world.addEntity(e3);
        assertEquals(3, world.getEntities().size());

        world.removeEntity(e2);
        assertEquals(2, world.getEntities().size());

        world.removeEntity(e1);
        assertEquals(1, world.getEntities().size());

        world.removeEntity(e2);
        assertEquals(1, world.getEntities().size());
    }

    @Test
    void testGetEntities() {
        Entity e1 = new Entity(new Vec2i(2, 7));
        Entity e2 = new Entity(new Vec2i(-2, 5));
        Entity e3 = new Entity(new Vec2i(2, 7));
        Entity e4 = new Entity(new Vec2i(-14, 12));
        World<Entity> world = new World();
        world.addEntity(e1);
        world.addEntity(e2);
        world.addEntity(e3);
        world.addEntity(e4);
        world.addEntity(e3);
        world.addEntity(e2);

        assertEquals(4, world.getEntities().size());
        assertTrue(world.getEntities().contains(e1));
        assertTrue(world.getEntities().contains(e2));
        assertTrue(world.getEntities().contains(e3));
        assertTrue(world.getEntities().contains(e4));
    }
}
