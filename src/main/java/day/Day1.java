package day;

import common.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Day1 {
    public static void main(String[] args) {
        final long[] max = new long[]{0, 0};
        List<Long> elves = new ArrayList<>();
        FileReader.read("Day1").stream()
                .map(s -> (s.isEmpty() ? -1 : Long.parseLong(s)))
                .forEach(calories -> {
                    if (calories > 0) {
                        max[0] += calories;
                    } else {
                        elves.add(max[0]);
                        max[1] = Math.max(max[0], max[1]);
                        max[0] = 0;
                    }
                });
        System.out.println(max[1]);
        System.out.println(elves.stream().sorted((o1, o2) -> Long.compare(o2,o1)).limit(3).reduce(Long::sum).get());
    }
}
