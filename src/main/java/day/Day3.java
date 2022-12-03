package day;

import common.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Day3
{
    public static void main(String[] args) {
        part2();
    }

    private static void part1()
    {
        var res = FileReader.read("Day3").stream().map(rucksack -> {
            var sackAsNumbers = rucksack.chars().map(ch -> ((int)ch) < 'a' ? ch - 'A' + 27 : ch - 'a' + 1).boxed().collect(Collectors.toList());
            var firstCompartment = sackAsNumbers.stream().limit(sackAsNumbers.size()/2).collect(Collectors.toList());
            var secondCompartment = sackAsNumbers.stream().skip(sackAsNumbers.size()/2).collect(Collectors.toList());
            return firstCompartment.stream().filter(secondCompartment::contains).limit(1).collect(Collectors.toList()).get(0);
        }).reduce(Integer::sum).stream().collect(Collectors.toList());
        System.out.println(res);
    }

    private static void part2()
    {
        final AtomicLong sum = new AtomicLong();
        final List<List<Integer>> elves = new ArrayList<>();
        FileReader.readWithIndex("Day3").forEach(rucksack -> {
            int elfIndex = (int)(rucksack.getIndex() % 3);
            var sackAsNumbers = rucksack.getLine().chars().map(ch -> ((int)ch) < 'a' ? ch - 'A' + 27 : ch - 'a' + 1).boxed().collect(Collectors.toList());
            if(elves.size() == 3) {
                sum.addAndGet(elves.get(0).stream().filter(elves.get(1)::contains).filter(elves.get(2)::contains).limit(1).collect(Collectors.toList()).get(0));
                elves.clear();
            }
            elves.add(elfIndex, sackAsNumbers);
        });
        sum.addAndGet(elves.get(0).stream().filter(elves.get(1)::contains).filter(elves.get(2)::contains).limit(1).collect(Collectors.toList()).get(0));
        System.out.println(sum);
    }
}
