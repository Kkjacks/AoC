package com.bf.aoc.day10;

public enum Direction {
    N(-1,0),
    S(1,0),
    E(0,1),
    W(0,-1);

    final int rowOffset;
    final int colOffset;

    Direction(int rowOffset, int colOffset) {
        this.rowOffset = rowOffset;
        this.colOffset = colOffset;
    }

    public static Direction fromVerticalInput(String input) {
        if (input.equals("U")) return N;
        if (input.equals("D")) return S;
        if (input.equals("L")) return W;
        if (input.equals("R")) return E;
        throw new IllegalArgumentException();
    }

    public int rowOffset() {
        return rowOffset;
    }

    public int colOffset() {
        return colOffset;
    }

    public Direction from() {
        if (N == this) return S;
        if (S == this) return N;
        if (E == this) return W;
        return E;
    }

    public Direction turnLeft() {
        if (N == this) return W;
        if (S == this) return E;
        if (E == this) return N;
        return S;
    }

    public Direction turnRight() {
        if (N == this) return E;
        if (S == this) return W;
        if (E == this) return S;
        return N;
    }

    public Direction reverse() {
        return from();
    }
}
