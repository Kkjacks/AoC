package com.bf.aoc.util;

public record Point (long row, long col){
    public Point move(Direction direction) {
        return new Point(row + direction.rowOffset(), col + direction.colOffset());
    }
}
