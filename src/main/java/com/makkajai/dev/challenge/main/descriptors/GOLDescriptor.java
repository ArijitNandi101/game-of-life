package com.makkajai.dev.challenge.main.descriptors;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This class represents a descriptor for the state of the entities in the
 * Game of Life. A descriptor consists of 4 bits to store state.
 */
@AllArgsConstructor
@Getter
public abstract class GOLDescriptor {

    /**
     * A currently alive game of life entity will have a descriptor with
     * 1 as its most significant bit (i.e 8 in hexadecimal).
     */
    public static final int CURRENT_ALIVE_STATE = 0x8;

    /**
     * Any entity that lives after a moment in the game opf life pass (a tick occurs)
     * will have a descriptor with its second most significant bit set as 1
     * (i.e 4 in hexadecimal).
     */
    public static final int FUTURE_ALIVE_STATE = 0x4;

    /**
     * the descriptor value describing the state of the game of life entity.
     * Only the first 4 bits are valid data.
     */
    protected int value;

    /**
     * updates the descriptor based on the current state of the entity and the
     * states of all its neighbours.
     */
    public abstract void update();

    /**
     * checks to see if the descriptor's most significant bit is set
     * (currently alive or not).
     *
     * @return true if the most significant descriptor bit (4th) is set,
     *          otherwise false
     */
    public boolean isAlive() {
        return (this.value & GOLDescriptor.CURRENT_ALIVE_STATE) > 0;
    }

    /**
     * checks to see if the descriptor's second most significant bit is set
     * (will be alive after a moment or not).
     *
     * @return true if the second most significant descriptor bit (3rd) is set,
     *          otherwise false
     */
    public boolean willLive() {
        return (this.value & GOLDescriptor.FUTURE_ALIVE_STATE) > 0;
    }
}
