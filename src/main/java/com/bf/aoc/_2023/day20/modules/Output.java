package com.bf.aoc._2023.day20.modules;

import com.bf.aoc._2023.day20.Packet;
import com.bf.aoc._2023.day20.PulseRunner;
import com.bf.aoc._2023.day20.Transmitter;

import java.util.Collections;
import java.util.List;

public class Output extends Module {
    private Conjunction conjunction = null;

    public Output(String name) {
        super(name);
    }

    @Override
    public void connectTo(Module module) {}

    @Override
    List<Packet> handlePulse(Packet packet) {
        if (PulseRunner.PART == 2) {
            if (conjunction == null) {
                conjunction = (Conjunction) packet.from();
            }
            Transmitter.checkStates(conjunction);
        }
        return Collections.emptyList();
    }
}
