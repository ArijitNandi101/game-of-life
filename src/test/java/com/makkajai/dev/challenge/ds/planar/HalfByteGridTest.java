package com.makkajai.dev.challenge.ds.planar;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class HalfByteGridTest {
   
    @Test
    void testConstructor() {
        HalfByteGrid halfByteGrid = new HalfByteGrid();

        assertNotNull(halfByteGrid);

        char[][] cells = halfByteGrid.getCells();
        assertEquals(null, cells);

        assertEquals(new Vec2i(0, 0), halfByteGrid.getSize());
    }

    @Test
    void testCreateGrid() {
        HalfByteGrid halfByteGrid = new HalfByteGrid();
        halfByteGrid.createGrid(10, 10);
        
        char[][] cells = halfByteGrid.getCells();
        assertEquals(10, cells.length);
        assertEquals(5, cells[0].length);

        halfByteGrid.createGrid(11, 11);

        cells = halfByteGrid.getCells();
        assertEquals(11, cells.length);
        assertEquals(6, cells[0].length);

        assertThrows(IllegalArgumentException.class, () -> {
            halfByteGrid.createGrid(-11, 12);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            halfByteGrid.createGrid(11, -12);
        });
    }

    @Test
    void testGetAndSetCell() {
        HalfByteGrid halfByteGrid = new HalfByteGrid();
        halfByteGrid.createGrid(10, 8);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            halfByteGrid.getCell(10, 7);
        });


        assertThrows(IndexOutOfBoundsException.class, () -> {
            halfByteGrid.getCell(9, 8);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            halfByteGrid.getCell(-1, 7);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            halfByteGrid.getCell(9, -1);
        });

        assertDoesNotThrow(() -> {
            halfByteGrid.getCell(0, 0);
        });

        assertDoesNotThrow(() -> {
            halfByteGrid.getCell(9, 7);
        });

        for(int y = 0; y < halfByteGrid.getSize().y; ++y) {
            for (int x = 0; x < halfByteGrid.getSize().x * 2; ++x) {
                assertEquals(0, halfByteGrid.getCell(x, y));
            }
        }

        assertDoesNotThrow(() -> {
            for (int y = 0; y < halfByteGrid.getSize().y; ++y) {
                for (int x = 0; x < halfByteGrid.getSize().x * 2; ++x) {
                    if (y % 3 == 0 && x % 4 == 0) {
                        halfByteGrid.setCell(x, y, 8);
                    } else if (y % 2 == 0 && x % 2 == 0) {
                        halfByteGrid.setCell(x, y, 16);
                    }
                }
            }
        });

        for (int y = 0; y < halfByteGrid.getSize().y; ++y) {
            for (int x = 0; x < halfByteGrid.getSize().x * 2; ++x) {
                if (y % 3 == 0 && x % 4 == 0) {
                    assertEquals(8, halfByteGrid.getCell(x, y));
                } else if (y % 2 == 0 && x % 2 == 0) {
                    assertEquals(0, halfByteGrid.getCell(x, y));
                } else {
                     assertEquals(0, halfByteGrid.getCell(x, y));
                }
            }
        }

        ///////////////////////////////
        halfByteGrid.createGrid(11, 8);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            halfByteGrid.getCell(12, 7);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            halfByteGrid.getCell(11, 8);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            halfByteGrid.getCell(-1, 7);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            halfByteGrid.getCell(11, -1);
        });

        assertDoesNotThrow(() -> {
            halfByteGrid.getCell(0, 0);
        });

        assertDoesNotThrow(() -> {
            halfByteGrid.getCell(11, 7);
        });

        for (int y = 0; y < halfByteGrid.getSize().y; ++y) {
            for (int x = 0; x < halfByteGrid.getSize().x * 2; ++x) {
                assertEquals(0, halfByteGrid.getCell(x, y));
            }
        }

        assertDoesNotThrow(() -> {
            for (int y = 0; y < halfByteGrid.getSize().y; ++y) {
                for (int x = 0; x < halfByteGrid.getSize().x * 2; ++x) {
                    if (y % 3 == 0 && x % 4 == 0) {
                        halfByteGrid.setCell(x, y, 8);
                    } else if (y % 2 == 0 && x % 2 == 0) {
                        halfByteGrid.setCell(x, y, 16);
                    }
                }
            }
        });

        for (int y = 0; y < halfByteGrid.getSize().y; ++y) {
            for (int x = 0; x < halfByteGrid.getSize().x * 2; ++x) {
                if (y % 3 == 0 && x % 4 == 0) {
                    assertEquals(8, halfByteGrid.getCell(x, y));
                } else if (y % 2 == 0 && x % 2 == 0) {
                    assertEquals(0, halfByteGrid.getCell(x, y));
                } else {
                    assertEquals(0, halfByteGrid.getCell(x, y));
                }
            }
        }
    }

    @Test
    void clear() {
        HalfByteGrid halfByteGrid = new HalfByteGrid();
        halfByteGrid.createGrid(10, 8);
        assertNotEquals(null, halfByteGrid.getCells());
        halfByteGrid.clear();
        assertEquals(null, halfByteGrid.getCells());
    }

    @Test
    void testPrint() {
        HalfByteGrid halfByteGrid = new HalfByteGrid();

        assertDoesNotThrow(() -> {
            halfByteGrid.print(value -> value.toString());
        });

        halfByteGrid.createGrid(2, 2);

        assertDoesNotThrow(() -> {
            halfByteGrid.print(value -> value.toString());
        });
    }
}
