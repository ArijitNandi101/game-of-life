package com.makkajai.dev.challenge.main;

import java.io.Serializable;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;
import java.util.stream.IntStream;

import com.makkajai.dev.challenge.ds.planar.Vec2i;
import com.makkajai.dev.challenge.systems.entitySystem.IEntity2D;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * This class is a representation of a Game of Life entity. The entity
 * has an ID and a location. The ID is of every entity is unique for a
 * given location. The entity also contains the locations of its neighbours.
 */
@EqualsAndHashCode
@Getter
public class GOLEntity2D implements IEntity2D, Serializable {

    /**
     * A conditionally unique identifier for the entity corresponding
     * to each location in the 2 dimensional plane where this entity lives.
     */
    private UUID id;

    /**
     * The 2 dimensional (x, y) integral position of the entity.
     */
    private Vec2i position;

    /**
     * The 2 dimensional (x, y) positions of all possible neighbours of
     * the entity in the same 2 dimensional plane.
     */
    private List<Vec2i> neighbourPositions;

    /**
     * Initializes the entity with is location based unique id, its 2 dimensional
     * location, and all the locations of its possible neighbours.
     *
     * @param position The {@link Vec2i} containing current position (x, y) of the entity.
     *                 This is used to derive other data for the entity such as its
     *                 id and neighbour positions.
     */
    public GOLEntity2D(Vec2i position) {
        this.position = position;
        this.id = UUID.nameUUIDFromBytes(position.toString().getBytes());
        this.neighbourPositions = this.computeNeighbourPositions();
    }

    public GOLEntity2D(int positionX, int positionY) {
        this(new Vec2i(positionX, positionY));
    }

    /**
     * Compute all possible neighbours of this entity. In a 2 dimensional
     * plane, there are eight such neighbours. One each on the top, bottom,
     * left, right, top-left, bottom-left, top-right, and bottom-right.
     *
     * @return {@link List} of {@link Vec2i} containing the position of all the possible
     *              neighbours of this entity in the same coordinate system.
     */
    private List<Vec2i> computeNeighbourPositions() {
        return IntStream.range(-1, 2).mapToObj(y_offset -> (int) y_offset)
                .flatMap(y_offset -> IntStream.range(-1, 2).mapToObj(x_offset -> (int) x_offset)
                    .map(x_offset -> new Vec2i(x_offset, y_offset))
                ).filter(offsets -> offsets.x != 0 || offsets.y != 0)
                .map(position::translate)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("{id: %s, position: %s }", this.id, this.position);
    }
}
