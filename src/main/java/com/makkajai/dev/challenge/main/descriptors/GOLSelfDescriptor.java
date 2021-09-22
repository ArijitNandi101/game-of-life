package com.makkajai.dev.challenge.main.descriptors;

public class GOLSelfDescriptor extends GOLDescriptor {


    public GOLSelfDescriptor(int value) {
        super(value);
    }

    /**
     * This method is used to update the 4 bit entity state valuepritor of a 
     * currently alive entity based on the rules of Game of live.
     * 
     * The highest Bit is 1 for currently alive entities.
     * 
     * The 2 lowest bits keep track of how many alive entities it is surrounded by.
     * 
     * The 3rd lowest bit represents if this entity will be alive after update.
     * It is initially 0, representing dead. It will be switched to 1 if it is going to live.
     * 
     * @param self_valueriptor the current entity state valueriptor to be updated.
     * @return the updated entity valueriptor after evaluation with the Game of Life rules.
     */
    @Override
    public void update() {
        // if the entity had alrady seen three alive neighbours in the past 
        // then including the current alive neighbour this entity has 
        // more than 3 neighbours so it will die.
        if ((this.value & 3) == 3) {
            this.value = 0xB;
        } else {
            // if entity has currently seen more than 1 alive nighbours then it lives
            // and increments its alive neighbour count.
            if ((this.value & 3) != 0) {
                this.value |= 4;
            }
            this.value += 1;
        }
    }
}
