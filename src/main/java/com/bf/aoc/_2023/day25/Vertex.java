package com.bf.aoc._2023.day25;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Accessors(fluent = true)
public class Vertex {
    private final String name;
    private final List<Edge> edges = new ArrayList<>();

    public void add(Edge edge) {
        edges.add(edge);
    }

    public void remove(List<Edge> edges) {
        this.edges.removeAll(edges);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex vertex)) return false;
        return Objects.equals(name, vertex.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
