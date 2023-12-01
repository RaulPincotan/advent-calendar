package org.adventcalendar;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.lines;

public class Day1_1 {

    private static final String SOURCE_FILE = "src/main/resources/coordinates.txt";

    public static void main(String[] args) {
        System.out.println("calibrateCoordinates() = " + calibrateCoordinates());
    }

    private static int calibrateCoordinates() {
        List<String> fileElements = readCoordinates();

        return fileElements.stream()
                .mapToInt(Day1_1::getCoordinates)
                .sum();
    }

    private static int getCoordinates(String row) {
        int firstNumber = 0;
        int lastNumber = 0;

        for (int i = 0, j = row.length() - 1; i < row.length(); i++) {
            boolean isDigitFirst = Character.isDigit(row.charAt(i));
            boolean isDigitLast = Character.isDigit(row.charAt(j));
            if (isDigitFirst && firstNumber == 0) {
                firstNumber = Integer.parseInt(row.substring(i, i + 1));
            }
            if (isDigitLast && lastNumber == 0) {
                lastNumber = Integer.parseInt(row.substring(j, j + 1));
            }
            j--;
        }

        return firstNumber * 10 + lastNumber;
    }


    private static List<String> readCoordinates() {
        try {
            return lines(Path.of(SOURCE_FILE))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}