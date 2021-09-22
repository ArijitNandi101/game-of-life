package com.makkajai.dev.challenge.main.descriptors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class GOLDescriptor {
    public static final int CURRENT_ALIVE_STATE = 8;

    public static final int FUTURE_ALIVE_STATE = 4;

    protected int value;

    public abstract void update();

    public boolean isAlive() {
        return (this.value & GOLDescriptor.CURRENT_ALIVE_STATE) > 0;
    }

    public boolean willLive() {
        return (this.value & GOLDescriptor.FUTURE_ALIVE_STATE) > 0;
    }
}
