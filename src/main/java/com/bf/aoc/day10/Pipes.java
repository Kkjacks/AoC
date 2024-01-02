package com.bf.aoc.day10;

import com.bf.aoc.util.Direction;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Pipes {
    private static final Logger log = LoggerFactory.getLogger("");
    @Getter private final Table<Integer, Integer, Coordinate> map = HashBasedTable.create();
    @Getter private Coordinate start;
    private Coordinate current;

    public Pipes(List<String> lines) {
        int row = -1;
        for (String line : lines) {
            int col = -1;
            row++;
            for (char c : line.toCharArray()) {
                col++;
                Pipe pipe = Pipe.fromChar(c);
                Coordinate coordinate = new Coordinate(row, col, pipe);
                map.put(row, col, coordinate);
                if (Pipe.START == pipe) {
                    start = coordinate;
                    current = start;
                }
            }
        }
    }

    public List<Coordinate> findPath() {
        List<Coordinate> path = new ArrayList<>();
        current = current.findNextStep(this);
        while (current.pipe != Pipe.START) {
            path.add(current);
            //log.info("{}", path);
            current = current.findNextStep(this);
        }
        return path;
    }

    public void convertStartToPipe() {
        int row = start.row;
        int col = start.col;
        Direction direction1 = null;
        Direction direction2 = null;
        for (Direction direction : Direction.values()) {
            switch (direction) {
                case N -> {
                    if (isConnectingPipe(get(row-1, col), Direction.N))
                        if (direction1 == null) direction1 = direction; else direction2 = direction;
                }
                case S -> {
                    if (isConnectingPipe(get(row+1, col), Direction.S))
                        if (direction1 == null) direction1 = direction; else direction2 = direction;
                }
                case E -> {
                    if (isConnectingPipe(get(row, col+1), Direction.E))
                        if (direction1 == null) direction1 = direction; else direction2 = direction;
                }
                case W -> {
                    if (isConnectingPipe(get(row, col-1), Direction.W))
                        if (direction1 == null) direction1 = direction; else direction2 = direction;
                }
            }
            if (direction2 != null)
                break;
        }
        Pipe newStartPipe = Pipe.fromDirections(direction1, direction2);
        log.info("Converting start to '{}'", newStartPipe);
        start.pipe = newStartPipe;
    }

    private boolean isConnectingPipe(Coordinate coordinate, Direction direction) {
        return coordinate != null && coordinate.pipe.getDirections().contains(direction.from());
    }

    public int countInsideCoordinates() {
        int maxCol = map.row(0).size() - 1;
        int count = 0;
        boolean inLoop = false;
        for (Coordinate value : map.values()) {
            if (value.isPipe()) {
                if (value.pipe.connectsNorth()) {
                    inLoop = !inLoop;
                }
            } else {
                if (inLoop) {
                    log.info("Found: {}", value.printLocation());
                    count++;
                }
            }
            if (value.col == maxCol)
                inLoop = false;
        }
        return count;
    }

    public Coordinate get(int row, int col) {
        return map.get(row, col);
    }

    public String toString() {
        String result = "Start: " + start.printLocation() + "\n";
        for (Map.Entry<Integer, Map<Integer, Coordinate>> rows : map.rowMap().entrySet()) {
            result += rows.getValue().values().stream().map(Coordinate::toString).reduce(String::concat).get() + "\n";
        }
        return result;
    }

}
