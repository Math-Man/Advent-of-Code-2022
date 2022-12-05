package day.day5;

import common.FileReader;
import java.util.*;

/*
    This one was fun but I didn't spend much time thinking on how to move multiple crates at once in a single iteration.
    Maybe using a different data structure would have helped but the puzzle just looked so 'stack-able'. 🏗
    Also, I think I've spent more time on parsing the input than actually solving the puzzle.
*/

public class Day5 {

    private static final boolean PART1 = false;

    public static void main(String[] args) {
        final Map<Integer, Stack<Character>> cargoStacks = new HashMap<>();
        FileReader.read("Day5").forEach(line -> {
            if (line.startsWith("move")) {
                var words = line.split(" ");
                int count = Integer.parseInt(words[1]);
                int from = Integer.parseInt(words[3]) - 1;
                int to = Integer.parseInt(words[5]) - 1;

                if (PART1) {
                    moveCrate(cargoStacks, from, to, count);
                } else {
                    moveCrateMultiple(cargoStacks, from, to, count);
                }

            } else if (line.contains("[")) {
                var lineCharArray = line.toCharArray();
                for (int i = 1; i < lineCharArray.length; i += 4) {
                    if (lineCharArray[i] != ' ') {
                        int stackIndex = i / 4;
                        cargoStacks.computeIfAbsent(stackIndex, index -> new Stack<>());
                        cargoStacks.get(stackIndex).push(lineCharArray[i]);
                    }
                }
            } else if (line.isEmpty()) { // reverse the stack once we are done building the map
                cargoStacks.forEach((integer, stack) -> Collections.reverse(stack));
            }
        });
        cargoStacks.forEach((integer, stack) -> System.out.print(stack.peek()));
    }


    private static void moveCrate(Map<Integer, Stack<Character>> cargoStacks, int target, int destination, int count) {
        for (int i = 0; i < count; i++) {
            cargoStacks.get(destination).push(cargoStacks.get(target).pop());
        }
    }

    private static void moveCrateMultiple(Map<Integer, Stack<Character>> cargoStacks, int target, int destination, int count) {
        Stack<Character> crane = new Stack<>();
        for (int i = 0; i < count; i++) {
            crane.push(cargoStacks.get(target).pop());
        }
        for (int i = 0; i < count; i++) {
            cargoStacks.get(destination).push(crane.pop());
        }

    }
}
