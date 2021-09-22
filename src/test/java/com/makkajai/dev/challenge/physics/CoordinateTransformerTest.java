package com.makkajai.dev.challenge.physics;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoordinateTransformerTest {

    @AllArgsConstructor
    @EqualsAndHashCode
    class Vec1i implements ITransformable<Vec1i> {

        int x = 0;

        @Override
        public Vec1i translate(Vec1i rhs) {
            return new Vec1i(this.x + rhs.x);
        }
    }

    @Test
    void testConstructor() {
        CoordinateTransformer<Vec1i> transformer = new CoordinateTransformer(new Vec1i(3));

        assertEquals(new Vec1i(3), transformer.getOffset());
    }

    @Test
    void testTransform() {
        CoordinateTransformer<Vec1i> transformer = new CoordinateTransformer(new Vec1i(3));

        assertEquals(new Vec1i(8), transformer.transform(new Vec1i(5)));
        assertEquals(new Vec1i(0), transformer.transform(new Vec1i(-3)));
        assertEquals(new Vec1i(-1097), transformer.transform(new Vec1i(-1100)));

        transformer = new CoordinateTransformer(new Vec1i(-3));
        assertEquals(new Vec1i(-8), transformer.transform(new Vec1i(-5)));
        assertEquals(new Vec1i(0), transformer.transform(new Vec1i(3)));
        assertEquals(new Vec1i(1097), transformer.transform(new Vec1i(1100)));
    }
}
