package com.bf.aoc._2023.day24;

import com.bf.aoc._2023.Runner2023;
import org.apache.commons.lang3.DoubleRange;
import org.apache.commons.lang3.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

public class HailRunner extends Runner2023 {
    static {
        DAY=24; TEST=false; PART=2;
    }
    private static final Pattern pattern = Pattern.compile("(\\d+), +(\\d+), +(\\d+) @ +(-?\\d+), +(-?\\d+), +(-?\\d+)");

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        HailRunner r = new HailRunner();
        log.info("Result: {}", isPart1() ? r.part1(input()) : r.part2(input()));
        long endTime = System.currentTimeMillis();
        log.info("{} sec", format("%5.3f", (endTime - startTime) / 1000f));
    }

    @Override
    public long part1(List<String> lines) {
        List<Hailstone> hailstones = createHail(lines);
        Range<Double> range = DoubleRange.of(min(), max());

        long count = 0;
        for (int i = 0; i < hailstones.size(); i++) {
            Hailstone h1 = hailstones.get(i);
            for (int j = i + 1; j < hailstones.size(); j++) {
                Hailstone h2 = hailstones.get(j);
                double x = (h2.b() - h1.b()) / (h1.a() - h2.a());
                double y = h1.a() * x + h1.b();
                boolean inBounds = range.contains(x) && range.contains(y);
                boolean futureOne = Math.signum(x - h1.x()) == Math.signum(h1.vx());
                boolean futureTwo = Math.signum(x - h2.x()) == Math.signum(h2.vx());
                if (inBounds && futureOne && futureTwo) {
                    log.info("{} and {} are INSIDE at x={}, y={}", h1.name(), h2.name(), format("%.03f", x), format("%.03f", y));
                    count++;
                } else if (Double.isInfinite(x) || Double.isInfinite(y)) {
                    log.info("{} and {} never intersect", h1.name(), h2.name());
                } else if (!futureOne && !futureTwo) {
                    log.info("{} and {} cross in the past for both hailstones", h1.name(), h2.name());
                } else if (!futureOne) {
                    log.info("{} and {} cross in the past for {}", h1.name(), h2.name(), h1.name());
                } else if (!futureTwo) {
                    log.info("{} and {} cross in the past for {}", h1.name(), h2.name(), h2.name());
                } else {
                    log.info("{} and {} are OUTSIDE at x={}, y={}", h1.name(), h2.name(), format("%.03f", x), format("%.03f", y));
                }
            }
        }
        
        return count;
    }

    @Override
    public long part2(List<String> lines) {
        List<Hailstone> hailstones = createHail(lines);
        double[][] matrix = new double[4][4];
        double[] rhs = new double[4];

        // Solve X,Y
        for (int i = 0; i < 4; i++) {
            Hailstone h1 = hailstones.get(i);
            Hailstone h2 = hailstones.get(i + 1);
            matrix[i][0] = h2.vy() - h1.vy();
            matrix[i][1] = h1.vx() - h2.vx();
            matrix[i][2] = h1.y() - h2.y();
            matrix[i][3] = h2.x() - h1.x();
            rhs[i] = -h1.x() * h1.vy() + h1.y() * h1.vx() + h2.x() * h2.vy() - h2.y() * h2.vx();
        }
        gaussianElimination(matrix, rhs);
        long x = Math.round(rhs[0]);
        long y = Math.round(rhs[1]);
        long vx = Math.round(rhs[2]);

        // Solve Z
        matrix = new double[2][2];
        rhs = new double[2];
        for (int i = 0; i < 2; i++) {
            Hailstone h1 = hailstones.get(i);
            Hailstone h2 = hailstones.get(i + 1);
            matrix[i][0] = h1.vx() - h2.vx();
            matrix[i][1] = h2.x() - h1.x();
            rhs[i] = -h1.x() * h1.vz() + h1.z() * h1.vx() + h2.x() * h2.vz() - h2.z() * h2.vx()
                    - ((h2.vz() - h1.vz()) * x) - ((h1.z() - h2.z()) * vx);
        }
        gaussianElimination(matrix, rhs);
        long z = Math.round(rhs[0]);

        return x + y + z;
    }

    // https://en.wikipedia.org/wiki/Gaussian_elimination
    private void gaussianElimination(double[][] matrix, double[] rhs) {
        int size = matrix.length;
        for (int i = 0; i < size; i++) {
            double pivot = matrix[i][i];
            for (int j = 0; j < size; j++) {
                matrix[i][j] = matrix[i][j] / pivot;
            }
            rhs[i] = rhs[i] / pivot;
            for (int k = 0; k < size; k++) {
                if (k != i) {
                    double factor = matrix[k][i];
                    for (int j = 0; j < size; j++) {
                        matrix[k][j] = matrix[k][j] - factor * matrix[i][j];
                    }
                    rhs[k] = rhs[k] - factor * rhs[i];
                }
            }
        }
    }


    private static List<Hailstone> createHail(List<String> lines) {
        char name = 'A';
        List<Hailstone> hailstones = new ArrayList<>();
        for (String line : lines) {
            Matcher m = pattern.matcher(line);
            if (!m.matches()) throw new IllegalArgumentException(line);
            hailstones.add(new Hailstone(
                    name++,
                    Long.parseLong(m.group(1)),
                    Long.parseLong(m.group(2)),
                    Long.parseLong(m.group(3)),
                    Long.parseLong(m.group(4)),
                    Long.parseLong(m.group(5)),
                    Long.parseLong(m.group(6))));
        }
        return hailstones;
    }

    private static double min() {
        return TEST ? 7d : 200000000000000d;
    }

    private static double max() {
        return TEST ? 27d : 400000000000000d;
    }
}
