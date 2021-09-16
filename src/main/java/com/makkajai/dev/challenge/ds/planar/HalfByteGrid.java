package com.makkajai.dev.challenge.ds.planar;

import java.util.function.Function;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * This is a grid of cells where each cell is only 4 bit in size. This can be
 * used to store 4 bit or less sized data. 
 */
public class HalfByteGrid {

    /**
     * Contains the current size of the grid
     */
    @Getter(AccessLevel.PACKAGE)
    private Vec2i size = new Vec2i();

    /**
     * A 0 index 2D grid of half byte cells internally represented as a 2D array of chars.
     */
    @Getter(AccessLevel.PACKAGE)
    private char[][] cells; 

    public void clear() {
        cells = null;
    }

    
    /**
     * Creates an empty (0 initialized) grid of the provided size.
     * 
     * @param x_size the size of the grid in the x-axis
     * @param y_size the size of the grid in the y-axis
     * @throws IllegalArgumentException in cases the x or y axis size provided
     * is negative or zero.
     */
    public void createGrid(int x_size, int y_size) throws IllegalArgumentException {
        if (x_size <= 0 || y_size <= 0) {
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

    /**
     * @param x_coord the x coordinate (inner array) of the cell in the grid
     * @param y_coord the y coordinate (outer array) of the cell in the grid
     * @return the 4 bits of a single cell in the grid as an int
     */
    public int getCell(int x_coord, int y_coord) {
        return (cells[y_coord][x_coord >> 1] >> (4 * (x_coord & 1))) & 0xF;
    }

    /**
     * @param x_coord the x coordinate (inner array) of the cell in the grid
     * @param y_coord the y coordinate (outer array) of the cell in the grid
     * @param value a integer whose only lowest 4 bits will be set as the value of the cell
     */
    public void setCell(int x_coord, int y_coord, int value) {
        if ((x_coord & 1) == 1) {
            cells[y_coord][x_coord >> 1] = (char) ((value << 4) | (cells[y_coord][x_coord >> 1] & 0xF));
        } else {
            cells[y_coord][x_coord >> 1] = (char) ((value & 0xF) | (cells[y_coord][x_coord >> 1] & 0xF0));
        }
    }

    /**
     * Prints the grid as a rectangle according to the pattern provided.
     * 
     * @param formatter a {@link java.util.function.Function} that takes in the
     *              4 bit value of each cell in the grid and formats it into a {@link String}
     */
    public void print(Function<Integer, String> formatter) {
        for (int y_coord = 0; y_coord < size.y; ++y_coord) {
            for (int x_coord = 0; x_coord < size.x * 2; ++x_coord) {
                System.out.print(formatter.apply(getCell(x_coord, y_coord)) + " | ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
