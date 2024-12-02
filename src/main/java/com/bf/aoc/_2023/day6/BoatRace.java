package com.bf.aoc._2023.day6;

public class BoatRace {
    private final long time;
    private final long record;

    public BoatRace(long time, long record) {
        this.time = time;
        this.record = record;
    }

    public long evaluateOptions() {
        long options = 0;
        for (long speed = 1; speed < time; speed++) {
            if (record < ((time - speed) * speed)) {
                options++;
            }
        }
        return options;
    }
}
