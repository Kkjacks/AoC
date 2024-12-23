package com.bf.aoc._2023.day15;

import com.bf.aoc._2023.Runner2023;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HashRunner extends Runner2023 {
    static {
        DAY=15; TEST=false; PART=2;
    }
    private static final Pattern pattern = Pattern.compile("(\\w+)[=\\-](\\d)?");

    public static void main(String[] args) {
        HashRunner r = new HashRunner();
        log.info("Result: {}", isPart1() ? r.part1(input()) : r.part2(input()));
    }

    @Override
    public long part1(List<String> lines) {
        if (lines.size() != 1)
            throw new IllegalStateException();
        List<Integer> hashes = new ArrayList<>();
        for (String code : lines.get(0).split(",")) {
            log.info("{}", code);
            hashes.add(hash(code));
        }
        return hashes.stream().reduce(0, Integer::sum);
    }

    @Override
    public long part2(List<String> lines) {
        if (lines.size() != 1)
            throw new IllegalStateException();
        Map<Integer, List<Pair<String, Integer>>> boxes = new HashMap<>();
        for (String input : lines.get(0).split(",")) {
            Matcher matcher = pattern.matcher(input);
            if (!matcher.matches()) {
                log.info("{} is invalid", input);
                System.exit(1);
            }
            String code = matcher.group(1);
            String lens = matcher.group(2);
            int boxNumber = hash(code);
            if (!boxes.containsKey(boxNumber))
                boxes.put(boxNumber, new ArrayList<>());
            List<Pair<String, Integer>> box = boxes.get(boxNumber);
            Pair<String, Integer> entry = box.stream().filter(p -> p.getKey().equals(code)).findFirst().orElse(null);

            // remove entry if exists
            if (lens == null) {
                box.remove(entry);
            } else {
                // replace slot with new lens
                if (entry != null) {
                    int index = box.indexOf(entry);
                    box.remove(entry);
                    box.add(index, Pair.of(code, Integer.parseInt(lens)));
                // add new slot
                } else {
                    box.add(Pair.of(code, Integer.parseInt(lens)));
                }
            }
        }

        // calculate result
        int result = 0;
        for (Map.Entry<Integer, List<Pair<String, Integer>>> entry : boxes.entrySet()) {
            int boxNum = entry.getKey() + 1;
            int slotNum=0;
            for (Pair<String, Integer> slot : entry.getValue()) {
                slotNum++;
                int focalLength = slot.getValue();
                int value = boxNum * slotNum * focalLength;
                log.info("Box {} [{} {}] = {}", boxNum, slot.getKey(), focalLength, value);
                result += value;
            }

        }
        return result;
    }

    private int hash(String code) {
        int hash = 0;
        for (char c : code.toCharArray()) {
            hash = ((hash + c) * 17) % 256;
        }
        return hash;
    }
}
