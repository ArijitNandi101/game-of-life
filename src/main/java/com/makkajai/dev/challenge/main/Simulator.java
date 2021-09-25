package com.makkajai.dev.challenge.main;

import com.makkajai.dev.challenge.ds.planar.Vec2i;
import com.makkajai.dev.challenge.physics.CoordinateTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.extern.java.Log;

/**
 * This class encapsulates the simulation of the Game of Life World.
 * This is used to tick through each moment in the simulation.
 * It keeps track of all entities that can be effected during a particular
 * tick in time to simulate the world.
 */
@Log
public class Simulator {

    /**
     * A group of 8 {@link CoordinateTransformer} that represent the eight relative
     * coordinate locations of any entity in the Game of Life world.
     */
    private static List<CoordinateTransformer<Vec2i>> neighbourCoordinateTransformers;

    static {
        neighbourCoordinateTransformers = computeNeighbourCoordinateTransformers();
    }

    /**
     * this encapsulates the  all the alive entities and their immediate neighbours
     * found within the game of life. Each entity in the {@link #world} is aware of its
     * immediate neighbours that are also present in the {@link #world}.
     */
    private GOLWorld world = new GOLWorld();

    /**
     * Compute all possible neighbour coordinates of any entity. In a 2 dimensional
     * plane, there are eight such neighbours. One each on the top, bottom,
     * left, right, top-left, bottom-left, top-right, and bottom-right.
     *
     * @return {@link List} of {@link CoordinateTransformer<Vec2i>}
     *              containing the relative position of all the possible
     *              neighbours of an entity in the same coordinate system.
     */
    private static List<CoordinateTransformer<Vec2i>> computeNeighbourCoordinateTransformers() {
        return IntStream.range(-1, 2).mapToObj(y_offset -> y_offset)
                .flatMap(y_offset -> IntStream.range(-1, 2).mapToObj(x_offset -> x_offset)
                        .map(x_offset -> new Vec2i(x_offset, y_offset))
                ).filter(offsets -> offsets.x != 0 || offsets.y != 0)
                .map(CoordinateTransformer::new)
                .collect(Collectors.toList());
    }

    /**
     * Initializes the simulator's {@link #world} with seed values, i.e.
     * the first generation of alive entities in the Game of Life world.
     *
     * @param seed The input alive entity coordinates in the Game of Life world.
     *             These seed locations are stored in entities in the {@link #world}.
     */
    public void seed(List<Vec2i> seed) {

        // reset the world clearing all entities in it.
        world.clear();

        // encapsulate the seed coordinates as alive entities and add them to the
        // Game of Life world.
        seed.stream()
            .map(GOLEntity::new)
            .peek(entity -> entity.setState(GOLEntity.State.ALIVE))
            .forEach(world::addEntity);

        // collects the neighbouring coordinates of the seed and adds them as dead
        // entities in the world. Any neighbour which is also an alive entity added
        // in the previous step will not be inserted because it has the same ID.
        neighbourCoordinateTransformers.stream()
                .flatMap(coordinateTransformer ->
                        seed.stream().map(coordinateTransformer::transform)
                ).distinct()
                .map(GOLEntity::new)
                .forEach(world::addEntity);
    }

    /**
     * This method ticks through one moment in the game of life world. It goes through
     * each entity and applies the rules of game of life to find the next set of alive
     * entities and saves them in the world, and it removes redundant dead entities.
     */
    public void tick() {
        // A list of alive entities that will die after the current tick.
        List<GOLEntity> willDie = new ArrayList<>();

        // A list of dead entities that will come alive after the current tick.
        List<GOLEntity> willLive = new ArrayList<>();

        //here will loop through all entities currently in the world
        // and pick the entities that will change their state in accordance
        // with rules of the Game of Life.
        this.world.getEntities().stream().forEach(entity -> {
            byte aliveNeighbourCount = entity.getAliveNeighbourCount();
            switch (entity.getState()) {
                case ALIVE:
                    // GAME RULE: an alive entities must have 2 or 3 alive neighbours
                    // in order to live for the next tick
                    if (aliveNeighbourCount < 2 || aliveNeighbourCount > 3) {
                        willDie.add(entity);
                    }
                    break;
                case DEAD:
                    // GAME RULE: a dead entity must have exactly 3 alive neighbours
                    // in order to come alive for the next tick
                    if (aliveNeighbourCount == 3) {
                        willLive.add(entity);
                    }
                    break;
                default: throw new RuntimeException(String.format(
                        "Unexpected GOLEntity State encountered: [%s]",
                        entity.getState().toString()
                ));
            }
        });

        // kill all alive entities that will not live for the next tick.
        willDie.forEach(entity -> entity.setState(GOLEntity.State.DEAD));

        // bring to life all dead that will live for the next tick.
        willLive.forEach(entity -> entity.setState(GOLEntity.State.ALIVE));

        // Finally, we update the world, adding new entities or removing old ones, based on
        // the updated collective state of the world.
        updateWorld(willLive);
    }

    /**
     * Renders the coordinates of all alive entities present in {@link #world},
     * each on one line as comma separated xy values.
     */
    public void render() {
        getAliveEntityPositionStream()
            .map(entityPosition -> String.format("%d, %d", entityPosition.x, entityPosition.y))
            .forEach(System.out::println);
    }

    /**
     * Gets a list of the coordinates of all alive Game of Life entities
     * currently present in the {@link #world}
     *
     * @return {@link Stream} of {@link Vec2i} representing 2 dimensional coordinates
     *          of the game of life entities.
     */
    Stream<Vec2i> getAliveEntityPositionStream() {
        return this.world.getEntities().stream()
            .filter(GOLEntity::isAlive)
            .map(GOLEntity::getPosition);
    }

    /**
     * After the fate of the entities for the next tick is decided, this method
     * removes all those entities that are dead for the next tick and are not
     * immediate neighbours of the alive entities in the next tick.
     * Then it adds all the neighbours of the currently alive entities in the
     * {@link #world} that were not previously present in the world.
     *
     * @param willLive a {@link List} of {@link GOLEntity} that were dead previously
     *                 but have come alive for the next tick.
     */
    private void updateWorld(List<GOLEntity> willLive) {

        // collect all detached entities
        // (entities that are not neighbours of any alive entity)
        List<GOLEntity> detachedEntities = this.world.getEntities().stream()
                .filter(GOLEntity::isDead)
                .filter(entity -> entity.getAliveNeighbourCount() == 0)
                        .collect(Collectors.toList());

        // and then remove the detached entities form the world.
        // They will not interact with the world in the next tick in any way.
        detachedEntities.stream().forEach(world::removeEntity);


        // add neighbours of any entity that was previously dead but has come alive
        // (because the neighbours of these entities might not be present in the world
        // since they were previously dead).
        neighbourCoordinateTransformers.stream()
                .flatMap(coordinateTransformer ->
                        willLive.stream()
                                .map(GOLEntity::getPosition)
                                .map(coordinateTransformer::transform)
                ).distinct()
                .map(GOLEntity::new)
                .forEach(world::addEntity);
    }
}
