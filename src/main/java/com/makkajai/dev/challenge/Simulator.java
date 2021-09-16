package com.makkajai.dev.challenge;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.makkajai.dev.challenge.ds.planar.HalfByteGrid;
import com.makkajai.dev.challenge.ds.planar.Vec2i;

import lombok.Getter;
import lombok.extern.java.Log;

@Log
public class Simulator {

    @Getter
    private Vec2i topLeft = new Vec2i();

    @Getter
    private Vec2i bottomRight = new Vec2i();

    private HalfByteGrid grid = new HalfByteGrid();

    public List<Vec2i> tick(List<Vec2i> seed) {

        grid.clear();

        topLeft.x = topLeft.y = Integer.MAX_VALUE;
        bottomRight.x = bottomRight.y = Integer.MIN_VALUE;

        for(Vec2i entityPosition: seed) {
            this.expandBounds(entityPosition);
        }
        grid.createGrid(bottomRight.x - topLeft.x + 1, bottomRight.y - topLeft.y + 1);

        seed.parallelStream().forEach(entityPosition -> 
            grid.setCellDescriptor(entityPosition.x - topLeft.x, entityPosition.y - topLeft.y, 8)
        );
        
        seed.stream().forEach(this::updateNeighbours);
        return getAliveEntities(seed).stream().collect(Collectors.toList());
    }

    void expandBounds(Vec2i entityPosition) {
        topLeft = new Vec2i(Math.min(topLeft.x, entityPosition.x - 1), Math.min(topLeft.y, entityPosition.y - 1));
        bottomRight = new Vec2i(Math.max(bottomRight.x, entityPosition.x + 1),Math.max(bottomRight.y, entityPosition.y + 1));
    }

    int updateSelfDescriptor(int self_descriptor) {
        if ((self_descriptor & 3) == 3) {
            return 0xB;
        } else {
            if ((self_descriptor & 3) != 0) {
                self_descriptor |= 4;
            }
            return self_descriptor + 1;
        }
    }

    int updateNeighbourDescriptor(int neighbour_descriptor) {
        if ((neighbour_descriptor & 3) == 3) {
            return 3;
        } else if ((neighbour_descriptor & 3) == 2) {
            return 7;
        } else {
            return neighbour_descriptor + 1;
        }
    }

    void updateNeighbours(Vec2i entityPosition) {
        Vec2i coords = entityPosition.subtract(topLeft);
        int self_descriptor = grid.getCellDescriptor(coords.x, coords.y);
        for (int y_offset = -1; y_offset < 2; ++y_offset) {
            for (int x_offset = -1; x_offset < 2; ++x_offset) {
                if (y_offset == 0 && x_offset == 0) continue;
                int neighbour_descriptor = grid.getCellDescriptor(coords.x + x_offset, coords.y + y_offset);
                if ((neighbour_descriptor >> 3) == 1) {
                    self_descriptor =  updateSelfDescriptor(self_descriptor);
                } else {
                    grid.setCellDescriptor(
                        coords.x + x_offset, 
                        coords.y + y_offset, 
                        updateNeighbourDescriptor(neighbour_descriptor)
                    );
                }
            }
        }
        grid.setCellDescriptor(coords.x, coords.y, self_descriptor);
    }

    public Set<Vec2i> getAliveEntities(List<Vec2i> entityPositions) {
        return entityPositions.stream()
            .map(coords -> coords.subtract(topLeft))
            .flatMap(coords -> IntStream.range(-1, 2)
                .mapToObj(y_offset -> (int) y_offset)
                .flatMap(y_offset -> IntStream.range(-1, 2)
                    .mapToObj(x_offset -> (int) x_offset)
                    .map(x_offset -> new Vec2i(coords.x + x_offset, coords.y + y_offset))
                ).filter(offsetCoords -> (grid.getCellDescriptor(offsetCoords.x, offsetCoords.y) & 4) > 0)
                .map(topLeft::add)
            ).collect(Collectors.toSet());
    }

    public void prettyPrint() {
        grid.print(x -> (x & 8) + " A: " + ((x & 4) >> 2) + " N: " + (x & 3));
    }
}
