package com.bf.aoc.day3;

import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class Entry {
    final int row, col;
    private final boolean number, symbol, gear;
    final char val;
    private Entry parentPart;
    private Integer partNumber;

    public Entry(int row, int col, Character val) {
        this.row = row;
        this.col = col;
        this.val = val;
        this.number = Character.isDigit(val);
        this.symbol = !number && val != '.';
        this.gear = val == '*';
    }

    public void analyze(Schematic schematic) {
        if (isNumber()) {
            Entry prev = prev(schematic);
            if (!prev.isNumber()) {
                parentPart = this;
                determinePartNumber(schematic);
            } else {
                while (prev.isNumber()) {
                    parentPart = prev;
                    prev = prev.prev(schematic);
                }
            }
        }
    }

    private void determinePartNumber(Schematic schematic) {
        String part = "" + val;
        Entry next = next(schematic);
        while (next.isNumber()) {
            part += next.val;
            next = next.next(schematic);
        }
        partNumber = Integer.parseInt(part);
    }

    public boolean isParentPart() {
        return isNumber() && this == parentPart;
    }

    private Entry prev(Schematic schematic) {
        return schematic.get(row, col-1);
    }

    private Entry next(Schematic schematic) {
        return schematic.get(row, col+1);
    }

    public boolean connectsToSymbol(Schematic schematic) {
        if(schematic.get(row-1, col-1).isSymbol()
            || schematic.get(row-1, col).isSymbol()
            || schematic.get(row-1, col+1).isSymbol()
            || schematic.get(row, col-1).isSymbol()
            || schematic.get(row, col+1).isSymbol()
            || schematic.get(row+1, col-1).isSymbol()
            || schematic.get(row+1, col).isSymbol()
            || schematic.get(row+1, col+1).isSymbol()) {
            return true;
        }
        Entry next = next(schematic);
        if (next.isNumber()) {
            return next.connectsToSymbol(schematic);
        }
        return false;
    }

    public Set<Entry> getPartConnections(Schematic schematic) {
        Set<Entry> parts = new HashSet<>();
        addIfPart(schematic.get(row-1, col-1), parts);
        addIfPart(schematic.get(row-1, col), parts);
        addIfPart(schematic.get(row-1, col+1), parts);
        addIfPart(schematic.get(row, col-1), parts);
        addIfPart(schematic.get(row, col), parts);
        addIfPart(schematic.get(row, col+1), parts);
        addIfPart(schematic.get(row+1, col-1), parts);
        addIfPart(schematic.get(row+1, col), parts);
        addIfPart(schematic.get(row+1, col+1), parts);
        return parts;
    }

    private void addIfPart(Entry entry, Set<Entry> parts) {
        if (entry.isNumber())
            parts.add(entry.getParentPart());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entry entry)) return false;
        return row == entry.row && col == entry.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        if (this == parentPart) {
            return "[" + row + "," + col + "," + partNumber.toString() + "]";
        } else {
            return "[" + row + "," + col + "," + val + "]";
        }
    }
}
