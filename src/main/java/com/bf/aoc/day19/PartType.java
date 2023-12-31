package com.bf.aoc.day19;

import java.util.function.Function;

public enum PartType {
    X(Part::x),
    M(Part::m),
    A(Part::a),
    S(Part::s);

    public static PartType from(String input) {
        switch (input) {
            case "x" -> { return X; }
            case "m" -> { return M; }
            case "a" -> { return A; }
            case "s" -> { return S; }
            default -> throw new IllegalArgumentException();
        }
    }

    private final Function<Part, Integer> function;

    PartType(Function<Part, Integer> function) {
        this.function = function;
    }

    public int get(Part part) {
        return function.apply(part);
    }
}
