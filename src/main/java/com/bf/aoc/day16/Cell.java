package com.bf.aoc.day16;

import com.bf.aoc.day10.Direction;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class Cell {
    private final char type;
    @Getter boolean energized;
    private final Set<Direction> directions = new HashSet<>();

    public Cell(char type) {
        this.type = type;
    }

    public void reset() {
        energized=false;
        directions.clear();
    }

    public Beam moveBeam(Beam beam) {
        Direction direction = beam.direction;
        directions.add(direction);
        switch (type) {
            case '.' -> {
                return beam.move(direction);
            }
            case '/' -> {
                switch (direction) {
                    case N -> {
                        return beam.move(Direction.E);
                    }
                    case S -> {
                        return beam.move(Direction.W);
                    }
                    case E -> {
                        return beam.move(Direction.N);
                    }
                    case W -> {
                        return beam.move(Direction.S);
                    }
                }
            }
            case '\\' -> {
                switch (direction) {
                    case N -> {
                        return beam.move(Direction.W);
                    }
                    case S -> {
                        return beam.move(Direction.E);
                    }
                    case E -> {
                        return beam.move(Direction.S);
                    }
                    case W -> {
                        return beam.move(Direction.N);
                    }
                }
            }
            case '|' -> {
                switch (direction) {
                    case N, S -> {
                        return beam.move(direction);
                    }
                    case E, W -> {
                        return beam.split(Direction.N, Direction.S);
                    }
                }
            }
            case '-' -> {
                switch (direction) {
                    case N, S -> {
                        return beam.split(Direction.E, Direction.W);
                    }
                    case E, W -> {
                        return beam.move(direction);
                    }
                }
            }
        }
        throw new IllegalArgumentException();
    }

    public boolean isLoop(Direction direction) {
        return directions.contains(direction);
    }
}
