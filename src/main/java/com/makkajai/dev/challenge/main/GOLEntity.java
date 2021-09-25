package com.makkajai.dev.challenge.main;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import com.makkajai.dev.challenge.ds.planar.Vec2i;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is a representation of a Game of Life entity. The entity
 * has an ID, a location, and can be {@link State#ALIVE} or {@link State#DEAD}
 * at any given moment.
 * The ID is of every entity is unique for a given location.
 * The entity also contains references to its neighbours that exist in the
 * Game of Life world.
 */
public class GOLEntity implements Serializable {

    /**
     * An enum type describing the that of a game of life entity
     * which can be either alive or dead at any given time.
     */
    public enum State {
        ALIVE,
        DEAD,
    }

    /**
     * A conditionally unique identifier for the entity corresponding
     * to each location in the 2 dimensional plane where this entity lives.
     */
    @Getter
    private UUID id;

    /**
     * The 2 dimensional (x, y) integral position of the entity.
     */
    @Getter
    private Vec2i position;

    /**
     * The {@link State} of this entity.
     */
    @Getter
    @Setter
    private State state;

    /**
     * A list of possible neighbours of
     * this entity in the same 2 dimensional plane.
     */
    private HashMap<UUID, GOLEntity> neighbours;

    /**
     * Initializes the entity with is location based unique id, its 2 dimensional
     * location, and its state as {@link State#DEAD}.
     *
     * @param position The {@link Vec2i} containing current position (x, y) of the entity.
     *                 This is used to derive other data for the entity such as its id.
     */
    public GOLEntity(Vec2i position) {
        this.id = UUID.nameUUIDFromBytes(position.toString().getBytes());
        this.position = position;
        this.state = State.DEAD;
        this.neighbours = new HashMap<>();
    }

    public GOLEntity(int positionX, int positionY) {
        this(new Vec2i(positionX, positionY));
    }

    /**
     * Adds a reference to {@link #neighbours} of another {@link GOLEntity} which is
     * a neighbour of this entity.
     * The neighbours are not verified here since the rules of being neighbours is handled
     * by the external system managing these entities.
     * @param neighbour the neighbour of this entity whose reference is to be remembered
     *                  by this entity.
     */
    public void addNeighbour(GOLEntity neighbour) {
        this.neighbours.putIfAbsent(neighbour.getId(), neighbour);
    }

    /**
     * Removes any previously added entity from this entity's {@link #neighbours} list
     * in case that entity is no longer valid for referencing.
     *
     * @param neighbour the neighbour of this entity which needs to be forgotten by
     *                  this entity.
     */
    public void removeNeighbour(GOLEntity neighbour) { this.neighbours.remove(neighbour.getId()); }

    /**
     * @return {@link Collection} of all the known {@link GOLEntity} that are known to
     *                  be neighbours of this entity.
     */
    public Collection<GOLEntity> getNeighbours() {
        return this.neighbours.values();
    }

    /**
     * Counts the number of alive entities among the known {@link #neighbours} of
     * this entity and returns the count.
     *
     * @return the number of alive neighbours of this entity.
     */
    public byte getAliveNeighbourCount() {
        return (byte) this.neighbours.values().stream().filter(GOLEntity::isAlive).count();
    }

    /**
     * @return true if this entities {@link #state} is {@link State#ALIVE},
     * otherwise false.
     */
    public boolean isAlive() {
        switch (this.getState()) {
            case ALIVE: return true;
            default: return false;
        }
    }

    /**
     * @return true if this entities {@link #state} is {@link State#DEAD},
     * otherwise false.
     */
    public boolean isDead() {
        switch (this.getState()) {
            case DEAD: return true;
            default: return false;
        }
    }

    /**
     * Checks to see of another {@link GOLEntity} is the neighbour of this entity.
     * For two entity to be neighbours, the difference between their corresponding
     * x and y coordinates must be 0 or 1 for both of the coordinates.
     *
     * @param other the other {@link GOLEntity} which is being checked as a
     *              potential neighbour.
     * @return true if the other entity is a neighbour, otherwise false.
     */
    public boolean isNeighbourOf(GOLEntity other) {
        return Math.abs(this.getPosition().x - other.getPosition().x) < 2
            && Math.abs(this.getPosition().y - other.getPosition().y) < 2;
    }

    @Override
    public String toString() {
        return String.format("{id: %s, position: %s, state: %s}", this.id, this.position, this.state);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof GOLEntity))
            return false;
        GOLEntity other = (GOLEntity) o;
        return this.id.equals(other.id);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(this.id, this.position);
    }
}
