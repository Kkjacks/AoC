package com.bf.aoc.day23;

import com.bf.aoc.util.Direction;
import com.bf.aoc.util.Point;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Trail {
    private final Table<Integer, Integer, Location> trail = HashBasedTable.create();
    private Location start, end;
    private final int rows, cols;

    public Trail(List<String> lines) {
        int row = 1;
        for (String line : lines) {
            int col = 1;
            for (char c : line.toCharArray()) {
                Location location;
                if (row == 1 && c == '.') {
                    start = location = new Location(row, col, 'S');
                } else if (row == lines.size() && c == '.') {
                    end = location = new Location(row, col, 'E');
                } else {
                    location = new Location(row, col, c);
                }
               trail.put(row, col, location);
               col++;
            }
            row++;
        }
        rows = trail.rowKeySet().size();
        cols = trail.columnKeySet().size();
    }

    public List<Location> walk() {
        List<Location> path = new ArrayList<>();
        path.add(start);
        List<List<Location>> paths = step(path);
        return paths.stream().max(Comparator.comparingInt(List::size)).orElseThrow();
    }

    // all these are used to map intersections (could be consolidated)
    private final Set<Intersection> allIntersections = new HashSet<>();
    private final Map<Location, Intersection> intersectionCache = new HashMap<>();
    private final Set<Point> intersectionPoints = new HashSet<>();
    private final List<Intersection> queue = new LinkedList<>();

    // overly messy
    public Intersection mapIntersections() {
        start.markComplete();
        Intersection firstIntersection = new Intersection(start, Collections.singletonList(step(start, Direction.S)));
        allIntersections.add(firstIntersection);
        List<Location> path = new ArrayList<>();
        queue.add(firstIntersection);
        while (!queue.isEmpty()) {
            Intersection currentIntersection = queue.removeFirst();
            while (currentIntersection.hasUnmappedForks()) {
                Location forkToMap = currentIntersection.nextUnmappedFork();
                path.clear();
                Intersection nextIntersection = findNextIntersection(forkToMap, path);
                if (nextIntersection != null) {
                    // store path back to handle 4 way intersections
                    intersectionCache.put(path.get(path.size() - 2), currentIntersection);
                } else {
                    // looped back to connection in reverse
                    nextIntersection = intersectionCache.get(forkToMap);
                    currentIntersection.addReverseConnection(nextIntersection);
                    continue;
                }
                intersectionPoints.add(nextIntersection.point());
                if (!allIntersections.add(nextIntersection)) {
                    for (Intersection x : allIntersections) {
                        if (x.equals(nextIntersection)) {
                            nextIntersection = x;
                        }
                    }
                }
                currentIntersection.addConnection(nextIntersection, path.size()+1);
                if (!nextIntersection.end())
                    queue.add(nextIntersection);
            }
        }
        return firstIntersection;
    }

    private Intersection findNextIntersection(Location current, List<Location> path) {
        List<Location> nextSteps = new ArrayList<>();
        Location stop = null;
        for (Direction direction : Direction.values()) {
            Location location = step(current, direction);
            if (location != null) {
                if (location.ground().canStep(direction)) {
                    nextSteps.add(location);
                } else if (!path.isEmpty() && intersectionPoints.contains(location.point())) {
                    stop = location;
                }
            }
        }
        if (nextSteps.isEmpty()) {
            if (current == end) {
                return new Intersection(current, nextSteps).markEnd();
            } else if (stop == null) {
                // connection already mapped from other direction
                return null;
            }
            path.add(stop);
            return new Intersection(stop, nextSteps);
        } else {
            current.markComplete();
            if (nextSteps.size() == 1) {
                // only 1 way to go so keep going
                Location next = nextSteps.get(0);
                path.add(next);
                return findNextIntersection(next, path);
            } else {
                // path forks return new intersection
                return new Intersection(current, nextSteps);
            }
        }
    }

    private List<List<Location>> step(List<Location> path) {
        Location current = path.getLast();
        List<List<Location>> paths = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            Location location = step(current, direction);
            if (location != null && !path.contains(location) && location.ground().canStep(direction)) {
                if (location == end) {
                    paths.add(path);
                } else {
                    List<Location> newPath = new ArrayList<>(path);
                    newPath.add(location);
                    paths.addAll(step(newPath));
                }
            }
        }
        return paths;
    }

    private Location step(Location location, Direction direction) {
        return step(location.point(), direction);
    }

    private Location step(Point point, Direction direction) {
        Point next = point.move(direction);
        return trail.get((int)next.row(), (int)next.col());
    }

    public String printPath(List<Location> path) {
        StringBuilder sb = new StringBuilder();
        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {
                Location location = get(r,c);
                if (path.contains(location))
                    sb.append("O");
                else
                    sb.append(location.ground());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Location get(int r, int c) {
        return trail.get(r, c);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {
                sb.append(trail.get(r,c));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
