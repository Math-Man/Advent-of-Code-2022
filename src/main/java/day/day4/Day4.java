package day.day4;

import common.FileReader;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

public class Day4 {
    public static void main(String[] args) {
        part1();
        part2();
    }

    private static void part2() {
        AtomicInteger counter = new AtomicInteger();
        FileReader.read("Day4").stream()
                .map(AssignmentsLine::new)
                .forEach(asg -> {
                    boolean notOverlapping = asg.firstAssignmentStart > asg.secondAssignmentEnd || asg.secondAssignmentStart > asg.firstAssignmentEnd;
                    if(!notOverlapping) {
                        counter.addAndGet(1);
                    }
                });
        System.out.println(counter.intValue());
    }

    private static void part1() {
        AtomicInteger counter = new AtomicInteger();
        FileReader.read("Day4").stream()
                .map(AssignmentsLine::new)
                .forEach(asg -> {
                    boolean overlapping = (asg.firstAssignmentStart <= asg.secondAssignmentStart && asg.firstAssignmentEnd >= asg.secondAssignmentEnd)
                            || (asg.secondAssignmentStart <= asg.firstAssignmentStart && asg.secondAssignmentEnd >= asg.firstAssignmentEnd);
                    if (overlapping) {
                        counter.addAndGet(1);
                    }
                });
        System.out.println(counter.intValue());
    }


    @Getter
    private static class AssignmentsLine {
        private final int firstAssignmentStart;
        private final int firstAssignmentEnd;
        private final int secondAssignmentStart;
        private final int secondAssignmentEnd;

        public AssignmentsLine(String line) {
            var assignments = line.split(",");
            var firstAssignment = assignments[0].split("-");
            var secondAssignment = assignments[1].split("-");

            firstAssignmentStart = Integer.parseInt(firstAssignment[0]);
            firstAssignmentEnd = Integer.parseInt(firstAssignment[1]);
            secondAssignmentStart = Integer.parseInt(secondAssignment[0]);
            secondAssignmentEnd = Integer.parseInt(secondAssignment[1]);
        }

    }
}
