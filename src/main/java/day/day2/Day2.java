package day.day2;

import common.FileReader;

import java.util.stream.Collectors;


public class Day2 {

    private static final long ROCK = 1;
    private static final long PAPER = 2;
    private static final long SCISSORS = 3;
    private static final long DRAW = 3;
    private static final long WIN = 6;

    public static void main(String[] args) {
        part1();
        part2();

    }

    /**
     * There is probably a better way of doing this.
     */
    private static void part2() {
        var v = FileReader.read("Day2").stream()
                .map(line -> {
                    var strategy = line.replace(" ", "").toCharArray();
                    int winMod = 0;
                    int selection = 0;
                    if(strategy[1] == 'Z') { // Win
                        if(strategy[0] == 'A') {
                            selection = 2;
                        } else if (strategy[0] == 'B') {
                            selection = 3;
                        } else {
                            selection = 1;
                        }
                        winMod = 6;
                    } else if(strategy[1] == 'Y') { // Draw
                        if(strategy[0] == 'A') {
                            selection = 1;
                        } else if (strategy[0] == 'B') {
                            selection = 2;
                        } else {
                            selection = 3;
                        }
                        winMod = 3;
                    } else {  // Lose
                        if(strategy[0] == 'A') {
                            selection = 3;
                        } else if (strategy[0] == 'B') {
                            selection = 1;
                        } else {
                            selection = 2;
                        }
                    }

                    return winMod + selection;

                }).collect(Collectors.toList());

        System.out.println(v);
        System.out.println(v.stream().reduce(Integer::sum));
    }

    private static void part1() {
        var v = FileReader.read("Day2").stream()
                .map(line -> {
                    var strategy = line.replace(" ", "").toCharArray();
                    int winMod = 0;
                    if((strategy[0] - 'A') == (strategy[1] - 'X')) { // Both same
                        winMod = 3;
                    } else if (
                            (strategy[0] == 'A' && strategy[1] == 'Y') ||
                            (strategy[0] == 'B' && strategy[1] == 'Z') ||
                            (strategy[0] == 'C' && strategy[1] == 'X')) {
                        winMod = 6;
                    }

                    return winMod + (strategy[1] - 'X' + 1);
                }).collect(Collectors.toList());

        System.out.println(v);
        System.out.println(v.stream().reduce(Integer::sum));
    }
}
