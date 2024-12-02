package com.bf.aoc._2023.day25;

import com.bf.aoc._2023.Runner2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SnowverloadRunner extends Runner2023 {
    static {
        DAY=25; TEST=false; PART=1;
    }
    private static final Pattern pattern = Pattern.compile("(\\w{3}): (.*)");

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        SnowverloadRunner r = new SnowverloadRunner();
        log.info("Result: {}", isPart1() ? r.part1(input()) : r.part2(input()));
        long endTime = System.currentTimeMillis();
        log.info("{} sec", String.format("%5.3f", (endTime - startTime) / 1000f));
    }

    @Override
    public long part1(List<String> lines) {
        Map<String, Vertex> vertexMap = new HashMap<>();
        List<Edge> edges = new ArrayList<>();
        for (String line : lines) {
            Matcher m = pattern.matcher(line);
            if (!m.matches()) throw new IllegalStateException(line);
            Vertex v1 = vertexMap.computeIfAbsent(m.group(1), Vertex::new);
            for (String name : m.group(2).split(" ")) {
                Vertex v2 = vertexMap.computeIfAbsent(name, Vertex::new);
                Edge edge = new Edge(v1, v2);
                edges.add(edge);
                v1.edges().add(edge);
                v2.edges().add(edge);
            }
        }
        List<Vertex> vertices = new ArrayList<>(vertexMap.values());
        Random random = new Random();
        int i = 0;
        while (i++ < 1000) {
            dijkstra(vertices.get(random.nextInt(vertices.size())), vertices.get(random.nextInt(vertices.size())));
        }
        edges.sort((o1, o2) -> o2.counter - o1.counter);

        List<Edge> cuts = Arrays.asList(edges.get(0), edges.get(1), edges.get(2));
        vertices.forEach(v -> v.remove(cuts));
        return (long) countSize(edges.get(0).v1()) * countSize(edges.get(0).v2());
    }

    @Override
    public long part2(List<String> lines) {
        return 1;
    }


    private static int countSize(Vertex vertex) {
        Set<Vertex> seen = new HashSet<>();
        LinkedList<Vertex> vertices = new LinkedList<>();
        vertices.add(vertex);
        while (!vertices.isEmpty()) {
            Vertex v = vertices.removeFirst();
            if (!seen.contains(v)) {
                seen.add(v);
                for (Edge edge : v.edges()) {
                    vertices.add(edge.other(v));
                }
            }
        }
        return seen.size();
    }

    private static void dijkstra(Vertex start, Vertex end) {
        PriorityQueue<WorkItem> queue = new PriorityQueue<>(Comparator.comparingInt(WorkItem::steps));
        HashMap<Vertex, Integer> shortest = new HashMap<>();
        queue.add(new WorkItem(start, 0, null, null));
        while (!queue.isEmpty()) {
            WorkItem wi = queue.remove();
            int way = shortest.computeIfAbsent(wi.vertex(), v -> Integer.MAX_VALUE);
            if (way <= wi.steps()) {
                continue;
            }
            if (end == wi.vertex()) {
                WorkItem cur = wi;
                while (cur.used() != null) {
                    cur.used().counter++;
                    cur = cur.prev();
                }
                return;
            }
            shortest.put(wi.vertex(), wi.steps());
            for (Edge edge : wi.vertex().edges()) {
                Vertex cand = edge.other(wi.vertex());
                int cur = shortest.computeIfAbsent(cand, v -> Integer.MAX_VALUE);
                if (cur > wi.steps() + 1) {
                    queue.add(new WorkItem(cand, wi.steps()+1, edge, wi));
                }
            }
        }
    }
}
