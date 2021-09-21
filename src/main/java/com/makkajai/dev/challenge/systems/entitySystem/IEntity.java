package com.makkajai.dev.challenge.systems.entitySystem;

import com.makkajai.dev.challenge.ds.planar.Vec2i;

import java.util.UUID;

public interface IEntity {

    UUID getId();

    Vec2i getPosition();

}
