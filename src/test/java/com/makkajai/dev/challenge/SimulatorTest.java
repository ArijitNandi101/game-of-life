package com.makkajai.dev.challenge;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.makkajai.dev.challenge.ds.planar.Vec2i;

import org.junit.jupiter.api.Test;

class SimulatorTest {

    // @Test
    // void testResetBounds() {
    //     Simulator simulator = new Simulator();

    //     assertEquals(new Vec2i(0, 0), simulator.getTopLeft());
    //     assertEquals(new Vec2i(0, 0), simulator.getBottomRight());

    //     simulator.resetBounds();


    //     assertEquals(new Vec2i(Integer.MAX_VALUE, Integer.MAX_VALUE), simulator.getTopLeft());
    //     assertEquals(new Vec2i(Integer.MIN_VALUE, Integer.MIN_VALUE), simulator.getBottomRight());

    // }
    
    // @Test
    // void testExpandBounds() {
    //     Simulator simulator = new Simulator();
    //     simulator.resetBounds();

    //     simulator.expandBounds(new Vec2i(1, 2));
    //     assertEquals(new Vec2i(0, 1), simulator.getTopLeft());
    //     assertEquals(new Vec2i(2, 3), simulator.getBottomRight());

    //     simulator.expandBounds(new Vec2i(-3, 0));
    //     assertEquals(new Vec2i(-4, -1), simulator.getTopLeft());
    //     assertEquals(new Vec2i(2, 3), simulator.getBottomRight());
    // }

    // @Test
    // void testUpdateSelfDescriptor() {
    //     Simulator simulator = new Simulator();

    //     assertEquals(1, simulator.updateSelfDescriptor(0));
    //     assertEquals(6, simulator.updateSelfDescriptor(1));
    //     assertEquals(7, simulator.updateSelfDescriptor(2));
    //     assertEquals(0xB, simulator.updateSelfDescriptor(3));
    //     assertEquals(9, simulator.updateSelfDescriptor(8));
    //     assertEquals(0xE, simulator.updateSelfDescriptor(9));
    //     assertEquals(0xF, simulator.updateSelfDescriptor(0xA));
    //     assertEquals(0xB, simulator.updateSelfDescriptor(0xB));
    //     assertEquals(0xB, simulator.updateSelfDescriptor(0xF));
    // }

    // @Test
    // void testUpdateNeighbourDescriptor() {
    //     Simulator simulator = new Simulator();

    //     assertEquals(1, simulator.updateNeighbourDescriptor(0));
    //     assertEquals(2, simulator.updateNeighbourDescriptor(1));
    //     assertEquals(7, simulator.updateNeighbourDescriptor(2));
    //     assertEquals(3, simulator.updateNeighbourDescriptor(3));
    //     assertEquals(9, simulator.updateNeighbourDescriptor(8));
    //     assertEquals(0xA, simulator.updateNeighbourDescriptor(9));
    //     assertEquals(7, simulator.updateNeighbourDescriptor(0xA));
    //     assertEquals(3, simulator.updateNeighbourDescriptor(0xB));
    //     assertEquals(3, simulator.updateNeighbourDescriptor(0xF));
    // }

    // @Test
    // void testTick() {
    //     Simulator simulator = new Simulator();

    //     // block pattern
    //     List<Vec2i> aliveEntities = simulator.tick(List.of(
    //         new Vec2i(1, 1),
    //         new Vec2i(1, 2),
    //         new Vec2i(2, 1),
    //         new Vec2i(2, 2)
    //     ));

    //     assertEquals(4, aliveEntities.size());

    //      assertTrue(aliveEntities.contains(new Vec2i(1, 1)));
    //      assertTrue(aliveEntities.contains(new Vec2i(1, 2)));
    //      assertTrue(aliveEntities.contains(new Vec2i(2, 1)));
    //      assertTrue(aliveEntities.contains(new Vec2i(2, 2)));

    //      // boat pattern
    //      aliveEntities = simulator.tick(List.of(
    //          new Vec2i(0, 1), 
    //          new Vec2i(1, 0), 
    //          new Vec2i(2, 1), 
    //          new Vec2i(0, 2),
    //          new Vec2i(1, 2)
    //     ));

    //      assertEquals(5, aliveEntities.size());

    //      assertTrue(aliveEntities.contains(new Vec2i(0, 1)));
    //      assertTrue(aliveEntities.contains(new Vec2i(1, 0)));
    //      assertTrue(aliveEntities.contains(new Vec2i(2, 1)));
    //      assertTrue(aliveEntities.contains(new Vec2i(0, 2)));
    //      assertTrue(aliveEntities.contains(new Vec2i(1, 2)));

    //      // blinker pattern
    //      aliveEntities = simulator.tick(List.of(
    //         new Vec2i(1, 1), 
    //         new Vec2i(1, 0), 
    //         new Vec2i(1, 2)
    //     ));

    //     assertEquals(3, aliveEntities.size());

    //     assertTrue(aliveEntities.contains(new Vec2i(1, 1)));
    //     assertTrue(aliveEntities.contains(new Vec2i(0, 1)));
    //     assertTrue(aliveEntities.contains(new Vec2i(2, 1)));

    //     // toad pattern
    //     aliveEntities = simulator.tick(List.of(
    //         new Vec2i(1, 1), 
    //         new Vec2i(1, 2), 
    //         new Vec2i(1, 3), 
    //         new Vec2i(2, 2),
    //         new Vec2i(2, 3),
    //         new Vec2i(2, 4)
    //     ));

    //     assertEquals(6, aliveEntities.size());

    //     assertTrue(aliveEntities.contains(new Vec2i(0, 2)));
    //     assertTrue(aliveEntities.contains(new Vec2i(1, 1)));
    //     assertTrue(aliveEntities.contains(new Vec2i(1, 4)));
    //     assertTrue(aliveEntities.contains(new Vec2i(2, 1)));
    //     assertTrue(aliveEntities.contains(new Vec2i(2, 4)));
    //     assertTrue(aliveEntities.contains(new Vec2i(3, 3)));
    // }

    // @Test
    // void testPrettyPrint() {
    //     Simulator simulator = new Simulator();

    //     assertDoesNotThrow(() -> {
    //         simulator.prettyPrint();
    //     });

    //     simulator.tick(List.of(new Vec2i(1, 1), new Vec2i(1, 0)));

    //     assertDoesNotThrow(() -> {
    //         simulator.prettyPrint();
    //     });
    // }
}
