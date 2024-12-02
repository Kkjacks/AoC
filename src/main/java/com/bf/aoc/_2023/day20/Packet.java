package com.bf.aoc._2023.day20;

import com.bf.aoc._2023.day20.modules.Module;

public record Packet(Module from, Pulse pulse, Module to) {
}
