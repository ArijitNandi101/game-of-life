package com.makkajai.dev.challenge.systems.entitySystem;

import com.makkajai.dev.challenge.ds.planar.Vec2i;

import java.util.UUID;

/**
 * Represents the behaviors of a 2 dimensional Entity described by its id
 * and its position on a 2 dimensional (x, y) plane.
 */
public interface IEntity2D {

    UUID getId();

    Vec2i getPosition();

}
