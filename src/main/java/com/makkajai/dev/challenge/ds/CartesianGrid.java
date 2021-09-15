package com.makkajai.dev.challenge.ds;

public class CartesianGrid {
    private Vec2i topLeft = new Vec2i(Integer.MAX_VALUE, Integer.MAX_VALUE);
    private Vec2i bottomRight = new Vec2i(Integer.MIN_VALUE, Integer.MIN_VALUE);
    private char[][] cells; 

    public void expandBounds(Vec2i entityPosition) {
        topLeft = new Vec2i(
            Math.min(topLeft.x, entityPosition.x - 1), 
            Math.min(topLeft.y, entityPosition.y - 1)
        );
        bottomRight = new Vec2i(
            Math.max(bottomRight.x, entityPosition.x + 1), 
            Math.max(bottomRight.y, entityPosition.y + 1)
        );
    }

    public void createGrid() {
        cells = new char[bottomRight.y - topLeft.y + 1][(bottomRight.x - topLeft.x + 2) >> 1];
    }

    public void insert(Vec2i entityPosition) {
        setCellDescriptor(entityPosition.x - topLeft.x, entityPosition.y - topLeft.y, 8);
        // this.prettyPrint();
        // System.out.println(entityPosition.x & 1);
    }

    private int getCellDescriptor(int x_coord, int y_coord) {
        return (cells[y_coord][x_coord >> 1] >> (4 * (x_coord & 1))) & 0xF;
    }

    private void setCellDescriptor(int x_coord, int y_coord, int cellDescriptor) {
        if ((cellDescriptor >> 4) != 0) {
            throw new RuntimeException("tf");
        }
        if ((x_coord & 1) == 1) {
            cells[y_coord][x_coord >> 1] = (char) ((cellDescriptor << 4) | (cells[y_coord][x_coord >> 1] & 0xF));
        } else {
            cells[y_coord][x_coord >> 1] = (char) (cellDescriptor | (cells[y_coord][x_coord >> 1] & 0xF0));
        }
    }

    public void updateNeighbours(Vec2i entityPosition) {
        // this.prettyPrint();
        int y_coord = entityPosition.y - topLeft.y;
        int x_coord = entityPosition.x - topLeft.x;
        int self_descriptor = getCellDescriptor(x_coord, y_coord);
        // System.out.println("self desc: " + self_descriptor + " => coords[" + x_coord + ", " + y_coord + "]");
        for(int i = -1; i < 2; ++i) {
            for(int j = -1; j < 2; ++j) {
                if (i==0 && j == 0) continue;
                int neighbour_descriptor = getCellDescriptor(x_coord + j, y_coord + i);
                // System.out.println("neighbour desc: " + neighbour_descriptor + " => coords[" + (x_coord + j) + ", " + (y_coord + i) + "]");
                
                if ((neighbour_descriptor >> 3) == 1) {
                    if ((self_descriptor & 3) == 3) {
                        self_descriptor = 0xB;
                    } else {
                        if ((self_descriptor & 3) != 0) {
                            self_descriptor |= 4;
                        } else {
                            self_descriptor++;
                        }
                    }
                } else {
                    if ((neighbour_descriptor & 3) == 3) {
                        neighbour_descriptor = 3;
                    } else if ((neighbour_descriptor & 3) == 2) {
                        neighbour_descriptor = 7;
                    } else {
                        neighbour_descriptor++;
                    }
                    setCellDescriptor(x_coord + j, y_coord + i, neighbour_descriptor);
                }
            }
        }
        setCellDescriptor(x_coord, y_coord, self_descriptor);
    }

    public void prettyPrint() {
        for (char[] yy : this.cells) {
            for (int x : yy) {
                System.out.print((x & 8) + " A: " + ((x & 4) >> 2) + " N: " + (x & 3)
                        + " | " + ((x >> 4) & 8) + " A:" + (((x >> 4) & 4) >> 2) + " N:" + ((x >> 4) & 3) + " | ");
            }
            System.out.println();
        }
    }
}
