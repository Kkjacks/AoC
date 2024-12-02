package com.bf.aoc._2023.day22;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Accessors(fluent = true)
public class Brick implements Comparable<Brick> {
    private String name;
    private Position start, end;
    private Set<Brick> bricksAbove = new HashSet<>();
    private Set<Brick> bricksBelow = new HashSet<>();

    public Brick(String name, Position start, Position end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public void connectSupports(Set<Brick> supports) {
        bricksBelow.addAll(supports);
        supports.forEach(b -> b.connectAbove(this));
    }

    private void connectAbove(Brick brick) {
        bricksAbove.add(brick);
    }

    public boolean canDestroy() {
        for (Brick brick : bricksAbove) {
            if (brick.bricksBelow().stream().noneMatch(b -> b != this)) {
                return false;
            }
        }
        return true;
    }

    public long countDamage() {
        Set<Brick> damagedBricks = new HashSet<>();
        for (Brick brick : bricksAbove) {
            if (brick.isSingleSupport()) {
                damagedBricks.add(brick);
                brick.addStacksAbove(damagedBricks);
            }
        }
        return damagedBricks.size();
    }

    private boolean isSingleSupport() {
        return bricksBelow().size() == 1;
    }

    private void addStacksAbove(Set<Brick> damagedBricks) {
        Set<Brick> recurse = new HashSet<>();
        for (Brick brick : bricksAbove) {
            Set<Brick> below = brick.bricksBelow();
            below.removeAll(damagedBricks);
            if (below.isEmpty()) {
                damagedBricks.add(brick);
                recurse.add(brick);
            }
        }

        for (Brick brick : recurse) {
            brick.addStacksAbove(damagedBricks);
        }
    }

    private Set<Brick> bricksBelow() {
        return new HashSet<>(bricksBelow);
    }

    public List<Position> positionsBelow() {
        if (isVertical()) {
            Position position = start.z() > end.z() ? end : start;
            return Collections.singletonList(position.below());
        }
        return positions().stream().map(Position::below).collect(Collectors.toList());
    }

    public List<Position> positions() {
        List<Position> positions = new ArrayList<>();
        if (isVertical()) {
            for (int Z = start.z(); Z <= end().z(); Z++)
                positions.add(new Position(start.x(), start.y(), Z));
        } else if (start.x() != end.x()) {
            for (int X = start.x(); X <= end.x(); X++)
                positions.add(new Position(X, start.y(), start.z()));
        } else {
            for (int Y = start.y(); Y <= end.y(); Y++)
                positions.add(new Position(start.x(), Y, start.z()));
        }
        return positions;
    }

    public boolean isVertical() {
        return start.z() != end.z()
                // single block
                || (start.x() == end.x() && start.y() == end.y());
    }

    public void drop() {
        start.drop();
        end.drop();
    }

    @Override
    public int compareTo(Brick o) {
        return Integer.compare(start().z(), o.start.z());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Brick brick)) return false;
        return Objects.equals(name, brick.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
