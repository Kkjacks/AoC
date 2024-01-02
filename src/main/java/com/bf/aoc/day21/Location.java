package com.bf.aoc.day21;

import com.bf.aoc.util.Direction;
import com.bf.aoc.util.Point;
import lombok.Data;

@Data
public class Location {
    private Point point;
    private int steps = 0;
    private int extendedRows = 0;
    private int extendedCols = 0;

    public Location(Point point) {
        this.point = point;
    }

    private Location(Location location, Direction direction) {
        this.point = location.getPoint().move(direction);
        this.steps = location.getSteps() + 1;
        this.extendedRows = location.getExtendedRows();
        this.extendedCols = location.getExtendedCols();
    }

    public Location move(Direction direction) {
        return new Location(this, direction);
    }

    public Long row() {
        return point.row();
    }

    public Long col() {
        return point.col();
    }
}
