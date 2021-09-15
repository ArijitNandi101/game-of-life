package com.makkajai.dev.challenge.ds;

public class Vec2i {
    public int x;
    public int y;

    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("%d, %d", x, y);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;

        Vec2i rhs = (Vec2i) obj;
        return this.x == rhs.x && this.y == rhs.y;
    }
}
