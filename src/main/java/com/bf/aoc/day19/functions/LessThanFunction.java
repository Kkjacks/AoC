package com.bf.aoc.day19.functions;

import com.bf.aoc.day19.Part;
import com.bf.aoc.day19.PartRange;
import com.bf.aoc.day19.PartType;
import com.bf.aoc.day19.Result;

public class LessThanFunction implements RangeFunction {
    private final PartType partType;
    private final Integer value;
    private final Result result;

    public LessThanFunction(PartType partType, Integer value, String result) {
        this.partType = partType;
        this.value = value;
        this.result = new Result(result);
    }

    @Override
    public Result apply(Part part) {
        return partType.get(part) < value ? result : null;
    }

    @Override
    public PartRange calcRange(PartRange partRange) {
        PartRange negRange = new PartRange(partRange);
        partRange.setMax(partType, value-1);
        negRange.setMin(partType, value);
        return negRange;
    }

    @Override
    public Result getAcceptResult() {
        return result;
    }
}
