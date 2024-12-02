package com.bf.aoc._2023.day20;

import com.bf.aoc.Runner;
import com.bf.aoc._2023.day20.modules.Broadcaster;
import com.bf.aoc._2023.day20.modules.Button;
import com.bf.aoc._2023.day20.modules.Conjunction;
import com.bf.aoc._2023.day20.modules.FlipFlop;
import com.bf.aoc._2023.day20.modules.Module;
import com.bf.aoc._2023.day20.modules.Output;
import com.bf.aoc.util.LCM;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PulseRunner extends Runner {
    static {
        DAY=20; TEST=false; PART=2;
    }
    private static final Pattern pattern = Pattern.compile("(\\S+) -> (.*)");

    public static void main(String[] args) {
        PulseRunner r = new PulseRunner();
        log.info("Result: {}", isPart1() ? r.part1(input()) : r.part2(input()));
    }

    @Override
    public long part1(List<String> lines) {
        Broadcaster broadcaster = new Broadcaster();
        Map<String, Module> moduleMap = buildModules(lines, broadcaster);

        for (int i = 0; i < 1000; i++) {
            Transmitter.sendPulse(new Packet(new Button(broadcaster), Pulse.LOW, broadcaster));
            log.info(" -- {} --\n\n", i);
        }

        long lowPulses = moduleMap.values().stream()
                .mapToLong(Module::getLowPulses)
                .sum();
        long highPulses = moduleMap.values().stream()
                .mapToLong(Module::getHighPulses)
                .sum();

        log.info("\n\n{} low {} high", lowPulses, highPulses);

        return lowPulses * highPulses;
    }

    @Override
    public long part2(List<String> lines) {
        Broadcaster broadcaster = new Broadcaster();
        buildModules(lines, broadcaster);
        while (!Transmitter.breakCircuit) {
            Transmitter.sendPulse(new Packet(new Button(broadcaster), Pulse.LOW, broadcaster));
        }

        return LCM.calculate(Transmitter.pulsesToToggle.values());
        
    }

    private Map<String, Module> buildModules(List<String> lines, Broadcaster broadcaster) {
        Map<String, Module> moduleMap = new HashMap<>();
        Map<Module, String[]> connectionsMap = new HashMap<>();

        for (String line : lines) {
            Matcher m = pattern.matcher(line);
            if (!m.matches()) throw new IllegalArgumentException();
            String name = m.group(1);
            String[] modNames = m.group(2).split(", ");
            Module module;
            if (name.equals("broadcaster")) {
                module = broadcaster;
            } else if (name.startsWith("%")) {
                module = new FlipFlop(name.substring(1));
            } else {
                module = new Conjunction(name.substring(1));
            }
            moduleMap.put(module.getName(), module);
            connectionsMap.put(module, modNames);
        }

        for (Map.Entry<Module, String[]> entry : connectionsMap.entrySet()) {
            Module src = entry.getKey();
            for (String destName : entry.getValue()) {
                Module dest = moduleMap.get(destName);
                if (dest == null) {
                    dest = new Output(destName);
                    moduleMap.put("Output", dest);
                }
                src.connectTo(dest);
            }
        }
        return moduleMap;
    }

}
