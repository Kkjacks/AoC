package com.bf.aoc.day23;

import com.bf.aoc.util.Point;
import lombok.Getter;
import lombok.experimental.Accessors;

import javax.annotation.Nonnull;

@Getter
@Accessors(fluent = true)
public class Location {
    private final Point point;
    private Ground ground;

    Location(long row, long col, char c) {
        point = new Point(row, col);
        ground = Ground.from(c);
    }

    public void markComplete() {
        ground = Ground.DONE;
    }

    @Nonnull
    public Ground ground() {
        return ground;
    }

    @Override
    public String toString() {
        return point.toString();
    }

    public boolean equals(int x, int y) {
        return point.row() == x && point.col() == y;
    }
}
