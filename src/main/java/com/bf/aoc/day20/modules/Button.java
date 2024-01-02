package com.bf.aoc.day20.modules;

import com.bf.aoc.day20.Packet;
import com.bf.aoc.day20.Pulse;

import java.util.List;

public class Button extends Module {

    public Button(Broadcaster broadcaster) {
        super("Button");
        connectTo(broadcaster);
    }

    @Override
    public List<Packet> handlePulse(Packet packet) {
        return generatePackets(Pulse.LOW);
    }
}
