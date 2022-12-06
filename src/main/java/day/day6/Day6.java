package day.day6;

import common.FileReader;

import java.util.ArrayList;
import java.util.List;

/*
    Literally took 5 minutes to write and somehow, it just works?
*/

public class Day6 {

    private static final int DISTINCT_ARRAY_LENGTH = 14;

    public static void main(String[] args) {
        var lineArray = FileReader.read("Day6").stream().findFirst().get().toCharArray();
        System.out.println(findUniqueSubsetIndex(lineArray, DISTINCT_ARRAY_LENGTH));
    }

    private static int findUniqueSubsetIndex(char[] input, int length) {
        List<Character> swap = new ArrayList<>();
        for (int i = 0; i < input.length; i++) {
            if (swap.size() < length) {
                swap.add(input[i]);
            } else {
                if (isDistinctArray(swap)) {
                    System.out.println(swap);
                    return i;
                }
                int swapIndex = i % length;
                swap.set(swapIndex, input[i]);
            }
        }
        return 0;
    }

    public static <T> boolean isDistinctArray(final List<T> values) {
        return values.stream().distinct().count() == values.size(); // Is this cheating?
    }

}
