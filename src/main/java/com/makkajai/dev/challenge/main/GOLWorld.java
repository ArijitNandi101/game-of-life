package com.makkajai.dev.challenge.main;

import java.util.Map;
import java.util.UUID;
import java.util.Collection;
import java.util.HashMap;

/**
 * This class represents a 2d world of entities. The world entities are contained
 * that are alive or can become alive are stored usually to limit the amount.
 * of storage required.
 */
public class GOLWorld {

    /**
     * this is the list all entities, alive or dead, that are relevant to
     * game of life world at any given moment.
     */
    private Map<UUID, GOLEntity> entities = new HashMap();

    /**
     * Adds an entity to the world.
     * If the entity to be added to the world is already present in the world,
     * nothing is changed.
     * The entity is informed of its neighbours also currently present in the world
     * whose reference will be saved by this entity. the reference of this entity is also
     * saved by its neighbours as it is a symmetric relationship between entities.
     *
     * @param entity the entity to be added
     */
    public void addEntity(GOLEntity entity) {
        if (this.entities.containsKey(entity.getId())) return;
        this.addNeighbouringEntities(entity);
        this.entities.put(entity.getId(), entity);
    }

    private void addNeighbouringEntities(GOLEntity entity) {
        this.entities.values().stream()
            .filter(entity::isNeighbourOf)
            .forEach(neighbouringEntity -> {
                neighbouringEntity.addNeighbour(entity);
                entity.addNeighbour(neighbouringEntity);
            });
    }

    /**
     * Removes an entity from this world. Also removes all references of this entity
     * as a neighbour of any other entities.
     * If the entity to be removed is not present in the world, nothing happens.
     *
     * @param entity the entity to be removed.
     */
    public void removeEntity(GOLEntity entity) {
        if (!this.entities.containsKey(entity.getId())) return;
        entity.getNeighbours().stream().forEach(neighbouringEntity -> neighbouringEntity.removeNeighbour(entity));
        this.entities.remove(entity.getId());
    }

    /**
     * @return a {@link Collection} of all the entities present in this world.
     */
    public Collection<GOLEntity> getEntities() {
        return entities.values();
    }

    /**
     * removes all entities from the world and resets its bounds.
     */
    public void clear() {
        this.entities.clear();
    }
}
