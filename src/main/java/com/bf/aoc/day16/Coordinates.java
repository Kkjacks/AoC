package com.bf.aoc.day16;

import com.bf.aoc.day10.Direction;

public class Coordinates {
    int row, col;

    public Coordinates(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void move(Direction direction) {
        switch (direction) {
            case N -> row--;
            case S -> row++;
            case E -> col++;
            case W -> col--;
        }
    }

    public Coordinates split(Direction direction) {
        switch (direction) {
            case N -> {
                return new Coordinates(row-1, col);
            }
            case S -> {
                return new Coordinates(row+1, col);
            }
            case E -> {
                return new Coordinates(row, col+1);
            }
            case W -> {
                return new Coordinates(row, col-1);
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return "[" + row + "," + col + "]";
    }
}
