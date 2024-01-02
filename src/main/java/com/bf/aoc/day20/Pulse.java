package com.bf.aoc.day20;

public enum Pulse {
    HIGH(true), LOW(false);

    private final boolean high;

    Pulse(boolean high) {
        this.high = high;
    }

    public boolean high() {
        return high;
    }

    public boolean low() {
        return !high;
    }
}
