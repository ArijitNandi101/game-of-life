package com.makkajai.dev.challenge.main.descriptors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GOLNeighbourDescriptor extends GOLDescriptor {
    
    private int value;

    /**
     * This method is used to update the 4 bit entity state valuepritor of a
     * currently dead entity based on the rules of Game of live.
     * 
     * The highest Bit is 0 for currently dead entities.
     * 
     * The 2 lowest bits keep track of how many alive entities it is surrounded by.
     * 
     * The 3rd lowest bit represents if this entity will be alive after update. It is
     * initially 0, representing dead. It will be switched to 1 if it is going to
     * live.
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
            this.value = 3;
        } 
        // if entity has currently seen 2 alive nighbours then it lives
        // and sets its alive neighbour count to 3.
        else if ((this.value & 3) == 2) {
            this.value = 7;
        } 
        // if the entity has currently seen less than 3 alive nighbours
        // then it increments its alive neighbour count.
        else {
            this.value += 1;
        }
    }
}
