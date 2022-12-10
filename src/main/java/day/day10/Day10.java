package day.day10;

import common.FileReader;
import lombok.Value;

/**
 * I DID THIS WITHOUT COFFEE!
 */

public class Day10 {

    private static int cycle = 0;
    private static int registerX = 1;
    private static int part1Sum = 0;

    public static void main(String[] args) {
        //part1Main();
        part2Main();
    }

    private static void part1Main() {
        FileReader.read("Day10").stream().forEach(line -> {
            CommandResponse response = parseCommand(line);
            for(int i = 0; i < response.cyclesToComplete; i++) {
                cycle++;
                if(cycle > 0 && (cycle == 20 || (cycle+20) % 40 == 0)) {
                    part1Sum += cycle * registerX;
                    System.out.println(cycle + " " + registerX + " " + (cycle * registerX) + " " + part1Sum);
                }
            }
            registerX += response.returnValue;
        });

        System.out.println(part1Sum);
    }

    private static void part2Main() {
        final boolean[][] screen = new boolean[6][40];
        FileReader.read("Day10").stream().forEach(line -> {
            CommandResponse response = parseCommand(line);
            for(int i = 0; i < response.cyclesToComplete; i++) {

                int column = cycle % 40;
                int row = cycle / 40;

                if(Math.abs(column - registerX) <= 1) {
                    screen[row][column] = true;
                }
                System.out.print(screen[row][column] ? "#" : ".");

                cycle++;

                if(cycle > 0 && cycle % 40 == 0) {
                    System.out.println();
                }
            }
            registerX += response.returnValue;
        });

    }

    private static CommandResponse parseCommand(String command) {
        String[] params = command.split(" ");
        switch (params[0]) {
            case "noop":
                return new CommandResponse(1, 0);
            case "addx":
                return new CommandResponse(2, Integer.parseInt(params[1]));
            default:
                throw new IllegalStateException("What?");
        }
    }


    @Value
    private static class CommandResponse {
        int cyclesToComplete;
        int returnValue;
    }

}
