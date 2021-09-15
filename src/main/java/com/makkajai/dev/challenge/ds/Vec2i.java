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
        // TODO Auto-generated method stub
        return String.format("(%d, %d)", x, y);
    }
}
