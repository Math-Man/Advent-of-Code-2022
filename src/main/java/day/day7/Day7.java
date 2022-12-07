package day.day7;

import common.FileReader;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
 * Wanted to solve this with a tree, looked very tree-able 🌳
 *
 */

public class Day7 {

    public static Map<Node, Long> directorySizeMap = new HashMap<>();

    public static Node root = null;
    public static Node current = null;

    public static void main(String[] args) {
        root = new Node(false, "root", 0, null);
        current = root;
        FileReader.read("Day7").forEach(Day7::parseCommand);
        calculateDirectorySize();
        System.out.println(part1());
        System.out.println(part2());
    }

    public static long part1() {
        return directorySizeMap.values().stream().filter(size -> size < 100000L).reduce(Long::sum).get();
    }

    public static long part2() {
        long unusedSpace = 70000000 - directorySizeMap.get(root);
        long requiredSpaceToSave = 30000000 - unusedSpace;
        return directorySizeMap.values().stream().sorted().filter(size -> size > requiredSpaceToSave).findFirst().get();
    }

    public static void calculateDirectorySize() {
        long size = getDirSize(root);
        directorySizeMap.put(root, size);
    }

    public static long getDirSize(Node start) {
        long sum = 0L;
        for (Node child : start.getChildren()) {
            if (child.isFile()) {
                sum += child.getNodeData();
            } else {
                var dirSize = getDirSize(child);
                directorySizeMap.put(child, dirSize);
                sum += dirSize;
            }
        }
        return sum;
    }

    public static void parseCommand(String line) {
        if (line.startsWith("$")) {
            String[] command = line.split(" ");
            switch (command[1]) {
                case "ls":
                    break;
                case "cd":
                    if (command[2].equals("/")) {
                        current = root;
                    } else if (command[2].equals("..")) {
                        current = current.getParent();
                    } else {
                        current = current.getChildren().stream()
                                .filter(node -> node.getNodeName().equals(command[2]))
                                .findFirst().orElseThrow(() -> new IllegalStateException("What?"));
                    }
                    break;
                default:
                    System.out.println("Go away, I don't like you! We are not friends anymore!");
                    throw new IllegalStateException("😡");
            }
        } else {
            String[] result = line.split(" ");
            String directoryName = result[1];
            if (result[0].startsWith("dir")) {
                current.getChildren().add(new Node(false, directoryName, 0, current));
            } else {
                current.getChildren().add(new Node(true, directoryName, Long.parseLong(result[0]), current));
            }
        }
    }

    @RequiredArgsConstructor
    @Getter
    public static class Node {
        private final boolean isFile;
        private final String nodeName;
        private final long nodeData;
        private final Node parent;
        private final List<Node> children = new ArrayList<>();
    }

}





























// What it feels like to fix bugs in production
/*
░░░░░▄▄▄▄▀▀▀▀▀▀▀▀▄▄▄▄▄▄░░░░░░░
░░░░░█░░░░▒▒▒▒▒▒▒▒▒▒▒▒░░▀▀▄░░░░
░░░░█░░░▒▒▒▒▒▒░░░░░░░░▒▒▒░░█░░░
░░░█░░░░░░▄██▀▄▄░░░░░▄▄▄░░░░█░░
░▄▀▒▄▄▄▒░█▀▀▀▀▄▄█░░░██▄▄█░░░░█░
█░▒█▒▄░▀▄▄▄▀░░░░░░░░█░░░▒▒▒▒▒░█
█░▒█░█▀▄▄░░░░░█▀░░░░▀▄░░▄▀▀▀▄▒█
░█░▀▄░█▄░█▀▄▄░▀░▀▀░▄▄▀░░░░█░░█░
░░█░░░▀▄▀█▄▄░█▀▀▀▄▄▄▄▀▀█▀██░█░░
░░░█░░░░██░░▀█▄▄▄█▄▄█▄████░█░░░
░░░░█░░░░▀▀▄░█░░░█░█▀██████░█░░
░░░░░▀▄░░░░░▀▀▄▄▄█▄█▄█▄█▄▀░░█░░
░░░░░░░▀▄▄░▒▒▒▒░░░░░░░░░░▒░░░█░
░░░░░░░░░░▀▀▄▄░▒▒▒▒▒▒▒▒▒▒░░░░█░
░░░░░░░░░░░░░░▀▄▄▄▄▄░░░░░░░░█░░
*/