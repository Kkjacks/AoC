package com.bf.aoc._2023.day22;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(fluent = true)
public class Position {
    private int x,y,z;

    public Position(String x, String y, String z) {
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
        this.z = Integer.parseInt(z);
    }

    public void drop() {
        z--;
    }

    public Position below() {
        return new Position(x, y, z-1);
    }
}
