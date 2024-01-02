package com.bf.aoc.day20.modules;

import com.bf.aoc.day20.Packet;
import com.bf.aoc.day20.Pulse;

import java.util.List;

public class Conjunction extends Module {
    public Conjunction(String name) {
        super(name);
    }

    @Override
    public void connectFrom(Module module) {
        sources.put(module, Pulse.LOW);
    }

    @Override
    List<Packet> handlePulse(Packet packet) {
        sources.put(packet.from(), packet.pulse());
        Pulse pulse = sources.containsValue(Pulse.LOW) ? Pulse.HIGH : Pulse.LOW;
        return generatePackets(pulse);
    }
}
