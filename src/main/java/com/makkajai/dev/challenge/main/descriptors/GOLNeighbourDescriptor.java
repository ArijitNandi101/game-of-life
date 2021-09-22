package com.makkajai.dev.challenge.main.descriptors;

/**
 * This descriptor is for entities who are neighbours to the currently alive and known
 * entities in the game of life world.
 */
public class GOLNeighbourDescriptor extends GOLDescriptor {

    public GOLNeighbourDescriptor(int value) {
        super(value);
    }

    /**
     * This method is used to update the 4 bit entity state descriptor of the
     * currently dead entity based on the rules of Game of live.
     *
     * The highest Bit is 0 for currently dead entities.
     *
     * The 2 lowest bits keep track of how many alive entities it is surrounded by.
     *
     * The 3rd lowest bit represents if this entity will be alive after update.
     * It is initially 0, representing dead. It will be switched to 1 if it is
     * going to live.
     */
    @Override
    public void update() {
        // if the entity had already seen three alive neighbours in the past
        // then including the current alive neighbour this entity has
        // more than 3 neighbours, so it will die.
        if ((this.value & 3) == 3) {
            this.value = 3;
        } 
        // if entity has currently seen 2 alive neighbours then it lives
        // and sets its alive neighbour count to 3.
        else if ((this.value & 3) == 2) {
            this.value = 7;
        } 
        // if the entity has currently seen less than 3 alive neighbours
        // then it increments its alive neighbour count.
        else {
            this.value += 1;
        }
    }
}
