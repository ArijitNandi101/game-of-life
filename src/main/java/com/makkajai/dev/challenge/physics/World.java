package com.makkajai.dev.challenge.physics;

import com.makkajai.dev.challenge.ds.planar.Vec2i;
import com.makkajai.dev.challenge.systems.entitySystem.IEntity;

import java.util.Map;
import java.util.UUID;
import java.util.Collection;
import java.util.HashMap;

import lombok.Getter;

public class World<T extends IEntity> {

    /**
     * This is the lower bound as {@link com.makkajai.dev.challenge.ds.planar.Vec2i}
     * of the minimum bounding cartesian grid. 
     * This is always less than the higher bound for a valid grid.
     */
    @Getter
    private Vec2i topLeft = new Vec2i();

    /**
     * This is the higher bound as {@link com.makkajai.dev.challenge.ds.planar.Vec2i}
     * of the minimum bounding cartesian grid.
     * This is always more than the lower bound for a valid grid.
     */
    @Getter
    private Vec2i bottomRight = new Vec2i();

    private Map<UUID, T> entities = new HashMap();

    public World() {
        this.resetBounds();
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
        topLeft.x = Math.min(topLeft.x, entityPosition.x - 1);
        topLeft.y = Math.min(topLeft.y, entityPosition.y - 1);

        bottomRight.x = Math.max(bottomRight.x, entityPosition.x + 1);
        bottomRight.y = Math.max(bottomRight.y, entityPosition.y + 1);
    }

    void resetBounds() {
        topLeft.x = topLeft.y = Integer.MAX_VALUE;
        bottomRight.x = bottomRight.y = Integer.MIN_VALUE;
    }

    public int getXBoundRange() {
        return bottomRight.x - topLeft.x + 1;
    }

    public int getYBoundRange() {
        return bottomRight.y - topLeft.y + 1;
    }

    public void addEntity(T entity) {
        this.entities.put(entity.getId(), entity);
        this.expandBounds(entity.getPosition());
    }

    public void removeEntity(T entity) {
        this.entities.remove(entity.getId());
    }

    public Collection<T> getEntities() {
        return entities.values();
    }

    public void clear() {
        this.resetBounds();
        this.entities.clear();
    }
}
