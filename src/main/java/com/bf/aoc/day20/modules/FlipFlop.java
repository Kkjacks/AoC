package com.bf.aoc.day20.modules;

import com.bf.aoc.day20.Packet;
import com.bf.aoc.day20.Pulse;

import java.util.Collections;
import java.util.List;

public class FlipFlop extends Module {
    private boolean on = false;

    public FlipFlop(String name) {
        super(name);
    }

    @Override
    public List<Packet> handlePulse(Packet packet) {
        if (packet.pulse().high())
            return Collections.emptyList();

        on = !on;
        Pulse pulse = on ? Pulse.HIGH : Pulse.LOW;
        return generatePackets(pulse);
    }
}
