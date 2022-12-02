package common;

import lombok.SneakyThrows;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader
{
    @SneakyThrows
    public static List<String> read(String fileName) {
        URL url = FileReader.class.getClassLoader().getResource(String.format("PuzzleInput/%s.txt", fileName));
        return Files.lines(Paths.get(url.toURI())).collect(Collectors.toList());
    }

}
