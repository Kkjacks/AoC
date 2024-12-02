package com.bf.aoc._2023.day9;

public class Cell {
    int value;
    Cell child, next, prev, parent;

    public Cell(int value) {
        this.value = value;
    }

    public Cell(int value, Cell prev) {
        this.value = value;
    }

    public Cell next(int value) {
        next = new Cell(value);
        next.prev = this;
        return next;
    }

    public Cell calculateChild() {
        if (next == null) {
            return null;
        }
        int diff = next.value - value;
        child = new Cell(diff);
        child.parent = this;
        child.next = next.calculateChild();
        if (diff != 0)
            child.calculateChild();
        if (prev != null) {
            child.prev = prev.child;
        }

        return child;
    }

    public Cell calculateNext() {
        if (next != null) {
            return
                    next.calculateNext();
        } else {
            //if (value == 0) {
            if (isCurrentAndPreviousZero()) {
                next(0);
                next.parent = parent.next;
                return next;
            } else {
                child = prev.child.calculateNext();
                return next(value + child.value);
            }
        }
//        return prev.child.calculateNext();
    }

    private boolean isCurrentAndPreviousZero() {
        if (value == 0) {
            return prev == null || prev.isCurrentAndPreviousZero();
        }
        return false;
    }

    public String toString() {
        return "" + value;
    }

    public String print() {
        String result = "" + value;
        if (child != null) {
            result += ", " + child.print();
        }
        if (next != null && next.parent == null) {
            result += "\n" + next.print();
        }
        return result;
    }

}
