package com.makkajai.dev.challenge.ds.planar;

import java.util.function.Function;

import lombok.AccessLevel;
import lombok.Getter;

public class HalfByteGrid {

    @Getter(AccessLevel.PACKAGE)
    private Vec2i size = new Vec2i();

    @Getter(AccessLevel.PACKAGE)
    private char[][] cells; 

    public void clear() {
        cells = null;
    }

    public void createGrid(int x_size, int y_size) {
        if (x_size < 0 || y_size < 0) {
            throw new IllegalArgumentException(String.format(
                "Grid size can not be negative. Found: x_size: [%d], y_size: [%d]", 
                x_size, 
                y_size
            ));
        }
        size.x = (x_size + 1) >> 1;
        size.y = y_size;
        cells = new char[size.y][size.x];
    }

    public int getCell(int x_coord, int y_coord) {
        return (cells[y_coord][x_coord >> 1] >> (4 * (x_coord & 1))) & 0xF;
    }

    public void setCell(int x_coord, int y_coord, int cellDescriptor) {
        if ((x_coord & 1) == 1) {
            cells[y_coord][x_coord >> 1] = (char) ((cellDescriptor << 4) | (cells[y_coord][x_coord >> 1] & 0xF));
        } else {
            cells[y_coord][x_coord >> 1] = (char) ((cellDescriptor & 0xF) | (cells[y_coord][x_coord >> 1] & 0xF0));
        }
    }

    public void print(Function<Integer, String> prettyFormatter) {
        for (int y_coord = 0; y_coord < size.y; ++y_coord) {
            for (int x_coord = 0; x_coord < size.x * 2; ++x_coord) {
                System.out.print(prettyFormatter.apply(getCell(x_coord, y_coord)) + " | ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
