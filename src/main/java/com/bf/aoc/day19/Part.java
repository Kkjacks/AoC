package com.bf.aoc.day19;

public record Part(int x, int m, int a, int s) {

    public long total() {
        return x + m + a + s;
    }
}
