package com.bf.aoc._2023.day10;

import com.bf.aoc.util.Direction;
import lombok.Getter;

import java.util.Set;

@Getter
public enum Pipe {
    NS('|', Direction.N, Direction.S),
    NE('L', Direction.N, Direction.E),
    NW('J', Direction.N, Direction.W),
    SE('F', Direction.S, Direction.E),
    SW('7', Direction.S, Direction.W),
    EW('-', Direction.E, Direction.W),
    X('.'),
    START('S', Direction.N, Direction.E, Direction.S, Direction.W);

    public static Pipe fromChar(char c) {
        for (Pipe pipe : Pipe.values()) {
            if (pipe.val == c)
                return pipe;
        }
        throw new IllegalArgumentException("" + c);
    }

    public static Pipe fromDirections(Direction d1, Direction d2) {
        for (Pipe pipe : Pipe.values()) {
            if (pipe.directions.contains(d1) && pipe.directions.contains(d2))
                return pipe;
        }
        throw new IllegalArgumentException("" + d1 + d2);
    }

    private final char val;
    private final Set<Direction> directions;

    Pipe(char val, Direction... directions) {
        this.val = val;
        this.directions = Set.of(directions);
    }

    public boolean connectsNorth() {
        return NS == this || NE == this || NW == this || START == this;
    }
}
