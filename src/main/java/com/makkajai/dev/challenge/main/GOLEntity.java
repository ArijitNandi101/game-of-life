package com.makkajai.dev.challenge.main;

import java.io.Serializable;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;
import java.util.stream.IntStream;

import com.makkajai.dev.challenge.ds.planar.Vec2i;
import com.makkajai.dev.challenge.systems.entitySystem.IEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
public class GOLEntity implements IEntity, Serializable {

    private UUID id;
    private Vec2i position;
    private List<Vec2i> neighbourPositions;

    public GOLEntity(Vec2i position) {
        this.position = position;
        this.id = UUID.nameUUIDFromBytes(position.toString().getBytes());
        this.neighbourPositions = this.computeNeighbourPositions();
    }

    public GOLEntity(int positionX, int positionY) {
        this(new Vec2i(positionX, positionY));
    }

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
