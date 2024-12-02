package com.bf.aoc._2023.day19.functions;

import com.bf.aoc._2023.day19.Part;
import com.bf.aoc._2023.day19.PartRange;
import com.bf.aoc._2023.day19.PartType;
import com.bf.aoc._2023.day19.Result;

public class GreaterThanFunction implements RangeFunction {
    private final PartType partType;
    private final Integer value;
    private final Result result;

    public GreaterThanFunction(PartType partType, Integer value, String result) {
        this.partType = partType;
        this.value = value;
        this.result = new Result(result);
    }

    @Override
    public Result apply(Part part) {
        return partType.get(part) > value ? result : null;
    }

    @Override
    public PartRange calcRange(PartRange partRange) {
        PartRange negRange = new PartRange(partRange);
        partRange.setMin(partType, value+1);
        negRange.setMax(partType, value);
        return negRange;
    }

    @Override
    public Result getAcceptResult() {
        return result;
    }
}
