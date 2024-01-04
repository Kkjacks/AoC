package com.bf.aoc.day22;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BrickStacker {
    private static final Pattern pattern = Pattern.compile("(\\d+),(\\d+),(\\d+)~(\\d+),(\\d+),(\\d+)");
    private final List<Table<Integer, Integer, Brick>> tower = new ArrayList<>();
    private Integer brickName = 0;

    public List<Brick> stackBricks(List<String> lines) {
        List<Brick> bricks = createOrderedBricks(lines);
        // expand tower to max z so we don't have to worry about expanding on the fly
        for (int i = 0; i < bricks.get(bricks.size()-1).end().z(); i++)
            tower.add(HashBasedTable.create());

        // start stacking
        for (Brick brick : bricks) {
            while (brick.start().z() > 1) {
                Set<Brick> bricksBelow = getBricksBelow(brick);
                if (bricksBelow.isEmpty()) {
                    brick.drop();
                } else {
                    brick.connectSupports(bricksBelow);
                    break;
                }
            }
            for (Position position : brick.positions()) {
                getLevel(position).put(position.x(), position.y(), brick);
            }
        }
        Collections.reverse(bricks);
        return bricks;
    }

    private Set<Brick> getBricksBelow(Brick brick) {
        return brick.positionsBelow().stream()
                .map(this::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private Brick get(Position position) {
        return getLevel(position).get(position.x(), position.y());
    }

    private Table<Integer, Integer, Brick> getLevel(Position position) {
        return tower.get(position.z());
    }

    private List<Brick> createOrderedBricks(List<String> lines) {
        List<Brick> bricks = new ArrayList<>();
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.matches()) throw new IllegalStateException("Invalid input");
            Position start = new Position(matcher.group(1), matcher.group(2), matcher.group(3));
            Position end = new Position(matcher.group(4), matcher.group(5), matcher.group(6));
            if (start.z() > end.z()) throw new IllegalStateException("Input has Z index reversed");
            bricks.add(new Brick(nextName(), start, end));
        }
        Collections.sort(bricks);
        return bricks;
    }

    private String nextName() {
        String name = brickName.toString();
        brickName++;
        return name;
    }
}
