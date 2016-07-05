package swen221.lab9.parallelStream3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class KeywordFinder {
    public static long count1(String keyword, Path file) {
        // record the position where each occurrence appears in the file
        try (Stream<String> stream = Files.lines(file).parallel()) {
            return stream.reduce(0L, (count, line) -> {
                List<String> words = Arrays.asList(line.split(" "));
                long add = words.parallelStream().filter(s -> s.equals(keyword)).count();
                return count + add;
            }, Long::sum);
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public static long count2(String keyword, Path file) {
        // record the position where each occurrence appears in the file
        try (Stream<String> stream = Files.lines(file).parallel()) {
            Stream<String> s = stream.flatMap(l -> Stream.of(l.split(" ")));
            return s.reduce(0L, (count, string) -> {
                return string.equals(keyword) ? count + 1 : count;
            }, Long::sum);
        } catch (IOException e) {
            throw new Error(e);
        }
    }
}
