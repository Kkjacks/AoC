package com.bf.aoc.day24;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
record Hailstone(char name, double x, double y, double z, double vx, double vy, double vz) {

    @Override
    public String toString() {
        return String.format("%.0f, %.0f @ %.0f, %.0f   %f * x +%f", x, y, vx, vy, a(), b());
    }

    public double b() {
        return y - x * (vy / vx);
    }

    public double a() {
        return vy / vx;
    }
}
