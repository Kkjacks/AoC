package com.bf.aoc._2023.day16;

import com.bf.aoc.util.Direction;

public class Beam {
    Coordinates coordinates;
    Direction direction;

    public Beam(Coordinates coordinates, Direction direction) {
        this.coordinates = coordinates;
        this.direction = direction;
    }

    public void traverse(EnergyGrid grid) {
        //System.out.println(coordinates + "\n" + grid.printEnergyGrid(coordinates));
        Cell cell = grid.getCell(coordinates);
        if (cell == null || cell.isLoop(direction))
            return;
        cell.energized = true;
        Beam newBeam = cell.moveBeam(this);
        if (newBeam != null) {
            newBeam.traverse(grid);
        }
        traverse(grid);
    }

    public Beam move(Direction direction) {
        coordinates.move(direction);
        this.direction = direction;
        return null;
    }

    public Beam split(Direction direction, Direction direction2) {
        Beam newBeam = new Beam(coordinates.split(direction2), direction2);
        coordinates.move(direction);
        this.direction = direction;
        return newBeam;
    }
}
