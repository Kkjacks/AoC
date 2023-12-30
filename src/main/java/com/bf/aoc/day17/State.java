package com.bf.aoc.day17;

import com.bf.aoc.day10.Direction;

import java.util.Collection;

public class State implements Comparable<State> {
    Path path;
    int cost;
    State prev;

    public State(Path path, int cost, State prev) {
        this.path = path;
        this.cost = cost;
        this.prev = prev;
    }

    @Override
    public int compareTo(State o) {
        int ret = cost - o.cost;
        if (ret == 0 && path.direction() == o.path.direction()) {
            ret = path.steps() - o.path.steps();
        }
        if (ret == 0) {
            ret = o.path.row() + o.path.col() - path.row() - path.col();
        }
        return ret;
    }

    private void addNextWhenInBounds(Collection<State> out, Direction d, HeatGrid grid) {
        int nr = path.row() + d.rowOffset();
        int nc = path.col() + d.colOffset();
        if (nr >= 0 && nr < grid.rows && nc >= 0 && nc < grid.cols) {
            int nSteps = path.direction() == d ? path.steps() + 1 : 0;
            State nxt = new State(new Path(nr, nc, d, nSteps), cost + grid.get(nr, nc).cost, this);
            out.add(nxt);
        }
    }

    public void addNext(Collection<State> out, HeatGrid grid) {
        if (path.steps() < 2) {
            addNextWhenInBounds(out, path.direction(), grid);
        }
        addNextWhenInBounds(out, path.direction().turnLeft(), grid);
        addNextWhenInBounds(out, path.direction().turnRight(), grid);
    }

    public void addNextPart2(Collection<State> out, HeatGrid grid) {
        if (path.steps() < 9) {
            addNextWhenInBounds(out, path.direction(), grid);
        }
        if (path.steps() > 2) {
            addNextWhenInBounds(out, path.direction().turnLeft(), grid);
            addNextWhenInBounds(out, path.direction().turnRight(), grid);
        }
    }

    @Override
    public String toString() {
        return path.row() + "," + path.col() + " " + path.steps() + " " + path.direction() + " | " + cost;
    }
}
