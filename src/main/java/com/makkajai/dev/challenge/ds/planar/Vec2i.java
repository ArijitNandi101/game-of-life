package com.makkajai.dev.challenge.ds.planar;

import com.makkajai.dev.challenge.physics.ITransformable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * This class represents a location in the cartesian plane using integral (x, y) values.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Vec2i implements ITransformable<Vec2i> {
    
    public int x;
    public int y;

    /**
     * Adds another {@link Vec2i} to this one and returns a new one.
     * 
     * @param rhs the {@link Vec2i} to be added to this one
     * @return new {@link Vec2i} containing the sum
     */
    @Override
    public Vec2i translate(Vec2i rhs) {
        return new Vec2i(this.x + rhs.x, this.y + rhs.y);
    }

    public Vec2i negate() {
        return new Vec2i(-this.x, -this.y);
    }
    
    @Override
    public String toString() {
        return String.format("{x: %d, y: %d}", x, y);
    }
}
