package com.bf.aoc._2023.day9;

public class Node {
    int value;
    int level = 0;
    Node prev, next;
    Node child;
    Node parent1, parent2;

    public Node(int value) {
        this.value = value;
    }

    public void next(Node next) {
        this.next = next;
        next.prev = this;
        int diff = next.value - this.value;
//        if (diff == 0) {
//
//        }
        child = new Node(diff);
        child.parent1 = this;
        child.parent2 = next;
        child.level = this.level + 1;
        if (prev != null) {
            prev.child.next(child);
        }
    }

    public int calculateNext() {
        if (prev.child.value == 0) {
            return value;
        } else {
            prev.child.next(new Node(prev.child.value));
            return prev.child.value;
        }
    }

    public int getChildSum() {
        if (child.value == 0) {
            return value;
        } else {
            return value + child.getChildSum();
        }
    }

    @Override
    public String toString() {
        return "" + value;
    }

    public String print() {
        String result = "" + value;
        if (next != null) {
            result += " - " + next.print();
        } else {
            return result + "\n";
        }
        if (prev == null) {
            result += child.print();
        }
        return result;
    }

//    public String print(int i) {
//        String result = "" + value;
//        if (next != null) {
//            result += " - " + next.print();
//        } else {
//            result += "\n";
//        }
//        if (prev != null && child != null) {
//            result += child.print();
//        }
//        return result;
//    }
//
//    private void printChild(String result) {
//        if (prev != null) {
//            prev.printChild(result);
//        }
//
//        for (int i=0; i<child.level; i++) {
//            result += "  ";
//        }
//        result += child.value;
//    }
}
