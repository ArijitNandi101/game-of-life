package com.makkajai.dev.challenge;

import java.util.HashSet;
import java.util.List;
import com.makkajai.dev.challenge.ds.planar.HalfByteGrid;
import com.makkajai.dev.challenge.ds.planar.Vec2i;

public class Simulator {

    private Vec2i topLeft = new Vec2i(Integer.MAX_VALUE, Integer.MAX_VALUE);
    private Vec2i bottomRight = new Vec2i(Integer.MIN_VALUE, Integer.MIN_VALUE);

    HalfByteGrid grid = new HalfByteGrid();

    public HashSet<Vec2i> tick(List<Vec2i> seed) {
        grid.reset();

        for(Vec2i entityPosition: seed) {
            this.expandBounds(entityPosition);
        }
        grid.createGrid(bottomRight.x - topLeft.x + 1, bottomRight.y - topLeft.y + 1);

        for (Vec2i entityPosition : seed) {
            grid.setCellDescriptor(entityPosition.x - topLeft.x, entityPosition.y - topLeft.y, 8);
        }
        for (Vec2i entityPosition : seed) {
            this.updateNeighbours(entityPosition);
        }
        return getAliveEntities(seed);
    }

    public void expandBounds(Vec2i entityPosition) {
        topLeft = new Vec2i(Math.min(topLeft.x, entityPosition.x - 1), Math.min(topLeft.y, entityPosition.y - 1));
        bottomRight = new Vec2i(Math.max(bottomRight.x, entityPosition.x + 1),
                Math.max(bottomRight.y, entityPosition.y + 1));
    }

    public void updateNeighbours(Vec2i entityPosition) {
        int y_coord = entityPosition.y - topLeft.y;
        int x_coord = entityPosition.x - topLeft.x;
        int self_descriptor = grid.getCellDescriptor(x_coord, y_coord);
        for (int i = -1; i < 2; ++i) {
            for (int j = -1; j < 2; ++j) {
                if (i == 0 && j == 0)
                    continue;
                int neighbour_descriptor = grid.getCellDescriptor(x_coord + j, y_coord + i);

                if ((neighbour_descriptor >> 3) == 1) {
                    if ((self_descriptor & 3) == 3) {
                        self_descriptor = 0xB;
                    } else {
                        if ((self_descriptor & 3) != 0) {
                            self_descriptor |= 4;
                        }
                        self_descriptor++;
                    }
                } else {
                    if ((neighbour_descriptor & 3) == 3) {
                        neighbour_descriptor = 3;
                    } else if ((neighbour_descriptor & 3) == 2) {
                        neighbour_descriptor = 7;
                    } else {
                        neighbour_descriptor++;
                    }
                    grid.setCellDescriptor(x_coord + j, y_coord + i, neighbour_descriptor);
                }
            }
        }
        grid.setCellDescriptor(x_coord, y_coord, self_descriptor);
    }

    public HashSet<Vec2i> getAliveEntities(List<Vec2i> entityPositions) {
        HashSet<Vec2i> aliveEntities = new HashSet<>();
        
        for (Vec2i position: entityPositions) {
            int y_coord = position.y - topLeft.y;
            int x_coord = position.x - topLeft.x;

            for (int i = -1; i < 2; ++i) {
                for (int j = -1; j < 2; ++j) {
                    int cell_descriptor = grid.getCellDescriptor(x_coord + j, y_coord + i);
                    if ((cell_descriptor & 4) > 0) {
                        aliveEntities.add(new Vec2i(x_coord + j + topLeft.x, y_coord + i + topLeft.y));
                    }
                }
            }
        }
        return aliveEntities;
    }

    public void prettyPrint() {
        grid.print(x -> (x & 8) + " A: " + ((x & 4) >> 2) + " N: " + (x & 3));
    }
}
