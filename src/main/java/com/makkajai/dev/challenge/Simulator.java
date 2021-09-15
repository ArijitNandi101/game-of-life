package com.makkajai.dev.challenge;

import com.makkajai.dev.challenge.ds.Vec2i;

import java.util.List;
import com.makkajai.dev.challenge.ds.CartesianGrid;

public class Simulator {

    CartesianGrid grid = new CartesianGrid();;

    public void tick(List<Vec2i> seed) {
        
        for(Vec2i entityPosition: seed) {
            grid.expandBounds(entityPosition);
        }
        grid.createGrid();
        for (Vec2i entityPosition : seed) {
            grid.insert(entityPosition);
        }
        for (Vec2i entityPosition : seed) {
            grid.updateNeighbours(entityPosition);
        }
    }

    public void displayCurrentState() {
        grid.prettyPrint();
    }
}
