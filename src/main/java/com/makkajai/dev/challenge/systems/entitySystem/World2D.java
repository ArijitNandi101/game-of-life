package com.makkajai.dev.challenge.systems.entitySystem;

import com.makkajai.dev.challenge.ds.planar.Vec2i;

import java.util.Map;
import java.util.UUID;
import java.util.Collection;
import java.util.HashMap;

import lombok.Getter;

/**
 * This class represents a 2d world of entities. The world entities are contained
 * within a minimum bounding cartesian plane.
 * The entities in this world has distinct  {@link IEntity2D#getId()}
 *
 * @param <T> any specialized type that behaves like an {@link IEntity2D}
 */
public class World2D<T extends IEntity2D> {

    /**
     * This is the lower bound as {@link com.makkajai.dev.challenge.ds.planar.Vec2i}
     * of the minimum bounding cartesian plane. 
     * This is always less than the higher bound for a valid plane.
     */
    @Getter
    private Vec2i topLeft = new Vec2i();

    /**
     * This is the higher bound as {@link com.makkajai.dev.challenge.ds.planar.Vec2i}
     * of the minimum bounding cartesian plane.
     * This is always more than the lower bound for a valid plane.
     */
    @Getter
    private Vec2i bottomRight = new Vec2i();

    private Map<UUID, T> entities = new HashMap();

    /**
     * initializes the world that has no entities and resets its bounds.
     */
    public World2D() {
        this.resetBounds();
    }

    /**
     * Update the bounds of the minimum bounding cartesian plane on both axes.
     * The minimum bounds are computed such that there is enough space to store
     * all alive entities and all their immediately neighbouring entities.
     * This is possible because the minimum bounding plane is 2 more than
     * the actual minimum distance occupied by any two entity in both axes.
     * 
     * @param entityPosition The position of an alive entity that needs to fit within the
     *          plane bounds. This is used to update the minimum bounds.
     */
    void expandBounds(Vec2i entityPosition) {
        topLeft.x = Math.min(topLeft.x, entityPosition.x - 1);
        topLeft.y = Math.min(topLeft.y, entityPosition.y - 1);

        bottomRight.x = Math.max(bottomRight.x, entityPosition.x + 1);
        bottomRight.y = Math.max(bottomRight.y, entityPosition.y + 1);
    }

    /**
     * resets the bounds of this world such that no entity can fit in it.
     */
    void resetBounds() {
        topLeft.x = topLeft.y = Integer.MAX_VALUE;
        bottomRight.x = bottomRight.y = Integer.MIN_VALUE;
    }

    /**
     * @return the y-axis range (from start to end, both inclusive) of the
     *          minimum bounding cartesian plane of thi world.
     */
    public int getXBoundRange() {
        return bottomRight.x - topLeft.x + 1;
    }

    /**
     * @return the y-axis range (from start to end, both inclusive) of the
     *         minimum bounding cartesian plane of thi world.
     */
    public int getYBoundRange() {
        return bottomRight.y - topLeft.y + 1;
    }

    /**
     * Adds an entity to the world.
     * If the entity to be added to the world is already present in the world,
     * Its new version replaces the older one.
     * Each time a new distinct entity is added to this world, its bounds
     * {@link #topLeft} and {@link #bottomRight} are reevaluated to include this entity's
     * location.
     * @param entity the entity to be added
     */
    public void addEntity(T entity) {
        this.entities.put(entity.getId(), entity);
        this.expandBounds(entity.getPosition());
    }

    /**
     * Removes an entity from this world.
     * If the entity to be removed is not present in the world, nothing happens.
     * @param entity the entity to be removed.
     */
    public void removeEntity(T entity) {
        this.entities.remove(entity.getId());
    }

    /**
     * @return a {@link Collection} of all the entities present in this world.
     */
    public Collection<T> getEntities() {
        return entities.values();
    }

    /**
     * removes all entities from the world and resets its bounds.
     */
    public void clear() {
        this.resetBounds();
        this.entities.clear();
    }
}
