//package com.makkajai.dev.challenge.systems.entitySystem;
//
//import com.makkajai.dev.challenge.ds.planar.Vec2i;
//import com.makkajai.dev.challenge.main.GOLWorld;
//import lombok.Getter;
//import org.junit.jupiter.api.Test;
//
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class GOLWorldTest {
//
//    @Getter
//    class Entity2D implements IEntity2D {
//
//        private UUID id;
//        private Vec2i position;
//
//        public Entity2D(Vec2i position) {
//            this.id = UUID.randomUUID();
//            this.position = position;
//        }
//    }
//
//    @Test
//    void testConstructor() {
//        GOLWorld<Entity2D> GOLWorld = new GOLWorld();
//
//        assertNotNull(GOLWorld.getTopLeft());
//        assertNotNull(GOLWorld.getBottomRight());
//        assertEquals(0, GOLWorld.getEntities().size());
//    }
//
//     @Test
//     void testResetBounds() {
//         GOLWorld<Entity2D> GOLWorld = new GOLWorld();
//
//         assertEquals(new Vec2i(Integer.MAX_VALUE, Integer.MAX_VALUE), GOLWorld.getTopLeft());
//         assertEquals(new Vec2i(Integer.MIN_VALUE, Integer.MIN_VALUE), GOLWorld.getBottomRight());
//
//         GOLWorld.addEntity(new Entity2D(new Vec2i()));
//         assertEquals(new Vec2i(-1, -1), GOLWorld.getTopLeft());
//         assertEquals(new Vec2i(1, 1), GOLWorld.getBottomRight());
//
//         GOLWorld.resetBounds();
//
//
//         assertEquals(new Vec2i(Integer.MAX_VALUE, Integer.MAX_VALUE), GOLWorld.getTopLeft());
//         assertEquals(new Vec2i(Integer.MIN_VALUE, Integer.MIN_VALUE), GOLWorld.getBottomRight());
//
//     }
//
//     @Test
//     void testExpandBounds() {
//         GOLWorld<Entity2D> GOLWorld = new GOLWorld();
//         GOLWorld.resetBounds();
//
//         GOLWorld.expandBounds(new Vec2i(1, 2));
//         assertEquals(new Vec2i(0, 1), GOLWorld.getTopLeft());
//         assertEquals(new Vec2i(2, 3), GOLWorld.getBottomRight());
//
//         GOLWorld.expandBounds(new Vec2i(-3, 0));
//         assertEquals(new Vec2i(-4, -1), GOLWorld.getTopLeft());
//         assertEquals(new Vec2i(2, 3), GOLWorld.getBottomRight());
//     }
//
//     @Test
//    void testBoundRanges() {
//         GOLWorld<Entity2D> GOLWorld = new GOLWorld();
//
//         assertEquals(2, GOLWorld.getXBoundRange());
//         assertEquals(2, GOLWorld.getYBoundRange());
//
//         GOLWorld.addEntity(new Entity2D(new Vec2i()));
//         assertEquals(new Vec2i(-1, -1), GOLWorld.getTopLeft());
//         assertEquals(new Vec2i(1, 1), GOLWorld.getBottomRight());
//
//         assertEquals(3, GOLWorld.getXBoundRange());
//         assertEquals(3, GOLWorld.getYBoundRange());
//
//         GOLWorld.addEntity(new Entity2D(new Vec2i(-2, 0)));
//         assertEquals(new Vec2i(-3, -1), GOLWorld.getTopLeft());
//         assertEquals(new Vec2i(1, 1), GOLWorld.getBottomRight());
//
//         assertEquals(5, GOLWorld.getXBoundRange());
//         assertEquals(3, GOLWorld.getYBoundRange());
//
//         GOLWorld.addEntity(new Entity2D(new Vec2i()));
//         assertEquals(new Vec2i(-3, -1), GOLWorld.getTopLeft());
//         assertEquals(new Vec2i(1, 1), GOLWorld.getBottomRight());
//
//         assertEquals(5, GOLWorld.getXBoundRange());
//         assertEquals(3, GOLWorld.getYBoundRange());
//
//         GOLWorld.addEntity(new Entity2D(new Vec2i(10, 17)));
//         assertEquals(new Vec2i(-3, -1), GOLWorld.getTopLeft());
//         assertEquals(new Vec2i(11, 18), GOLWorld.getBottomRight());
//
//         assertEquals(15, GOLWorld.getXBoundRange());
//         assertEquals(20, GOLWorld.getYBoundRange());
//    }
//
//    @Test
//    void testAddEntity() {
//        Entity2D e1 = new Entity2D(new Vec2i(2, 7));
//        Entity2D e2 = new Entity2D(new Vec2i(-2, 5));
//        Entity2D e3 = new Entity2D(new Vec2i(2, 7));
//        GOLWorld<Entity2D> GOLWorld = new GOLWorld();
//        GOLWorld.addEntity(e1);
//        assertEquals(1, GOLWorld.getEntities().size());
//
//        GOLWorld.addEntity(e2);
//        assertEquals(2, GOLWorld.getEntities().size());
//
//        GOLWorld.addEntity(e3);
//        assertEquals(3, GOLWorld.getEntities().size());
//
//        GOLWorld.addEntity(e1);
//        assertEquals(3, GOLWorld.getEntities().size());
//    }
//
//    @Test
//    void testRemoveEntity() {
//        Entity2D e1 = new Entity2D(new Vec2i(2, 7));
//        Entity2D e2 = new Entity2D(new Vec2i(-2, 5));
//        Entity2D e3 = new Entity2D(new Vec2i(2, 7));
//        GOLWorld<Entity2D> GOLWorld = new GOLWorld();
//        GOLWorld.addEntity(e1);
//        GOLWorld.addEntity(e2);
//        GOLWorld.addEntity(e3);
//        assertEquals(3, GOLWorld.getEntities().size());
//
//        GOLWorld.removeEntity(e2);
//        assertEquals(2, GOLWorld.getEntities().size());
//
//        GOLWorld.removeEntity(e1);
//        assertEquals(1, GOLWorld.getEntities().size());
//
//        GOLWorld.removeEntity(e2);
//        assertEquals(1, GOLWorld.getEntities().size());
//    }
//
//    @Test
//    void testGetEntities() {
//        Entity2D e1 = new Entity2D(new Vec2i(2, 7));
//        Entity2D e2 = new Entity2D(new Vec2i(-2, 5));
//        Entity2D e3 = new Entity2D(new Vec2i(2, 7));
//        Entity2D e4 = new Entity2D(new Vec2i(-14, 12));
//        GOLWorld<Entity2D> GOLWorld = new GOLWorld();
//        GOLWorld.addEntity(e1);
//        GOLWorld.addEntity(e2);
//        GOLWorld.addEntity(e3);
//        GOLWorld.addEntity(e4);
//        GOLWorld.addEntity(e3);
//        GOLWorld.addEntity(e2);
//
//        assertEquals(4, GOLWorld.getEntities().size());
//        assertTrue(GOLWorld.getEntities().contains(e1));
//        assertTrue(GOLWorld.getEntities().contains(e2));
//        assertTrue(GOLWorld.getEntities().contains(e3));
//        assertTrue(GOLWorld.getEntities().contains(e4));
//    }
//}
