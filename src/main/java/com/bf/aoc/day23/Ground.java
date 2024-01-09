package com.bf.aoc.day23;

import com.bf.aoc.util.Direction;

import java.util.Arrays;

public enum Ground {
    PATH('.'),
    DONE('O'),
    TREE('#'),
    HILL_NORTH('^', Direction.N),
    HILL_SOUTH('v', Direction.S),
    HILL_EAST('>', Direction.E),
    HILL_WEST('<', Direction.W),
    START('S'),
    END('E');

    public static Ground from(char c) {
        return Arrays.stream(values()).filter(l -> l.c == c).findFirst().orElseThrow();
    }

    private final char c;
    private final Direction direction;

    Ground(char c) {
        this(c, null);
    }

    Ground(char c, Direction direction) {
        this.c = c;
        this.direction = direction;
    }

    public boolean canStep(Direction direction) {
        return TREE != this && DONE != this
                && (WalkRunner.PART == 2 || this.direction == null || this.direction != direction.from());
    }

    @Override
    public String toString() {
        return "" + c;
    }
}
