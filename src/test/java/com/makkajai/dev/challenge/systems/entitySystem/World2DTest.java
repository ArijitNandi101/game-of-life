package com.makkajai.dev.challenge.systems.entitySystem;

import com.makkajai.dev.challenge.ds.planar.Vec2i;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class World2DTest {

    @Getter
    class Entity2D implements IEntity2D {

        private UUID id;
        private Vec2i position;

        public Entity2D(Vec2i position) {
            this.id = UUID.randomUUID();
            this.position = position;
        }
    }

    @Test
    void testConstructor() {
        World2D<Entity2D> world2D = new World2D();

        assertNotNull(world2D.getTopLeft());
        assertNotNull(world2D.getBottomRight());
        assertEquals(0, world2D.getEntities().size());
    }

     @Test
     void testResetBounds() {
         World2D<Entity2D> world2D = new World2D();

         assertEquals(new Vec2i(Integer.MAX_VALUE, Integer.MAX_VALUE), world2D.getTopLeft());
         assertEquals(new Vec2i(Integer.MIN_VALUE, Integer.MIN_VALUE), world2D.getBottomRight());

         world2D.addEntity(new Entity2D(new Vec2i()));
         assertEquals(new Vec2i(-1, -1), world2D.getTopLeft());
         assertEquals(new Vec2i(1, 1), world2D.getBottomRight());

         world2D.resetBounds();


         assertEquals(new Vec2i(Integer.MAX_VALUE, Integer.MAX_VALUE), world2D.getTopLeft());
         assertEquals(new Vec2i(Integer.MIN_VALUE, Integer.MIN_VALUE), world2D.getBottomRight());

     }

     @Test
     void testExpandBounds() {
         World2D<Entity2D> world2D = new World2D();
         world2D.resetBounds();

         world2D.expandBounds(new Vec2i(1, 2));
         assertEquals(new Vec2i(0, 1), world2D.getTopLeft());
         assertEquals(new Vec2i(2, 3), world2D.getBottomRight());

         world2D.expandBounds(new Vec2i(-3, 0));
         assertEquals(new Vec2i(-4, -1), world2D.getTopLeft());
         assertEquals(new Vec2i(2, 3), world2D.getBottomRight());
     }

     @Test
    void testBoundRanges() {
         World2D<Entity2D> world2D = new World2D();

         assertEquals(2, world2D.getXBoundRange());
         assertEquals(2, world2D.getYBoundRange());

         world2D.addEntity(new Entity2D(new Vec2i()));
         assertEquals(new Vec2i(-1, -1), world2D.getTopLeft());
         assertEquals(new Vec2i(1, 1), world2D.getBottomRight());

         assertEquals(3, world2D.getXBoundRange());
         assertEquals(3, world2D.getYBoundRange());

         world2D.addEntity(new Entity2D(new Vec2i(-2, 0)));
         assertEquals(new Vec2i(-3, -1), world2D.getTopLeft());
         assertEquals(new Vec2i(1, 1), world2D.getBottomRight());

         assertEquals(5, world2D.getXBoundRange());
         assertEquals(3, world2D.getYBoundRange());

         world2D.addEntity(new Entity2D(new Vec2i()));
         assertEquals(new Vec2i(-3, -1), world2D.getTopLeft());
         assertEquals(new Vec2i(1, 1), world2D.getBottomRight());

         assertEquals(5, world2D.getXBoundRange());
         assertEquals(3, world2D.getYBoundRange());

         world2D.addEntity(new Entity2D(new Vec2i(10, 17)));
         assertEquals(new Vec2i(-3, -1), world2D.getTopLeft());
         assertEquals(new Vec2i(11, 18), world2D.getBottomRight());

         assertEquals(15, world2D.getXBoundRange());
         assertEquals(20, world2D.getYBoundRange());
    }

    @Test
    void testAddEntity() {
        Entity2D e1 = new Entity2D(new Vec2i(2, 7));
        Entity2D e2 = new Entity2D(new Vec2i(-2, 5));
        Entity2D e3 = new Entity2D(new Vec2i(2, 7));
        World2D<Entity2D> world2D = new World2D();
        world2D.addEntity(e1);
        assertEquals(1, world2D.getEntities().size());

        world2D.addEntity(e2);
        assertEquals(2, world2D.getEntities().size());

        world2D.addEntity(e3);
        assertEquals(3, world2D.getEntities().size());

        world2D.addEntity(e1);
        assertEquals(3, world2D.getEntities().size());
    }

    @Test
    void testRemoveEntity() {
        Entity2D e1 = new Entity2D(new Vec2i(2, 7));
        Entity2D e2 = new Entity2D(new Vec2i(-2, 5));
        Entity2D e3 = new Entity2D(new Vec2i(2, 7));
        World2D<Entity2D> world2D = new World2D();
        world2D.addEntity(e1);
        world2D.addEntity(e2);
        world2D.addEntity(e3);
        assertEquals(3, world2D.getEntities().size());

        world2D.removeEntity(e2);
        assertEquals(2, world2D.getEntities().size());

        world2D.removeEntity(e1);
        assertEquals(1, world2D.getEntities().size());

        world2D.removeEntity(e2);
        assertEquals(1, world2D.getEntities().size());
    }

    @Test
    void testGetEntities() {
        Entity2D e1 = new Entity2D(new Vec2i(2, 7));
        Entity2D e2 = new Entity2D(new Vec2i(-2, 5));
        Entity2D e3 = new Entity2D(new Vec2i(2, 7));
        Entity2D e4 = new Entity2D(new Vec2i(-14, 12));
        World2D<Entity2D> world2D = new World2D();
        world2D.addEntity(e1);
        world2D.addEntity(e2);
        world2D.addEntity(e3);
        world2D.addEntity(e4);
        world2D.addEntity(e3);
        world2D.addEntity(e2);

        assertEquals(4, world2D.getEntities().size());
        assertTrue(world2D.getEntities().contains(e1));
        assertTrue(world2D.getEntities().contains(e2));
        assertTrue(world2D.getEntities().contains(e3));
        assertTrue(world2D.getEntities().contains(e4));
    }
}
