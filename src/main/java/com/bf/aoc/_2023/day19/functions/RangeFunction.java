package com.bf.aoc._2023.day19.functions;

import com.bf.aoc._2023.day19.Part;
import com.bf.aoc._2023.day19.PartRange;
import com.bf.aoc._2023.day19.Result;

import java.util.function.Function;

public interface RangeFunction extends Function<Part, Result> {
    PartRange calcRange(PartRange partRange);

    Result getAcceptResult();
}
