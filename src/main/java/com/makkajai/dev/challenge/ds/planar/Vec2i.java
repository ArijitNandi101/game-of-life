package com.makkajai.dev.challenge.ds.planar;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * This class represents a location in the cartesian plane using integral (x, y) values.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Vec2i {
    
    public int x;
    public int y;

    /**
     * Adds another {@link Vec2i} to this one and returns a new one.
     * 
     * @param rhs the {@link Vec2i} to be added to this one
     * @return new {@link Vec2i} containing the sum
     */
    public Vec2i add(Vec2i rhs) {
        return new Vec2i(this.x + rhs.x, this.y + rhs.y);
    }

    /**
     * Subtracts another {@link Vec2i} to this one and returns a new one.
     * 
     * @param rhs the {@link Vec2i} to be subtracted from this one.
     * @return new {@link Vec2i} containing the different of the rhs Vector from this one.
     */
    public Vec2i subtract(Vec2i rhs) {
        return new Vec2i(this.x - rhs.x, this.y - rhs.y);
    }
    
    @Override
    public String toString() {
        return String.format("%d, %d", x, y);
    }
}
