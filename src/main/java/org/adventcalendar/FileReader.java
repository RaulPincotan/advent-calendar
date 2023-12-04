package org.adventcalendar;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;

@UtilityClass
public class FileReader {

    public List<String> readTxtFile(String sourceFile) {
        try (Stream<String> fileStream = lines(Path.of(sourceFile))) {
            return fileStream
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
