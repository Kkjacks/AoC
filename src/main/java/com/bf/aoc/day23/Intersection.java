package com.bf.aoc.day23;

import com.bf.aoc.util.Point;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Accessors(fluent = true)
public class Intersection {
    @Getter private final Point point;
    private final Map<Intersection, Integer> connectionSteps = new HashMap<>();
    private final List<Location> unmappedForks;
    @Getter @Setter
    private boolean visited;
    @Getter private boolean end;

    public Intersection(Location location, List<Location> unmappedForks) {
        this.point = location.point();
        this.unmappedForks = new ArrayList<>(unmappedForks);
    }

    public Collection<Intersection> connections() {
        return connectionSteps.keySet();
    }

    public void addConnection(Intersection connection, int steps) {
        connectionSteps.put(connection, steps);
        connection.addReverseConnection(this);
    }

    public void addReverseConnection(Intersection connection) {
        connectionSteps.put(connection, connection.getSteps(this));
    }

    public boolean hasUnmappedForks() {
        return !unmappedForks.isEmpty();
    }

    public Location nextUnmappedFork() {
        return unmappedForks.remove(0);
    }

    public Intersection markEnd() {
        end = true;
        return this;
    }

    public int getSteps(Intersection next) {
        return connectionSteps.get(next);
    }

    public boolean equals(int x, int y) {
        return point.row() == x && point.col() == y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Intersection that)) return false;
        return Objects.equals(point, that.point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point);
    }

    @Override
    public String toString() {
        return point().toString();
    }
}
