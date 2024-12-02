package com.bf.aoc._2023.day11;

import lombok.Getter;

public class Space {
    private static int GALAXY_COUNT = 1;
    private static String SPACE = ".";
    public static Space empty(int row, int col) {
        return new Space(SPACE, row, col);
    }

    public static Space galaxy(int row, int col) {
        return new Space("" + GALAXY_COUNT++, row, col);
    }

    @Getter private final String name;
    @Getter private final int row, col;
    @Getter private int rowSize = 1;
    @Getter private int colSize = 1;

    private Space(String name, int row, int col) {
        this.name = name;
        this.row = row;
        this.col = col;
    }

    public boolean isSpace() {
        return SPACE.equals(name);
    }

    public boolean isGalaxy() {
        return !isSpace();
    }

    public void expandRowOfEmptySpace() {
        if (GalaxyMap.solution == 1)
            rowSize += 1;
        else
            rowSize = 1000000;
    }

    public void expandColumnOfEmptySpace() {
        if (GalaxyMap.solution == 1)
            colSize += 1;
        else
            colSize = 1000000;
    }

    @Override
    public String toString() {
        return name;
    }
}
