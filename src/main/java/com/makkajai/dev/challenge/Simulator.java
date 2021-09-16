package com.makkajai.dev.challenge;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.makkajai.dev.challenge.ds.planar.HalfByteGrid;
import com.makkajai.dev.challenge.ds.planar.Vec2i;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.java.Log;

/**
 * This class encapsulates the simulation of the Game of Life World.
 * This calls ticks throw each moment in the simulation.
 * It creates a minimum bounding cartesian grid of integer coordinates, 
 * which it then uses to update the location of alive entities.
 */
@Log
public class Simulator {

    
    
    /**
     * This is the lower bound as {@link com.makkajai.dev.challenge.ds.planar.Vec2i}
     * of the minimum bounding cartesian grid. 
     * This is always less than the higher bound for a valid grid.
     */
    @Getter(AccessLevel.PACKAGE)
    private Vec2i topLeft = new Vec2i();

    
    /**
     * This is the higher bound as {@link com.makkajai.dev.challenge.ds.planar.Vec2i}
     * of the minimum bounding cartesian grid.
     * This is always more than the lower bound for a valid grid.
     */
    @Getter(AccessLevel.PACKAGE)
    private Vec2i bottomRight = new Vec2i();

    /**
     * This is the minimum bounding cartesian grid represented as an instance of 
     * {@link com.makkajai.dev.challenge.ds.planar.HalfByteGrid}
     * which stores the alive and dead entity location data.
     */
    private HalfByteGrid grid = new HalfByteGrid();

    /**
     * This method ticks throw a single moment in the Game of Life world. Currently,
     * it reinitializes the grid from the input seed and then performs a single
     * update operation on the grid.
     * 
     * @param seed The input alive entity coordinates in the Game of Life world.
     *             These seed locations are mappd to the grid.
     * @return A {@link java.util.List} of alive entity locations after a single update
     *              of the game world.
     */
    public List<Vec2i> tick(List<Vec2i> seed) {

        // clear previous data in the grid
        grid.clear();

        // reset the grid bounds and update the bounds of the minimum bounding cartesian grid
        this.resetBounds();
        for(Vec2i entityPosition: seed) {
            this.expandBounds(entityPosition);
        }

        // allocate memory to the grid for it to be able to store the entity coordinates
        grid.createGrid(bottomRight.x - topLeft.x + 1, bottomRight.y - topLeft.y + 1);

        // initialize all the seeding (currently alive) entity locations in the grid.
        // This can be done parallelly because the order of initialization or multiple
        // initializations of a grid location does not effect the final state of the grid.
        seed.parallelStream().forEach(entityPosition -> 
            grid.setCell(entityPosition.x - topLeft.x, entityPosition.y - topLeft.y, 8)
        );
        
        // This is the main tick step where we update the alive or dead entities in the grid
        // in accordance with rules of the Game of Life.
        seed.stream().forEach(this::updateWorld);

        // We collect the alive entity coordinates from the new state of the grid and return them.
        return getAliveEntities(seed).stream().collect(Collectors.toList());
    }

    void resetBounds() {
        topLeft.x = topLeft.y = Integer.MAX_VALUE;
        bottomRight.x = bottomRight.y = Integer.MIN_VALUE;
    }

    /**
     * Update the minimum bounds of the frid on both axes. The minimum bounds are computed 
     * such that there is enough space to store all alive entities before and after the update. 
     * This is possible because the minimum bounding grid is 2 more than
     * the actual minimum distance occupied by the entities in both axes.
     * 
     * @param entityPosition The position of an alive entity that needs to fit within the
     *          grid bounds. This is used to upadate the minimum bounds.
     */
    void expandBounds(Vec2i entityPosition) {
        topLeft = new Vec2i(Math.min(topLeft.x, entityPosition.x - 1), Math.min(topLeft.y, entityPosition.y - 1));
        bottomRight = new Vec2i(Math.max(bottomRight.x, entityPosition.x + 1),Math.max(bottomRight.y, entityPosition.y + 1));
    }

    /**
     * This method is used to update the 4 bit entity state descpritor of a 
     * currently alive entity based on the rules of Game of live.
     * 
     * The highest Bit is 1 for currently alive entities.
     * 
     * The 2 lowest bits keep track of how many alive entities it is surrounded by.
     * 
     * The 3rd lowest bit represents if this entity will be alive after update.
     * It is initially 0, representing dead. It will be switched to 1 if it is going to live.
     * 
     * @param self_descriptor the current entity state descriptor to be updated.
     * @return the updated entity descriptor after evaluation with the Game of Life rules.
     */
    int updateSelfDescriptor(int self_descriptor) {
        // if the entity had alrady seen three alive neighbours in the past 
        // then including the current alive neighbour this entity has 
        // more than 3 neighbours so it will die.
        if ((self_descriptor & 3) == 3) {
            return 0xB;
        } else {
            // if entity has currently seen more than 1 alive nighbours then it lives
            // and increments its alive neighbour count.
            if ((self_descriptor & 3) != 0) {
                self_descriptor |= 4;
            }
            return self_descriptor + 1;
        }
    }

