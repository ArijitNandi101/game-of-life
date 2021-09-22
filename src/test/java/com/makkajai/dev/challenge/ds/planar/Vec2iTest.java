package com.makkajai.dev.challenge.ds.planar;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.makkajai.dev.challenge.ds.planar.Vec2i;

class Vec2iTest {

    @Test
    void testConstructor() {
        Vec2i vec = new Vec2i();
        assertNotNull(vec);
        assertEquals(new Vec2i(0, 0), vec);
        assertEquals(0, vec.x);
        assertEquals(0, vec.y);

        vec = new Vec2i(-12, 4);
        assertNotNull(vec);
        assertEquals(new Vec2i(-12, 4), vec);
        assertEquals(-12, vec.x);
        assertEquals(4, vec.y);

        vec = new Vec2i(12, -4);
        assertNotNull(vec);
        assertEquals(new Vec2i(12, -4), vec);
        assertEquals(12, vec.x);
        assertEquals(-4, vec.y);

        vec = new Vec2i(Integer.MIN_VALUE, Integer.MAX_VALUE);
        assertNotNull(vec);
        assertEquals(new Vec2i(Integer.MIN_VALUE, Integer.MAX_VALUE), vec);
        assertEquals(Integer.MIN_VALUE, vec.x);
        assertEquals(Integer.MAX_VALUE, vec.y);
    }

    @Test
    void testTranslate() {
        Vec2i vec1 = new Vec2i(2, 4);
        Vec2i vec2 = new Vec2i(-3, 7);

        assertEquals(new Vec2i(4, 8), vec1.translate(vec1));
        assertEquals(new Vec2i(-6, 14), vec2.translate(vec2));

        assertEquals(new Vec2i(-1, 11), vec1.translate(vec2));
        assertEquals(new Vec2i(-1, 11), vec2.translate(vec1));    
    }

    @Test
    void testNegate() {
        Vec2i vec1 = new Vec2i(2, 4);
        Vec2i vec2 = new Vec2i(-3, 7);

        assertEquals(new Vec2i(-2, -4), vec1.negate());
        assertEquals(new Vec2i(3, -7), vec2.negate());
    }

    @Test
    void testToString() {
        Vec2i vec = new Vec2i(2, -4);
        
        assertEquals("{x: 2, y: -4}", vec.toString());
    }


    @Test
    void testEquals() {
        Vec2i vec = new Vec2i(2, -4);

        assertTrue(vec.equals(new Vec2i(2, -4)));
        assertFalse(vec.equals(new Vec2i(2, 4)));
        assertFalse(vec.equals(new Vec2i(-2, -4)));
    }

    @Test
    void testHashCode() {
        Vec2i vec = new Vec2i(2, -4);

        assertEquals(vec.hashCode(), new Vec2i(2, -4).hashCode());
        assertNotEquals(vec.hashCode(), new Vec2i(2, 4).hashCode());
        assertNotEquals(vec.hashCode(), new Vec2i(-2, -4).hashCode());
    }
    
}
