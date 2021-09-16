package com.makkajai.dev.challenge.ds.planar;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Vec2i {
    
    public int x;
    public int y;

    public Vec2i add(Vec2i rhs) {
        return new Vec2i(this.x + rhs.x, this.y + rhs.y);
    }

    public Vec2i subtract(Vec2i rhs) {
        return new Vec2i(this.x - rhs.x, this.y - rhs.y);
    }

    @Override
    public String toString() {
        return String.format("%d, %d", x, y);
    }
}
