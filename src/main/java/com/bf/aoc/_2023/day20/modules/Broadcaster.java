package com.bf.aoc._2023.day20.modules;

import com.bf.aoc._2023.day20.Packet;
import com.bf.aoc._2023.day20.Pulse;

import java.util.List;

public class Broadcaster extends Module {

    public Broadcaster() {
        super("Broadcaster");
    }

    @Override
    List<Packet> handlePulse(Packet packet) {
        if (packet.pulse().high())
            throw new IllegalArgumentException("Should always be low");
        
        return generatePackets(Pulse.LOW);
    }
}
