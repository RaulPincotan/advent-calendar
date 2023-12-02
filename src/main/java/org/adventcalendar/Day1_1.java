package org.adventcalendar;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.lines;

public class Day1_1 {

    private static final String SOURCE_FILE = "src/main/resources/coordinates2.txt";


    public static void main(String[] args) {
        System.out.println("calibrateCoordinates() = " + calibrateCoordinates());
    }


    private static int calibrateCoordinates() {
        return readCoordinates().stream()
                .mapToInt(Day1_1::getCoordinates)
                .sum();
    }


    private static int getCoordinates(String row) {
        int firstDigitCoordinate = 0;
        int lastDigitCoordinate = 0;
        String digits = convertLettersToDigits(row);

        for (int i = 0, j = digits.length() - 1; i < digits.length(); i++) {
            if (firstDigitCoordinate == 0) {
                firstDigitCoordinate = Integer.parseInt(digits.substring(i, i + 1));
            }
            if (lastDigitCoordinate == 0) {
                lastDigitCoordinate = Integer.parseInt(digits.substring(j, j + 1));
            }
            j--;
        }

        return firstDigitCoordinate * 10 + lastDigitCoordinate;
    }


    private static String convertLettersToDigits(String row) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < row.length(); i++) {
            String key = row.substring(i, i + 1);
            for (int j = i + 1; j < row.length(); j++) {
                key += row.substring(j, j + 1);
                if (mapToDigit().containsKey(key)) {
                    result.append(mapToDigit().get(key));
                    break;
                }
            }
            if (Character.isDigit(row.charAt(i))) {
                result.append(row.charAt(i));
            }
        }

        return result.toString();
    }


    private static Map<String, Integer> mapToDigit() {
        return Map.of(
                "one", 1,
                "two", 2,
                "three", 3,
                "four", 4,
                "five", 5,
                "six", 6,
                "seven", 7,
                "eight", 8,
                "nine", 9
        );
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