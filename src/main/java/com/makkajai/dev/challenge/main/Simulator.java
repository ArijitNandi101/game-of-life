package com.makkajai.dev.challenge.main;

import com.makkajai.dev.challenge.ds.planar.HalfByteGrid;
import com.makkajai.dev.challenge.ds.planar.Vec2i;
import com.makkajai.dev.challenge.physics.CoordinateTransformer;
import com.makkajai.dev.challenge.systems.entitySystem.World2D;
import com.makkajai.dev.challenge.main.descriptors.GOLDescriptor;
import com.makkajai.dev.challenge.main.descriptors.GOLSelfDescriptor;
import com.makkajai.dev.challenge.main.descriptors.GOLNeighbourDescriptor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.java.Log;

/**
 * This class encapsulates the simulation of the Game of Life World.
 * This is used to tick through each moment in the simulation.
 * It creates a minimum bounding cartesian grid of integer coordinates, 
 * which it then uses to update the location of alive entities.
 */
@Log
public class Simulator {
    
    /**
     * This is the grid for the minimum bounding cartesian plane represented
     * as an instance of {@link HalfByteGrid}
     * which stores the entity location data and their current state descriptors.
     */
    private HalfByteGrid grid = new HalfByteGrid();

    /**
     * this encapsulates the minimum bounding cartesian plane and all the entities
     * found within it. This includes all the currently alive entities and all of their
     * immediate neighbours.
     */
    private World2D<GOLEntity2D> world = new World2D();

    /**
     * transforms the coordinates of the entities present in the world
     * (which can be any integral value) to grid space
     * (which is 0 bound on the lower side).
     */
    private CoordinateTransformer<Vec2i> worldToGridTransformer;

    /**
     * transforms the coordinates of the entities present as present in the {@link #grid}
     * (which is 0 bound on the lower side) to world space
     * (which can be any integral value).
     */
    private CoordinateTransformer<Vec2i> gridToWorldTransformer;

    /**
     * Initializes the simulator's {@link World2D} with a seed values, i.e.
     * the first generation of alive entities in the Game of Life world.
     *
     * @param seed The input alive entity coordinates in the Game of Life world.
     *             These seed locations are stored in entities in the {@link World2D}.
     */
    public void seed(List<Vec2i> seed) {

        // reset the minimum bounding cartesian plane bounds
        world.clear();

        // encapsulate the seed coordinates as entities and add them to the
        // Game of Life world.
        seed.stream()
        .map(GOLEntity2D::new)
        .forEach(world::addEntity);

        this.worldToGridTransformer = new CoordinateTransformer(world.getTopLeft().negate());
        this.gridToWorldTransformer = new CoordinateTransformer(world.getTopLeft());
    }

    /**
     * This method ticks throw a single moment in the Game of Life world. Currently,
     * it clears the {@link #grid}, initializes it with all currently alive entity's location data
     * and then performs update operations on the {@link #grid}.
     * Then it uses the {@link #grid}'s updated data to add new entities to the
     * game of life {@link #world} when the come alive or remove them when they die.
     */
    public void tick() {

        // clear previous data in the grid
        this.grid.clear();

        // allocate memory to the grid for it to be able to store the entity coordinates
        this.grid.createGrid(this.world.getXBoundRange(), this.world.getYBoundRange());

        // initialize all the currently alive entity locations in the grid.
        // This can be done in parallel because the order of initialization or multiple
        // initializations of a grid location does not affect the final state of the grid.
        this.world.getEntities().parallelStream()
            .forEach(entity -> this.grid.setCell(
                worldToGridTransformer.transform(entity.getPosition()), 
                GOLDescriptor.CURRENT_ALIVE_STATE
            ));
        
        // This is the main tick step where we update the alive or dead entity states in the grid
        // in accordance with rules of the Game of Life.
        this.world.getEntities().stream().forEach(this::updateGrid);

        // Finally, we update the world, adding new entities or removing old ones, based on
        // the updated collective state of the grid.
        updateWorld();
    }

    /**
     * Renders the coordinates of all the entities present in {@link #world},
     * each on one life as comma separated xy values.
     */
    public void render() {
        getAliveEntityPositionStream()
            .map(entityPosition -> String.format("%d, %d", entityPosition.x, entityPosition.y))
            .forEach(System.out::println);
    }

    /**
     * Gets a list of the coordinates of all the Game of Life Entities
     * currently present in the {@link #world}
     *
     * @return {@link Stream} of {@link Vec2i} representing 2 dimensional coordinates
     *          of the game of life entities.
     */
    Stream<Vec2i> getAliveEntityPositionStream() {
        return this.world.getEntities().stream()
            .map(GOLEntity2D::getPosition);
    }

    /**
     * This is used to update the world based on the location of a currently alive entity.
     * The neighbouring 8 entities surrounding the entity's location along with the
     * current entity will be updated accordingly.
     * 
     * @param entity The currently alive entity which will update
     *              a portion of the world immediately surrounding it.
     */
    private void updateGrid(GOLEntity2D entity) {
        Vec2i gridCoords = this.worldToGridTransformer.transform(entity.getPosition());
        // we get the current entity's state descriptor from the grid for update.
        GOLSelfDescriptor self_descriptor = new GOLSelfDescriptor(grid.getCell(gridCoords));

        // we stream through all the neighbouring positions of the current entity
        entity.getNeighbourPositions().stream()
            // converting them to grid space
            .map(this.worldToGridTransformer::transform)
            // and update the grid based on each of their corresponding state descriptors
            .forEach(neighbourCoords -> {
                // we get the current entity's current neighbour's state descriptor from the 
                // grid for update.
                GOLNeighbourDescriptor neighbour_descriptor = new GOLNeighbourDescriptor(
                    grid.getCell(neighbourCoords)
                );

                // if neighbour is another alive entity then we do not update
                // its descriptor, instead we update the current entity's descriptor.
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
     * This is used to set all the alive entities after the {@link #grid} is updated.
     * Any previously alive entity that dies after {@link #grid} update is removed from the {@link #world}.
     * Any previously dead entity that lives after {@link #grid} update is added to the {@link #world}.
     * All other entities remain unchanged in the {@link #world}.
     */
    private void updateWorld() {
        List<GOLEntity2D> deadEntities = this.world.getEntities().stream()
            .filter(entity -> 
                !new GOLSelfDescriptor(
                    grid.getCell(this.worldToGridTransformer.transform(entity.getPosition()))
                ).willLive()
            ).collect(Collectors.toList());
        
        List<GOLEntity2D> aliveNeighbouringEntities = this.world.getEntities().stream()
            .flatMap(entity -> entity.getNeighbourPositions().stream())
            .distinct()
            .map(this.worldToGridTransformer::transform)
            // filter and allow only newly alive neighbouring entities to proceed
            .filter(gridCoords -> new GOLNeighbourDescriptor(grid.getCell(gridCoords)).willLive())
            // the grid offset that was earlier applied to the entity locations is removed here
            .map(this.gridToWorldTransformer::transform)
            .map(GOLEntity2D::new)
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
