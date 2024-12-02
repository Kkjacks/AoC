package com.bf.aoc._2023.day20;

import com.bf.aoc._2023.day20.modules.Conjunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transmitter {
    private static final Logger log = LoggerFactory.getLogger("");
    private static final List<Packet> queue = new ArrayList<>();
    public static long pulses = 0;
    private static Integer totalToggles = null;
    public final static Map<String, Long> pulsesToToggle = new HashMap<>();
    public static boolean breakCircuit = false;

    public static void sendPulse(Packet p) {
        pulses++;
        queue.add(p);
        while (!queue.isEmpty() && !breakCircuit) {
            Packet packet = queue.remove(0);
            // log.info("{} -{}-> {}", packet.from().getName(), packet.pulse(), packet.to().getName());
            queue.addAll(packet.to().accept(packet));
        }
    }

    public static void checkStates(Conjunction conjunction) {
        if (totalToggles == null) {
            totalToggles = conjunction.getSources().size();
        }
        if (conjunction.getSources().containsValue(Pulse.HIGH)) {
            conjunction.getSources().entrySet().stream()
                    .filter(e -> e.getValue() == Pulse.HIGH)
                    .filter(e -> !pulsesToToggle.containsKey(e.getKey().getName()))
                    .forEach(e -> pulsesToToggle.put(e.getKey().getName(), pulses));

            if (pulsesToToggle.size() == totalToggles) {
                breakCircuit = true;
            }

            log.info("{}) {}", pulses, conjunction.getSources());
        }
    }
}
