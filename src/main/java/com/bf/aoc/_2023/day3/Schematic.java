package com.bf.aoc._2023.day3;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Schematic {
    private static final Logger log = LoggerFactory.getLogger(Schematic.class);
    private static final Entry EMPTY = new EmptyEntry();
    private final Table<Integer, Integer, Entry> table;
    private final int rows, cols;

    public Schematic(List<String> lines) {
        rows = lines.size();
        cols = lines.get(0).length();
        this.table = HashBasedTable.create(rows, cols);
        int r = 0;
        for (String line : lines) {
            r++;
            int c = 0;
            for (char input : line.toCharArray()) {
                c++;
                table.put(r, c, new Entry(r, c, input));
            }
        }
        table.values().forEach(e -> e.analyze(this));
    }

    public Multimap<Entry, Entry> getGears() {
        SetMultimap<Entry, Entry> gearMap = HashMultimap.create();
        for (Entry gear : table.values()) {
            if (gear.isGear()) {
                for (Entry partConnection : gear.getPartConnections(this)) {
                    gearMap.put(gear, partConnection);
                }
            }
        }
        return gearMap;
    }

    public Entry get(int row, int col) {
        return Optional.ofNullable(table.get(row, col)).orElse(EMPTY);
    }

    public List<Entry> getPartNumberEntries() {
        List<Entry> partNumberEntries = new ArrayList<>();
        for (Entry entry : table.values()) {
            if (entry.isParentPart() && entry.connectsToSymbol(this)) {
                partNumberEntries.add(entry);
            }
        }
        return partNumberEntries;
    }
}


