//package com.makkajai.dev.challenge.ds.planar;
//
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import org.junit.jupiter.api.Test;
//
//class HalfByteGridTest {
//
//    @Test
//    void testConstructor() {
//        Grid2D grid2D = new Grid2D();
//
//        assertNotNull(grid2D);
//
//        char[][] cells = grid2D.getCells();
//        assertEquals(null, cells);
//
//        assertEquals(new Vec2i(0, 0), grid2D.getSize());
//    }
//
//    @Test
//    void testCreateGrid() {
//        Grid2D grid2D = new Grid2D();
//        grid2D.createGrid(10, 10);
//
//        char[][] cells = grid2D.getCells();
//        assertEquals(10, cells.length);
//        assertEquals(5, cells[0].length);
//
//        grid2D.createGrid(11, 11);
//
//        cells = grid2D.getCells();
//        assertEquals(11, cells.length);
//        assertEquals(6, cells[0].length);
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            grid2D.createGrid(-11, 12);
//        });
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            grid2D.createGrid(11, -12);
//        });
//    }
//
//    @Test
//    void testCreateGridVec2i() {
//        Grid2D grid2D = new Grid2D();
//        grid2D.createGrid(new Vec2i(10, 10));
//
//        char[][] cells = grid2D.getCells();
//        assertEquals(10, cells.length);
//        assertEquals(5, cells[0].length);
//
//        grid2D.createGrid(new Vec2i(11, 11));
//
//        cells = grid2D.getCells();
//        assertEquals(11, cells.length);
//        assertEquals(6, cells[0].length);
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            grid2D.createGrid(new Vec2i(-11, 12));
//        });
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            grid2D.createGrid(new Vec2i(11, -12));
//        });
//    }
//
//    @Test
//    void testGetAndSetCell() {
//        Grid2D grid2D = new Grid2D();
//        grid2D.createGrid(10, 8);
//
//        assertThrows(IndexOutOfBoundsException.class, () -> {
//            grid2D.getCell(10, 7);
//        });
//
//
//        assertThrows(IndexOutOfBoundsException.class, () -> {
//            grid2D.getCell(9, 8);
//        });
//
//        assertThrows(IndexOutOfBoundsException.class, () -> {
//            grid2D.getCell(-1, 7);
//        });
//
//        assertThrows(IndexOutOfBoundsException.class, () -> {
//            grid2D.getCell(9, -1);
//        });
//
//        assertDoesNotThrow(() -> {
//            grid2D.getCell(0, 0);
//        });
//
//        assertDoesNotThrow(() -> {
//            grid2D.getCell(9, 7);
//        });
//
//        for(int y = 0; y < grid2D.getSize().y; ++y) {
//            for (int x = 0; x < grid2D.getSize().x * 2; ++x) {
//                assertEquals(0, grid2D.getCell(x, y));
//            }
//        }
//
//        assertDoesNotThrow(() -> {
//            for (int y = 0; y < grid2D.getSize().y; ++y) {
//                for (int x = 0; x < grid2D.getSize().x * 2; ++x) {
//                    if (y % 3 == 0 && x % 4 == 0) {
//                        grid2D.setCell(x, y, 8);
//                    } else if (y % 2 == 0 && x % 2 == 0) {
//                        grid2D.setCell(x, y, 16);
//                    }
//                }
//            }
//        });
//
//        for (int y = 0; y < grid2D.getSize().y; ++y) {
//            for (int x = 0; x < grid2D.getSize().x * 2; ++x) {
//                if (y % 3 == 0 && x % 4 == 0) {
//                    assertEquals(8, grid2D.getCell(x, y));
//                } else if (y % 2 == 0 && x % 2 == 0) {
//                    assertEquals(0, grid2D.getCell(x, y));
//                } else {
//                     assertEquals(0, grid2D.getCell(x, y));
//                }
//            }
//        }
//
//        ///////////////////////////////
//        grid2D.createGrid(11, 8);
//
//        assertThrows(IndexOutOfBoundsException.class, () -> {
//            grid2D.getCell(12, 7);
//        });
//
//        assertThrows(IndexOutOfBoundsException.class, () -> {
//            grid2D.getCell(11, 8);
//        });
//
//        assertThrows(IndexOutOfBoundsException.class, () -> {
//            grid2D.getCell(-1, 7);
//        });
//
//        assertThrows(IndexOutOfBoundsException.class, () -> {
//            grid2D.getCell(11, -1);
//        });
//
//        assertDoesNotThrow(() -> {
//            grid2D.getCell(0, 0);
//        });
//
//        assertDoesNotThrow(() -> {
//            grid2D.getCell(11, 7);
//        });
//
//        for (int y = 0; y < grid2D.getSize().y; ++y) {
//            for (int x = 0; x < grid2D.getSize().x * 2; ++x) {
//                assertEquals(0, grid2D.getCell(x, y));
//            }
//        }
//
//        assertDoesNotThrow(() -> {
//            for (int y = 0; y < grid2D.getSize().y; ++y) {
//                for (int x = 0; x < grid2D.getSize().x * 2; ++x) {
//                    if (y % 3 == 0 && x % 4 == 0) {
//                        grid2D.setCell(x, y, 8);
//                    } else if (y % 2 == 0 && x % 2 == 0) {
//                        grid2D.setCell(x, y, 16);
//                    }
//                }
//            }
//        });
//
//        for (int y = 0; y < grid2D.getSize().y; ++y) {
//            for (int x = 0; x < grid2D.getSize().x * 2; ++x) {
//                if (y % 3 == 0 && x % 4 == 0) {
//                    assertEquals(8, grid2D.getCell(x, y));
//                } else if (y % 2 == 0 && x % 2 == 0) {
//                    assertEquals(0, grid2D.getCell(x, y));
//                } else {
//                    assertEquals(0, grid2D.getCell(x, y));
//                }
//            }
//        }
//    }
//
//    @Test
//    void testSetAndGetCellVec2i() {
//        Grid2D grid2D = new Grid2D();
//        grid2D.createGrid(new Vec2i(10, 8));
//
//        assertThrows(IndexOutOfBoundsException.class, () -> {
//            grid2D.getCell(new Vec2i(10, 7));
//        });
//
//
//        assertThrows(IndexOutOfBoundsException.class, () -> {
//            grid2D.getCell(new Vec2i(9, 8));
//        });
//
//        assertThrows(IndexOutOfBoundsException.class, () -> {
//            grid2D.getCell(new Vec2i(-1, 7));
//        });
//
//        assertThrows(IndexOutOfBoundsException.class, () -> {
//            grid2D.getCell(new Vec2i(9, -1));
//        });
//
//        assertDoesNotThrow(() -> {
//            grid2D.getCell(new Vec2i(0, 0));
//        });
//
//        assertDoesNotThrow(() -> {
//            grid2D.getCell(new Vec2i(9, 7));
//        });
//
//        for(int y = 0; y < grid2D.getSize().y; ++y) {
//            for (int x = 0; x < grid2D.getSize().x * 2; ++x) {
//                assertEquals(0, grid2D.getCell(new Vec2i(x, y)));
//            }
//        }
//
//        assertDoesNotThrow(() -> {
//            for (int y = 0; y < grid2D.getSize().y; ++y) {
//                for (int x = 0; x < grid2D.getSize().x * 2; ++x) {
//                    if (y % 3 == 0 && x % 4 == 0) {
//                        grid2D.setCell(new Vec2i(x, y), 8);
//                    } else if (y % 2 == 0 && x % 2 == 0) {
//                        grid2D.setCell(new Vec2i(x, y), 16);
//                    }
//                }
//            }
//        });
//
//        for (int y = 0; y < grid2D.getSize().y; ++y) {
//            for (int x = 0; x < grid2D.getSize().x * 2; ++x) {
//                if (y % 3 == 0 && x % 4 == 0) {
//                    assertEquals(8, grid2D.getCell(new Vec2i(x, y)));
//                } else if (y % 2 == 0 && x % 2 == 0) {
//                    assertEquals(0, grid2D.getCell(new Vec2i(x, y)));
//                } else {
//                     assertEquals(0, grid2D.getCell(new Vec2i(x, y)));
//                }
//            }
//        }
//
//        ///////////////////////////////
//        grid2D.createGrid(11, 8);
//
//        assertThrows(IndexOutOfBoundsException.class, () -> {
//            grid2D.getCell(new Vec2i(12, 7));
//        });
//
//        assertThrows(IndexOutOfBoundsException.class, () -> {
//            grid2D.getCell(new Vec2i(11, 8));
//        });
//
//        assertThrows(IndexOutOfBoundsException.class, () -> {
//            grid2D.getCell(new Vec2i(-1, 7));
//        });
//
//        assertThrows(IndexOutOfBoundsException.class, () -> {
//            grid2D.getCell(new Vec2i(11, -1));
//        });
//
//        assertDoesNotThrow(() -> {
//            grid2D.getCell(new Vec2i(0, 0));
//        });
//
//        assertDoesNotThrow(() -> {
//            grid2D.getCell(new Vec2i(11, 7));
//        });
//
//        for (int y = 0; y < grid2D.getSize().y; ++y) {
//            for (int x = 0; x < grid2D.getSize().x * 2; ++x) {
//                assertEquals(0, grid2D.getCell(new Vec2i(x, y)));
//            }
//        }
//
//        assertDoesNotThrow(() -> {
//            for (int y = 0; y < grid2D.getSize().y; ++y) {
//                for (int x = 0; x < grid2D.getSize().x * 2; ++x) {
//                    if (y % 3 == 0 && x % 4 == 0) {
//                        grid2D.setCell(new Vec2i(x, y), 8);
//                    } else if (y % 2 == 0 && x % 2 == 0) {
//                        grid2D.setCell(new Vec2i(x, y), 16);
//                    }
//                }
//            }
//        });
//
//        for (int y = 0; y < grid2D.getSize().y; ++y) {
//            for (int x = 0; x < grid2D.getSize().x * 2; ++x) {
//                if (y % 3 == 0 && x % 4 == 0) {
//                    assertEquals(8, grid2D.getCell(new Vec2i(x, y)));
//                } else if (y % 2 == 0 && x % 2 == 0) {
//                    assertEquals(0, grid2D.getCell(new Vec2i(x, y)));
//                } else {
//                    assertEquals(0, grid2D.getCell(new Vec2i(x, y)));
//                }
//            }
//        }
//    }
//
//    @Test
//    void clear() {
//        Grid2D grid2D = new Grid2D();
//        grid2D.createGrid(10, 8);
//        assertNotEquals(null, grid2D.getCells());
//        assertNotEquals(new Vec2i(0, 0), grid2D.getSize());
//        grid2D.clear();
//        assertEquals(null, grid2D.getCells());
//        assertEquals(new Vec2i(0, 0), grid2D.getSize());
//    }
//
//    @Test
//    void testPrint() {
//        Grid2D grid2D = new Grid2D();
//
//        assertDoesNotThrow(() -> {
//            grid2D.print(value -> value.toString());
//        });
//
//        grid2D.createGrid(2, 2);
//
//        assertDoesNotThrow(() -> {
//            grid2D.print(value -> value.toString());
//        });
//    }
//}