    /**
     * This method is used to update the 4 bit entity state descpritor of a
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
     * @param self_descriptor the current entity state descriptor to be updated.
     * @return the updated entity descriptor after evaluation with the Game of Life rules.
     */
    int updateNeighbourDescriptor(int neighbour_descriptor) {
        // if the entity had alrady seen three alive neighbours in the past
        // then including the current alive neighbour this entity has
        // more than 3 neighbours so it will die.
        if ((neighbour_descriptor & 3) == 3) {
            return 3;
        } 
        // if entity has currently seen 2 alive nighbours then it lives
        // and sets its alive neighbour count to 3.
        else if ((neighbour_descriptor & 3) == 2) {
            return 7;
        } 
        // if the entity has currently seen less than 3 alive nighbours
        // then it increments its alive neighbour count.
        else {
            return neighbour_descriptor + 1;
        }
    }

    /**
     * This is used to update the world based on the location of a currently alive entity.
     * The neighbouring 8 entities surrounding the entity's location along with the
     * current entity will be updated accordingly.
     * 
     * @param entityPosition The position of th currently alive entity which will update the
     *              a portion of the world immediately surrounding it.
     */
    void updateWorld(Vec2i entityPosition) {
        // Subtract the grid offset from the entity's location to find its grid coordinates.
        // This is done because the entity's location is integral but the grid is 0 indexed.
        // This offset will be removed later after the entire update is complete.
        Vec2i coords = entityPosition.subtract(topLeft);

        // we get the current entity's state descriptor from the grid for updation.
        int self_descriptor = grid.getCell(coords.x, coords.y);

        // we loop through both axes determining the offsets of the current ntity's neighbours. 
        for (int y_offset = -1; y_offset < 2; ++y_offset) {
            for (int x_offset = -1; x_offset < 2; ++x_offset) {

                // skip if offsets are 0 as it the coordinate of the currently alive entity.
                if (y_offset == 0 && x_offset == 0) continue;

                // we get the current entity's current neighbour's state descriptor from the 
                // grid for updation.
                int neighbour_descriptor = grid.getCell(coords.x + x_offset, coords.y + y_offset);

                // if neighbour is another alive entity then we do not update
                // its descriptor, instead we update the current entities descriptor.
                if ((neighbour_descriptor >> 3) == 1) {
                    self_descriptor =  updateSelfDescriptor(self_descriptor);
                }
                // if the neighbour is dead then we update its descriptor.
                // It is because this neighbour has seen the current entity as its alive neighbour. 
                else {
                    // we set the current entity's current neighbour's updated state descriptor.
                    grid.setCell(
                        coords.x + x_offset, 
                        coords.y + y_offset, 
                        updateNeighbourDescriptor(neighbour_descriptor)
                    );
                }
            }
        }
        // we set the current entity's updated state descriptor.
        grid.setCell(coords.x, coords.y, self_descriptor);
    }

    /**
     * This is used to get all the alive entities after the updation of the world
     * (i.e. tick) is complete. It returns the alive entities based on their updated
     * state descriptors.
     * 
     * @param entityPositions the previously alive entities for searching through
     *                        current state of the world efficiently. Because only
     *                        these entities and their immediate neighbours have
     *                        been influenced in the world.
     * @return the {@link java.util.Set} of alive entities in the updated game world.
     */
    private Set<Vec2i> getAliveEntities(List<Vec2i> entityPositions) {
        return entityPositions.stream()
            .map(coords -> coords.subtract(topLeft))
            .flatMap(coords -> IntStream.range(-1, 2)
                .mapToObj(y_offset -> (int) y_offset)
                .flatMap(y_offset -> IntStream.range(-1, 2)
                    .mapToObj(x_offset -> (int) x_offset)
                    .map(x_offset -> new Vec2i(coords.x + x_offset, coords.y + y_offset))
                )
                // filter and allow only alive entities to proceed
                .filter(offsetCoords -> (grid.getCell(offsetCoords.x, offsetCoords.y) & 4) > 0)
                // the grid offset that was earlier applied to the entity locations is removed here
                .map(topLeft::add)
            ).collect(Collectors.toSet());
    }

    /**
     * Prints the grid as a rectangle along with the entity descriptors
     */
    public void prettyPrint() {
                                          
        grid.print(x -> 
            // currently alive status
            (x & 8) 
                // alive after update status
            + " A: " + ((x & 4) >> 2) 
                // total alive neighbours seen
            + " N: " + (x & 3)
        );
    }
}
