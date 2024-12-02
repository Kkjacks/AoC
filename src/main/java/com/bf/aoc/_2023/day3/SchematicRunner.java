package com.bf.aoc._2023.day3;

import com.bf.aoc.DataReader;
import com.bf.aoc.Runner;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SchematicRunner extends Runner {
    private static final Logger log = LoggerFactory.getLogger("");
    static {
        DAY=3; TEST=false; PART=2;
    }

    public static void main(String[] args) throws Exception {
        SchematicRunner r = new SchematicRunner();
        log.info("Result: {}", isPart1() ? r.part1(input()) : r.part2(input()));
    }

    @Override
    public long part1(List<String> lines) {
        Schematic schematic = new Schematic(DataReader.data(3));
        int total = 0;
        for (Entry partNumberEntry : schematic.getPartNumberEntries()) {
            total += partNumberEntry.getPartNumber();
        }
        return total;
    }

    @Override
    public long part2(List<String> lines) {
        Schematic schematic = new Schematic(lines);
        int total = 0;
        Multimap<Entry, Entry> gears = schematic.getGears();
        for (Entry key : gears.keySet()) {
            List<Entry> parts = Lists.newArrayList(gears.get(key));
            if (parts.size() == 2) {
                int part1 = parts.get(0).getPartNumber();
                int part2 = parts.get(1).getPartNumber();
                log.info("{} * {} = {}", part1, part2, part1 * part2);
                total += (part1 * part2);
            }
        }
        return total;
    }
}
