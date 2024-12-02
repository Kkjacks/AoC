package com.bf.aoc._2023.day25;

public record WorkItem(Vertex vertex, int steps, Edge used, WorkItem prev){
    @Override
    public String toString() {
        return vertex.name() + " " + steps;
    }
}
