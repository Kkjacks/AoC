package com.bf.aoc._2023.day10;

import com.bf.aoc.util.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class Coordinate {
    private static final Logger log = LoggerFactory.getLogger("");
    final int row, col;
    Pipe pipe;
    private final Set<Direction> openConnections = new HashSet<>();
    private boolean included;

    Coordinate(int row, int col, Pipe pipe) {
        this.row = row;
        this.col = col;
        this.pipe = pipe;
        openConnections.addAll(pipe.getDirections());
    }

    public Coordinate findNextStep(Pipes pipes) {
        for (Direction direction : openConnections) {
            Coordinate nextCoordinate = move(pipes, direction);
            if (nextCoordinate != null) {
                openConnections.remove(direction);
                nextCoordinate.openConnections.remove(direction.from());
                nextCoordinate.included = true;
                return nextCoordinate;
            }
        }
        return null;
    }

    public Coordinate move(Pipes pipes, Direction direction) {
        Coordinate nextCoordinate;
        switch (direction) {
            case N -> nextCoordinate = pipes.get(row-1, col);
            case S -> nextCoordinate = pipes.get(row+1, col);
            case E -> nextCoordinate = pipes.get(row, col+1);
            case W -> nextCoordinate = pipes.get(row, col-1);
            default -> throw new IllegalStateException();
        }
        if (nextCoordinate != null && nextCoordinate.supports(direction)) {
            openConnections.remove(direction);
            nextCoordinate.openConnections.remove(direction.from());
            //log.info("{} allowed returning {}", direction, nextCoordinate);
            return nextCoordinate;
        } else {
            //log.info("Move {} not allowed", direction);
            return null;
        }
    }

    public void clean() {
        if (!included)
            pipe = Pipe.X;
    }

    public boolean supports(Direction direction) {
        return openConnections.contains(direction.from());
    }

    public boolean isPipe() {
        return Pipe.X != pipe;
    }

    @Override
    public String toString() {
        return "" + pipe.getVal();
    }

    public String printLocation() {
        return "(" + row + "," + col + ")";
    }
}
