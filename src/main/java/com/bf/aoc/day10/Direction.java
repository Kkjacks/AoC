package com.bf.aoc.day10;

public enum Direction {
    N, S, E, W;

    public Direction from() {
        if (N == this) return S;
        if (S == this) return N;
        if (E == this) return W;
        return E;
    }
}
