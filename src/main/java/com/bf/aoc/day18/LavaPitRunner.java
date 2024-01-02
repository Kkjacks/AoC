package com.bf.aoc.day18;

import com.bf.aoc.Runner;
import com.bf.aoc.util.Direction;
import com.bf.aoc.util.Point;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LavaPitRunner extends Runner {
    static {
        DAY=18; TEST=false; PART=2;
    }
    private static final Pattern pattern = Pattern.compile("(\\w) (\\d+) \\((#\\w{5})(\\d)\\)");

    public static void main(String[] args) {
        LavaPitRunner r = new LavaPitRunner();
        log.info("Result: {}", isPart1() ? r.part1(input()) : r.part2(input()));
    }

    @Override
    public long part1(List<String> lines) {
        List<Pair<Direction, Long>> digPath = new ArrayList<>();
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.matches()) throw new IllegalStateException();
            digPath.add(Pair.of(Direction.fromVerticalInput(matcher.group(1)), Long.parseLong(matcher.group(2))));
        }
        LavaPit pit = new LavaPit(digPath);
        log.info("{}", pit);
        pit.dig();
        log.info("{}", pit);
        return pit.sum();
    }

    @Override
    public long part2(List<String> lines) {
        List<Pair<Direction, Long>> digPath = new ArrayList<>();
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.matches()) throw new IllegalStateException();
            long hexDigits = Long.decode(matcher.group(3));
            Direction direction;
            switch (matcher.group(4)) {
                case "0" -> direction = Direction.E;
                case "1" -> direction = Direction.S;
                case "2" -> direction = Direction.W;
                case "3" -> direction = Direction.N;
                default -> throw new IllegalArgumentException();
            }
            digPath.add(Pair.of(direction, hexDigits));
        }

        // Using LavaPit's solution results in OOM.  Instead, use shoelace algorithm
        // then add 1/2 the perimeter plus 1 (shoelace accounts for other 1/2 as our
        // line is included in the area rather than an outline).
        long perimeter = 0;
        long r=0;
        long c=0;
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        for (Pair<Direction, Long> dig : digPath) {
            Direction direction = dig.getLeft();
            long steps = dig.getRight();
            perimeter += steps;
            r += (direction.rowOffset() * steps);
            c += (direction.colOffset() * steps);
            points.add(new Point(r, c));
        }

        long area = shoelace(points);

        return area + (perimeter/2) + 1;
    }

    // https://www.101computing.net/the-shoelace-algorithm/
    private long shoelace(List<Point> points) {
        points.add(points.get(0));
        Point first = points.remove(0);
        Point second;
        long result = 0;
        while (!points.isEmpty()) {
            second = points.remove(0);
            result += (first.row() * second.col()) - (first.col() * second.row());
            first = second;
        }
        return Math.abs(result / 2);
    }

}
