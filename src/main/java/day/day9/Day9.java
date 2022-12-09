package day.day9;

import common.FileReader;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
    For this puzzle I'll be expressing my right to liberate a grid puzzle from the grid and turn it into a cesspit of pain.
    Second part was a bit rough though, not the conversion from part 1 to part 2 but the fact that I forgot to make "Point" immutable.
*/

public class Day9 {

    private static final int ROPE_LENGTH = 9;

    private static final Set<Point> visitedPointsByTail = new HashSet<>() {{
        add(new Point(0, 0));
    }};

    private static final Point head = new Point(0, 0);
    private static final List<Point> rope = new ArrayList<>(ROPE_LENGTH);

    public static void main(String[] args) {
        for (int i = 0; i < ROPE_LENGTH; i++) {
            rope.add(new Point(0, 0));
        }

        FileReader.read("Day9").forEach(line -> {
            String[] command = line.split(" ");
            int amount = Integer.parseInt(command[1]);
            for (int i = 0; i < amount; i++) {
                stepHead(command[0]);
                for (int ropeIndex = 0; ropeIndex < ROPE_LENGTH; ropeIndex++) {
                    stepRope(ropeIndex);
                }
                visitedPointsByTail.add(rope.get(ROPE_LENGTH - 1).clone());
            }
        });

        System.out.println(visitedPointsByTail.size());
    }

    private static void stepHead(String direction) {
        switch (direction) {
            case "U":
                head.right();
                break;
            case "R":
                head.up();
                break;
            case "D":
                head.left();
                break;
            case "L":
                head.down();
                break;
            default:
                throw new IllegalStateException("What?");
        }
    }

    private static void stepRope(int ropeIndex) {
        Point currentHead = ropeIndex == 0 ? head : rope.get(ropeIndex - 1);
        Point currentTail = rope.get(ropeIndex);
        if (areTwoPointsTouching(currentHead, currentTail)) {
            return;
        }

        // I have no shame.
        if (currentHead.getRow() > currentTail.getRow() && currentHead.getColumn() > currentTail.getColumn()) { // upper right
            currentTail.right();
            currentTail.up();
        } else if (currentHead.getRow() > currentTail.getRow() && currentHead.getColumn() < currentTail.getColumn()) { // lower right
            currentTail.left();
            currentTail.up();
        } else if (currentHead.getRow() < currentTail.getRow() && currentHead.getColumn() > currentTail.getColumn()) { // upper left
            currentTail.right();
            currentTail.down();
        } else if (currentHead.getRow() < currentTail.getRow() && currentHead.getColumn() < currentTail.getColumn()) { // lower left
            currentTail.down();
            currentTail.left();
        } else if (currentHead.getRow() == currentTail.getRow() && currentHead.getColumn() < currentTail.getColumn()) { // down
            currentTail.left();
        } else if (currentHead.getRow() == currentTail.getRow() && currentHead.getColumn() > currentTail.getColumn()) { // up
            currentTail.right();
        } else if (currentHead.getRow() < currentTail.getRow() && currentHead.getColumn() == currentTail.getColumn()) { // left
            currentTail.down();
        } else if (currentHead.getRow() > currentTail.getRow() && currentHead.getColumn() == currentTail.getColumn()) { // right
            currentTail.up();
        } else {
            throw new IllegalStateException("What??");
        }
    }

    private static boolean areTwoPointsTouching(Point p1, Point p2) {
        return !(Math.abs(p1.getColumn() - p2.getColumn()) > 1 || Math.abs(p1.getRow() - p2.getRow()) > 1);
    }


    @Data
    @AllArgsConstructor
    private static class Point {
        private int row;
        private int column;

        public void right() {
            column++;
        }

        public void up() {
            row++;
        }

        public void left() {
            column--;
        }

        public void down() {
            row--;
        }

        @Override
        public Point clone() {
            return new Point(this.getRow(), this.getColumn());
        }
    }

}
