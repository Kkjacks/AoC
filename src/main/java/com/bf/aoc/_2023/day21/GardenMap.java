package com.bf.aoc._2023.day21;

import com.bf.aoc.util.Direction;
import com.bf.aoc.util.Point;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GardenMap {
    private static final Table<Long, Long, Character> garden = HashBasedTable.create();
    public static long rows, cols;
    private static final Set<Location> cache = new HashSet<>();
    public static Point start;

    public static Location init(List<String> lines) {
        long row = 0;
        for (String line : lines) {
            long col = 0;
            row++;
            for (char c : line.toCharArray()) {
                col++;
                if (c == 'S') {
                    start = new Point(row, col);
                    c = '.';
                }
                garden.put(row, col, c);
            }
        }
        rows = garden.rowKeySet().size();
        cols = garden.columnKeySet().size();

        return new Location(start);
    }

    private GardenMap() {}

    public static Set<Location> move(Location location, long steps) {
        return moveAllDirections(location, steps);
    }

    private static Set<Location> moveAllDirections(Location location, long steps) {
        Set<Location> newLocations = new HashSet<>();
        for (Direction direction : Direction.values()) {
            Location newLocation = move(location, direction);
            if (newLocation != null) {
                if (newLocation.getSteps() < steps) {
                    newLocations.addAll(moveAllDirections(newLocation, steps));
                } else {
                    newLocations.add(newLocation);
                }
            }
        }
        return newLocations;
    }

    private static Location move(Location location, Direction direction) {
        Location newLocation = location.move(direction);
        Character c = get(newLocation);
        /* This works but not fast enough
        if (c == null && StepRunner.PART == 2) {
            newLocation.loop(direction);
            c = get(newLocation);
        }
        */
        if (cache.contains(newLocation)) {
            return null;
        }

        if (c != null && c != '#') {
            cache.add(newLocation);
            if (StepRunner.PART == 2)
                markReached(newLocation);
            return newLocation;
        } else {
            return null;
        }
    }

    private static Character get(Location location) {
        return get(location.getPoint());
    }

    private static Character get(Point point) {
        return garden.get(point.row(), point.col());
    }

    private static void markReached(Location location) {
        garden.put(location.row(), location.col(), '*');
    }

    public static void resetCache() {
        cache.clear();
    }

    public static String print(Set<Location> locations) {
        Table<Long, Long, Character> garden = HashBasedTable.create(GardenMap.garden);
        garden.put(start.row(), start.col(), 'S');
        StringBuilder sb = new StringBuilder();
        Map<Point, Integer> points = new HashMap<>();
        for (Location location : locations) {
            if (location.getPoint().equals(start)) {
                garden.put(location.row(), location.col(), 'X');
            } else {
                if (points.containsKey(location.getPoint())) {
                    points.put(location.getPoint(), points.get(location.getPoint())+1);
                } else {
                    points.put(location.getPoint(), 1);
                }
                garden.put(location.row(), location.col(), Character.forDigit(points.get(location.getPoint()), 32));
            }
        }
        for (Map.Entry<Long, Map<Long, Character>> rows : garden.rowMap().entrySet()) {
            sb.append(rows.getValue().values().stream().map(Object::toString).reduce(String::concat).get()).append("\n");
        }
        return sb.toString();
    }
}
