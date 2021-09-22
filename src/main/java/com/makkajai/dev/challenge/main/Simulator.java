package com.makkajai.dev.challenge.main;

import com.makkajai.dev.challenge.ds.planar.HalfByteGrid;
import com.makkajai.dev.challenge.ds.planar.Vec2i;
import com.makkajai.dev.challenge.physics.CoordinateTransformer;
import com.makkajai.dev.challenge.physics.World;
import com.makkajai.dev.challenge.main.descriptors.GOLDescriptor;
import com.makkajai.dev.challenge.main.descriptors.GOLSelfDescriptor;
import com.makkajai.dev.challenge.main.descriptors.GOLNeighbourDescriptor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
     * This is the minimum bounding cartesian grid represented as an instance of 
     * {@link com.makkajai.dev.challenge.ds.planar.HalfByteGrid}
     * which stores the alive and dead entity location data.
     */
    private HalfByteGrid grid = new HalfByteGrid();

    private World<GOLEntity> world = new World();

    private CoordinateTransformer<Vec2i> worldToGridTransformer;
    private CoordinateTransformer<Vec2i> gridToWorldTransformer;

    /**
     * 
     * @param seed The input alive entity coordinates in the Game of Life world.
     *             These seed locations are mappd to the grid.
     */
    public void seed(List<Vec2i> seed) {

        // reset the grid bounds and update the bounds of the minimum bounding cartesian grid
        world.clear();

        seed.stream()
        .map(GOLEntity::new)
        .forEach(world::addEntity);

        this.worldToGridTransformer = new CoordinateTransformer(world.getTopLeft().negate());
        this.gridToWorldTransformer = new CoordinateTransformer(world.getTopLeft());
    }

    /**
     * This method ticks throw a single moment in the Game of Life world. Currently,
     * it reinitializes the grid from the input seed and then performs a single
     * update operation on the grid.
     * 
     */
    public void tick() {

        // clear previous data in the grid
        this.grid.clear();

        // allocate memory to the grid for it to be able to store the entity coordinates
        this.grid.createGrid(this.world.getXBoundRange(), this.world.getYBoundRange());

        // initialize all the seeding (currently alive) entity locations in the grid.
        // This can be done parallelly because the order of initialization or multiple
        // initializations of a grid location does not effect the final state of the grid.
        this.world.getEntities().parallelStream()
            .forEach(entity -> this.grid.setCell(
                worldToGridTransformer.transform(entity.getPosition()), 
                GOLDescriptor.CURRENT_ALIVE_STATE
            ));
        
        // This is the main tick step where we update the alive or dead entities in the grid
        // in accordance with rules of the Game of Life.
        this.world.getEntities().stream().forEach(this::updateGrid);

        // We collect the alive entity coordinates from the new state of the grid and return them.
        updateWorld();
    }

    public void render() {
        getAliveEntityPositionStream()
            .map(entityPosition -> String.format("%d, %d", entityPosition.x, entityPosition.y))
            .forEach(System.out::println);
    }

    Stream<Vec2i> getAliveEntityPositionStream() {
        return this.world.getEntities().stream()
            .map(GOLEntity::getPosition);
    }

    /**
     * This is used to update the world based on the location of a currently alive entity.
     * The neighbouring 8 entities surrounding the entity's location along with the
     * current entity will be updated accordingly.
     * 
     * @param entityPosition The position of th currently alive entity which will update the
     *              a portion of the world immediately surrounding it.
     */
    private void updateGrid(GOLEntity entity) {
        Vec2i gridCoords = this.worldToGridTransformer.transform(entity.getPosition());
        // we get the current entity's state descriptor from the grid for updation.
        GOLSelfDescriptor self_descriptor = new GOLSelfDescriptor(grid.getCell(gridCoords));

        // we loop through both axes determining the offsets of the current ntity's neighbours. 
        entity.getNeighbourPositions().stream()
            .map(this.worldToGridTransformer::transform)
            .forEach(neighbourCoords -> {
                // we get the current entity's current neighbour's state descriptor from the 
                // grid for updation.
                GOLNeighbourDescriptor neighbour_descriptor = new GOLNeighbourDescriptor(
                    grid.getCell(neighbourCoords)
                );

                // if neighbour is another alive entity then we do not update
                // its descriptor, instead we update the current entities descriptor.
                if (neighbour_descriptor.isAlive()) {
                    self_descriptor.update();
                }
                // if the neighbour is dead then we update its descriptor.
                // It is because this neighbour has seen the current entity as its alive neighbour. 
                else {
                    neighbour_descriptor.update();
                    // we set the current entity's current neighbour's updated state descriptor.
                    grid.setCell(neighbourCoords, neighbour_descriptor.getValue());
                }
            });

        // we set the current entity's updated state descriptor.
        grid.setCell(gridCoords, self_descriptor.getValue());
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
    private void updateWorld() {
        List<GOLEntity> deadEntities = this.world.getEntities().stream()
            .filter(entity -> 
                !new GOLSelfDescriptor(
                    grid.getCell(this.worldToGridTransformer.transform(entity.getPosition()))
                ).willLive()
            ).collect(Collectors.toList());
        
        List<GOLEntity> aliveNeighbouringEntities = this.world.getEntities().stream()
            .flatMap(entity -> entity.getNeighbourPositions().stream())
            .distinct()
            .map(this.worldToGridTransformer::transform)
            // filter and allow only alive entities to proceed
            .filter(gridCoords -> new GOLNeighbourDescriptor(grid.getCell(gridCoords)).willLive())
            // the grid offset that was earlier applied to the entity locations is removed here
            .map(this.gridToWorldTransformer::transform)
            .map(GOLEntity::new)
            .collect(Collectors.toList());

        deadEntities.forEach(this.world::removeEntity);
        world.getEntities().clear();
        aliveNeighbouringEntities.forEach(this.world::addEntity);
    }

    /**
     * Prints the grid as a rectangle along with the entity descriptors
     */
    void prettyPrintGrid() {                                  
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
