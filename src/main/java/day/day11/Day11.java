package day.day11;

import lombok.Data;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/*
    I gave up on part 2.
    Either there is probably a pattern I am not seeing or my method of approach was completely wrong from the start.
*/

public class Day11 {

    private static final boolean IS_PART_2 = false;

    private static final int MAX_ROUND = IS_PART_2 ? 10000 : 20;

    private static final Function<BigInteger, BigInteger> globalWorryLevelChange = IS_PART_2 ? (value -> value) : (value -> value.divide(BigInteger.valueOf(3)));

    private static List<Monkey> monkeys = new ArrayList<>() {{
        //Test Data:
//        add(new Monkey(new ArrayDeque<>(50) {{
//            add(new Item(79));
//            add(new Item(98));
//        }}, ((value) -> value.multiply(BigInteger.valueOf(19))), ((value) -> value.mod(BigInteger.valueOf(23)).compareTo(BigInteger.ZERO) == 0), 2, 3));
//        add(new Monkey(new ArrayDeque<>(50) {{
//            add(new Item(54));
//            add(new Item(65));
//            add(new Item(75));
//            add(new Item(74));
//        }}, ((value) -> value.add(BigInteger.valueOf(6))), ((value) -> value.mod(BigInteger.valueOf(19)).compareTo(BigInteger.ZERO) == 0), 2, 0));
//        add(new Monkey(new ArrayDeque<>(50) {{
//            add(new Item(79));
//            add(new Item(60));
//            add(new Item(97));
//        }}, ((value) -> value.multiply(value)), ((value) -> value.mod(BigInteger.valueOf(13)).compareTo(BigInteger.ZERO) == 0), 1, 3));
//        add(new Monkey(new ArrayDeque<>(50) {{
//            add(new Item(74));
//        }}, ((value) -> value.add(BigInteger.valueOf(3))), ((value) -> value.mod(BigInteger.valueOf(17)).compareTo(BigInteger.ZERO) == 0), 0, 1));


        //Real Data:
        add(new Monkey(new ArrayDeque<>(50) {{
            add(new Item(85));
            add(new Item(77));
            add(new Item(77));
        }}, ((value) -> value.multiply(BigInteger.valueOf(7))), ((value) -> value.mod(BigInteger.valueOf(19)).compareTo(BigInteger.ZERO) == 0), 6, 7));

        add(new Monkey(new ArrayDeque<>(50) {{
            add(new Item(80));
            add(new Item(99));
        }}, ((value) -> value.multiply(BigInteger.valueOf(11))), ((value) -> value.mod(BigInteger.valueOf(3)).compareTo(BigInteger.ZERO) == 0), 3, 5));

        add(new Monkey(new ArrayDeque<>(50) {{
            add(new Item(74));
            add(new Item(60));
            add(new Item(74));
            add(new Item(63));
            add(new Item(86));
            add(new Item(92));
            add(new Item(80));
        }}, ((value) -> value.add(BigInteger.valueOf(8))), ((value) -> value.mod(BigInteger.valueOf(13)).compareTo(BigInteger.ZERO) == 0), 0, 6));

        add(new Monkey(new ArrayDeque<>(50) {{
            add(new Item(71));
            add(new Item(58));
            add(new Item(93));
            add(new Item(65));
            add(new Item(80));
            add(new Item(68));
            add(new Item(54));
            add(new Item(71));
        }}, ((value) -> value.add(BigInteger.valueOf(7))), ((value) -> value.mod(BigInteger.valueOf(7)).compareTo(BigInteger.ZERO) == 0), 2, 4));

        add(new Monkey(new ArrayDeque<>(50) {{
            add(new Item(97));
            add(new Item(56));
            add(new Item(79));
            add(new Item(65));
            add(new Item(58));
        }}, ((value) -> value.add(BigInteger.valueOf(5))), ((value) -> value.mod(BigInteger.valueOf(5)).compareTo(BigInteger.ZERO) == 0), 2, 0));

        add(new Monkey(new ArrayDeque<>(50) {{
            add(new Item(77));
        }}, ((value) -> value.add(BigInteger.valueOf(4))), ((value) -> value.mod(BigInteger.valueOf(11)).compareTo(BigInteger.ZERO) == 0), 4, 3));

        add(new Monkey(new ArrayDeque<>(50) {{
            add(new Item(99));
            add(new Item(90));
            add(new Item(84));
            add(new Item(50));
        }}, ((value) -> value.multiply(value)), ((value) -> value.mod(BigInteger.valueOf(17)).compareTo(BigInteger.ZERO) == 0), 7, 1));

        add(new Monkey(new ArrayDeque<>(50) {{
            add(new Item(50));
            add(new Item(66));
            add(new Item(61));
            add(new Item(92));
            add(new Item(64));
            add(new Item(78));
        }}, ((value) -> value.add(BigInteger.valueOf(3))), ((value) -> value.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) == 0), 5, 1));

    }};

    public static void main(String[] args) {
        for(int i = 0; i < MAX_ROUND; i++) {
            for (Monkey monkey : monkeys) {
                while (monkey.getItems().size() > 0) {
                    monkey.inspectNextItem(monkeys);
                }
            }
            System.out.println("Round " + i + "complete");
        }

        // Find result for most active monkeys
        var rs = monkeys.stream()
                .map(monkey -> monkey.timesInspectedItems)
                .sorted((o1, o2) -> Long.compare(o2, o1))
                .limit(2)
                .reduce((long1, long2) -> long1 * long2).get();
        System.out.println(rs);
    }


    @Data
    private static class Monkey {
        private final Queue<Item> items;
        private final Function<BigInteger, BigInteger> inspectOperation;
        private final Predicate<BigInteger> test;
        private final int trueMonkeyIndex;
        private final int falseMonkeyIndex;

        private long timesInspectedItems = 0;

        public void inspectNextItem(List<Monkey> monkeys) {
            if(getItems().size() > 0) {
                Item currentItem = getItems().poll();
                currentItem.setWorryLevel(getInspectOperation().apply(currentItem.getWorryLevel()));
                currentItem.setWorryLevel(globalWorryLevelChange.apply(currentItem.getWorryLevel()));
                if(getTest().test(currentItem.getWorryLevel())) {
                    giveItem(monkeys, getTrueMonkeyIndex(), currentItem);
                } else {
                    giveItem(monkeys, getFalseMonkeyIndex(), currentItem);
                }
                timesInspectedItems++;
            }
        }

        private void giveItem(List<Monkey> monkeys, int monkeyIndex, Item item) {
            monkeys.get(monkeyIndex).getItems().add(item);
        }

    }

    @Data
    private static class Item {
        private BigInteger worryLevel;

        public Item(int level) {
            worryLevel = BigInteger.valueOf(level);
        }

    }
}
