package com.bf.aoc.day19;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.IntegerRange;
import org.apache.commons.lang3.Range;

public class PartRange {
    @Getter @Setter
    private boolean valid = true;
    private Range<Integer> x;
    private Range<Integer> m;
    private Range<Integer> a;
    private Range<Integer> s;

    public PartRange() {
        x = IntegerRange.of(1, 4000);
        m = IntegerRange.of(1, 4000);
        a = IntegerRange.of(1, 4000);
        s = IntegerRange.of(1, 4000);
    }

    // Copy constructor
    public PartRange(PartRange r) {
        x = IntegerRange.of(r.x.getMinimum(), r.x.getMaximum());
        m = IntegerRange.of(r.m.getMinimum(), r.m.getMaximum());
        a = IntegerRange.of(r.a.getMinimum(), r.a.getMaximum());
        s = IntegerRange.of(r.s.getMinimum(), r.s.getMaximum());
    }

    public void setMin(PartType type, int value) {
        switch (type) {
            case X -> x = intersection(x, IntegerRange.of(value, 4000));
            case M -> m = intersection(m, IntegerRange.of(value, 4000));
            case A -> a = intersection(a, IntegerRange.of(value, 4000));
            case S -> s = intersection(s, IntegerRange.of(value, 4000));
        }
    }

    public void setMax(PartType type, int value) {
        switch (type) {
            case X -> x = intersection(x, IntegerRange.of(1, value));
            case M -> m = intersection(m, IntegerRange.of(1, value));
            case A -> a = intersection(a, IntegerRange.of(1, value));
            case S -> s = intersection(s, IntegerRange.of(1, value));
        }
    }

    public long acceptableCombinations() {
        if (!valid) throw new IllegalStateException();
        int xVals = x.getMaximum() - x.getMinimum() + 1;
        int mVals = m.getMaximum() - m.getMinimum() + 1;
        int aVals = a.getMaximum() - a.getMinimum() + 1;
        int sVals = s.getMaximum() - s.getMinimum() + 1;
        return (long) xVals * mVals * aVals * sVals;
    }

    private Range<Integer> intersection(Range<Integer> self, IntegerRange other) {
        if (self.isOverlappedBy(other)) {
            return self.intersectionWith(other);
        } else {
            valid = false;
            return null;
        }

    }
}
