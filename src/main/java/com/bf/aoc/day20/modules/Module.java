package com.bf.aoc.day20.modules;

import com.bf.aoc.day20.Packet;
import com.bf.aoc.day20.Pulse;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Module {
    @Getter long lowPulses = 0;
    @Getter long highPulses = 0;
    @Getter final String name;
    final List<Module> destinations = new ArrayList<>();
    @Getter final Map<Module, Pulse> sources = new HashMap<>();

    public Module(String name) {
        this.name = name;
    }

    public final List<Packet> accept(Packet packet) {
        if (packet.pulse().low())
            lowPulses++;
        else
            highPulses++;
        return handlePulse(packet);
    }

    abstract List<Packet> handlePulse(Packet packet);

    public void connectTo(Module module) {
        destinations.add(module);
        module.connectFrom(this);
    }
    void connectFrom(Module module) {}

    List<Packet> generatePackets(Pulse pulse) {
        return destinations.stream()
                .map(d -> new Packet(this, pulse, d))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return name;
    }
}
