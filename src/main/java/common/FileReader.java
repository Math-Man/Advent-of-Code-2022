package common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class FileReader
{
    @SneakyThrows
    public static List<String> read(String fileName) {
        URL url = FileReader.class.getClassLoader().getResource(String.format("PuzzleInput/%s.txt", fileName));
        return Files.lines(Paths.get(url.toURI())).collect(Collectors.toList());
    }

    @SneakyThrows
    public static List<IndexedLine> readWithIndex(String fileName) {
        AtomicLong index = new AtomicLong(-1);
        URL url = FileReader.class.getClassLoader().getResource(String.format("PuzzleInput/%s.txt", fileName));
        return Files.lines(Paths.get(url.toURI())).map(line -> new IndexedLine(index.addAndGet(1), line)).collect(Collectors.toList());
    }

    @AllArgsConstructor
    @Getter
    public static class IndexedLine {
        long index;
        String line;
    }

}
