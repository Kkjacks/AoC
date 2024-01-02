package com.bf.aoc.day20;

import com.bf.aoc.day20.modules.Module;

public record Packet(Module from, Pulse pulse, Module to) {
}
