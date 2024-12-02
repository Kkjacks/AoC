package com.bf.aoc._2023.day16;

import com.bf.aoc.Runner;
import com.bf.aoc.util.Direction;

import java.util.List;

public class EnergyBeamRunner extends Runner {
    static {
        DAY=16; TEST=false; PART=2;
    }

    public static void main(String[] args) {
        EnergyBeamRunner r = new EnergyBeamRunner();
        log.info("Result: {}", isPart1() ? r.part1(input()) : r.part2(input()));
    }

    @Override
    public long part1(List<String> lines) {
        EnergyGrid grid = new EnergyGrid(lines);
        Beam beam = new Beam(new Coordinates(1,1), Direction.E);
        beam.traverse(grid);
        return grid.countEnergizedCells();
    }

    @Override
    public long part2(List<String> lines) {
        EnergyGrid grid = new EnergyGrid(lines);
        long best = 0;
        for (int r=1; r<grid.rows; r++) {
            Beam beam = new Beam(new Coordinates(r,1), Direction.E);
            beam.traverse(grid);
            long count = grid.countEnergizedCells();
            grid.reset();
            log.info("{}[{},{}]E = {}", (count > best ? "****" : "\t"), r, 1, count);
            if (count > best) {
                best = count;
            }
        }
        for (int r=1; r<grid.rows; r++) {
            Beam beam = new Beam(new Coordinates(r, grid.cols-1), Direction.W);
            beam.traverse(grid);
            long count = grid.countEnergizedCells();
            grid.reset();
            log.info("{}[{},{}]W = {}", (count > best ? "****" : "\t"), r, grid.cols-1, count);
            if (count > best) {
                best = count;
            }
        }
        for (int c=1; c< grid.cols; c++) {
            Beam beam = new Beam(new Coordinates(1,c), Direction.S);
            beam.traverse(grid);
            long count = grid.countEnergizedCells();
            grid.reset();
            log.info("{}[{},{}]S = {}", (count > best ? "****" : "\t"), 1, c, count);
            if (count > best) {
                best = count;
            }
        }
        for (int c=1; c< grid.cols; c++) {
            Beam beam = new Beam(new Coordinates(grid.rows-1, c), Direction.N);
            beam.traverse(grid);
            long count = grid.countEnergizedCells();
            grid.reset();
            log.info("{}[{},{}]N = {}", (count > best ? "****" : "\t"), grid.rows-1, c, count);
            if (count > best) {
                best = count;
            }
        }
        return best;
    }

}
