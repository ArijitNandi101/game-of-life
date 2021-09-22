package com.makkajai.dev.challenge.main;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.stream.Collectors;

import com.makkajai.dev.challenge.ds.planar.Vec2i;

import org.junit.jupiter.api.Test;

class SimulatorTest {

    @Test
    void testSeed() {
        Simulator simulator = new Simulator();
        simulator.seed(List.of(
            new Vec2i(1, 1), 
            new Vec2i(1, 0), 
            new Vec2i(1, 2)
        ));

        List<Vec2i> aliveEntities = simulator.getAliveEntityPositionStream().collect(Collectors.toList());
        assertEquals(3, aliveEntities.size());

         assertTrue(aliveEntities.contains(new Vec2i(1, 1)));
         assertTrue(aliveEntities.contains(new Vec2i(1, 0)));
         assertTrue(aliveEntities.contains(new Vec2i(1, 2)));
    }

    @Test
    void testTick() {
        Simulator simulator = new Simulator();

        // block pattern
        simulator.seed(List.of(
            new Vec2i(1, 1),
            new Vec2i(1, 2),
            new Vec2i(2, 1),
            new Vec2i(2, 2)
        ));
        simulator.tick();
        List<Vec2i> aliveEntities = simulator.getAliveEntityPositionStream().collect(Collectors.toList());
        assertEquals(4, aliveEntities.size());

         assertTrue(aliveEntities.contains(new Vec2i(1, 1)));
         assertTrue(aliveEntities.contains(new Vec2i(1, 2)));
         assertTrue(aliveEntities.contains(new Vec2i(2, 1)));
         assertTrue(aliveEntities.contains(new Vec2i(2, 2)));

         // boat pattern
         simulator.seed(List.of(
             new Vec2i(0, 1), 
             new Vec2i(1, 0), 
             new Vec2i(2, 1), 
             new Vec2i(0, 2),
             new Vec2i(1, 2)
        ));
        simulator.tick();
        aliveEntities = simulator.getAliveEntityPositionStream().collect(Collectors.toList());

        assertEquals(5, aliveEntities.size());

        assertTrue(aliveEntities.contains(new Vec2i(0, 1)));
        assertTrue(aliveEntities.contains(new Vec2i(1, 0)));
        assertTrue(aliveEntities.contains(new Vec2i(2, 1)));
        assertTrue(aliveEntities.contains(new Vec2i(0, 2)));
        assertTrue(aliveEntities.contains(new Vec2i(1, 2)));

         // blinker pattern
         simulator.seed(List.of(
            new Vec2i(1, 1), 
            new Vec2i(1, 0), 
            new Vec2i(1, 2)
        ));
        simulator.tick();
        aliveEntities = simulator.getAliveEntityPositionStream().collect(Collectors.toList());

        assertEquals(3, aliveEntities.size());

        assertTrue(aliveEntities.contains(new Vec2i(1, 1)));
        assertTrue(aliveEntities.contains(new Vec2i(0, 1)));
        assertTrue(aliveEntities.contains(new Vec2i(2, 1)));

        // toad pattern
        simulator.seed(List.of(
            new Vec2i(1, 1), 
            new Vec2i(1, 2), 
            new Vec2i(1, 3), 
            new Vec2i(2, 2),
            new Vec2i(2, 3),
            new Vec2i(2, 4)
        ));
        simulator.tick();
        aliveEntities = simulator.getAliveEntityPositionStream().collect(Collectors.toList());

        assertEquals(6, aliveEntities.size());

        assertTrue(aliveEntities.contains(new Vec2i(0, 2)));
        assertTrue(aliveEntities.contains(new Vec2i(1, 1)));
        assertTrue(aliveEntities.contains(new Vec2i(1, 4)));
        assertTrue(aliveEntities.contains(new Vec2i(2, 1)));
        assertTrue(aliveEntities.contains(new Vec2i(2, 4)));
        assertTrue(aliveEntities.contains(new Vec2i(3, 3)));
    }

    @Test
    void testGetAliveEntityPositionStream() {
        Simulator simulator = new Simulator();

        List<Vec2i> aliveEntities = simulator.getAliveEntityPositionStream().collect(Collectors.toList());
        assertNotNull(aliveEntities);
        assertEquals(0, aliveEntities.size());

        simulator.seed(List.of(
            new Vec2i(1, 1), 
            new Vec2i(1, 0), 
            new Vec2i(1, 2)
        ));

        aliveEntities = simulator.getAliveEntityPositionStream().collect(Collectors.toList());
        assertEquals(3, aliveEntities.size());

        simulator.seed(List.of(
            new Vec2i(-1, 1), 
            new Vec2i(-1, 0), 
            new Vec2i(1, -2)
        ));

        aliveEntities = simulator.getAliveEntityPositionStream().collect(Collectors.toList());
        assertEquals(3, aliveEntities.size());

        simulator.seed(List.of(
            new Vec2i(-1, -1), 
            new Vec2i(-1, 0)
        ));

        aliveEntities = simulator.getAliveEntityPositionStream().collect(Collectors.toList());
        assertEquals(2, aliveEntities.size());

        simulator.seed(List.of(
            new Vec2i(1, 1), 
            new Vec2i(1, 0), 
            new Vec2i(1, -2),
            new Vec2i(1, 0)
        ));

        aliveEntities = simulator.getAliveEntityPositionStream().collect(Collectors.toList());
        assertEquals(3, aliveEntities.size());

        simulator.seed(List.of(
            new Vec2i(1, 1), 
            new Vec2i(1, 0), 
            new Vec2i(1, -2),
            new Vec2i(1, 1)
        ));

        aliveEntities = simulator.getAliveEntityPositionStream().collect(Collectors.toList());
        assertEquals(3, aliveEntities.size());

        simulator.seed(List.of(
            new Vec2i(1, 1), 
            new Vec2i(1, 0), 
            new Vec2i(1, -2),
            new Vec2i(1, -5)
        ));

        aliveEntities = simulator.getAliveEntityPositionStream().collect(Collectors.toList());
        assertEquals(4, aliveEntities.size());

    }

    @Test
    void testPrettyPrintGrid() {
        Simulator simulator = new Simulator();

        assertDoesNotThrow(() -> {
            simulator.prettyPrintGrid();
        });

        simulator.seed(List.of(new Vec2i(1, 1), new Vec2i(1, 0)));
        simulator.tick();
        assertDoesNotThrow(() -> {
            simulator.prettyPrintGrid();
        });
    }

    @Test
    void testRender() {
        Simulator simulator = new Simulator();

        assertDoesNotThrow(() -> {
            simulator.render();
        });

        assertDoesNotThrow(() -> {
            simulator.seed(List.of(
                new Vec2i(1, 1), 
                new Vec2i(1, 0), 
                new Vec2i(1, -2),
                new Vec2i(1, -5)
            ));
            simulator.tick();
            simulator.render();
        });
    }
}
