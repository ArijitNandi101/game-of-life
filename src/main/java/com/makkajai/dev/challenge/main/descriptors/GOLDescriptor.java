package com.makkajai.dev.challenge.main.descriptors;

public abstract class GOLDescriptor {
    public static final int CURRENT_ALIVE_STATE = 8;

    public static final int FUTURE_ALIVE_STATE = 4;

    public abstract int getValue();

    public abstract void update();

    public boolean isAlive() {
        return (this.getValue() & GOLDescriptor.CURRENT_ALIVE_STATE) > 0;
    }

    public boolean willLive() {
        return (this.getValue() & GOLDescriptor.FUTURE_ALIVE_STATE) > 0;
    }
}
