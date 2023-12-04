package org.adventcalendar;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 {
    private static final int TOTAL_RED_CUBE_IN_THE_BAG = 12;
    private static final int TOTAL_GREEN_CUBE_IN_THE_BAG = 13;
    private static final int TOTAL_BLUE_CUBE_IN_THE_BAG = 14;
    private static final String SOURCE_FILE = "src/main/resources/bags.txt";

    public static void main(String[] args) {

        System.out.println(FileReader.readTxtFile(SOURCE_FILE)
                .stream()
                .map(Day2::convertToGame)
                .filter(Day2::isRevealValid)
                .mapToInt(Game::id)
                .sum());
    }

    private static boolean isRevealValid(Game game) {
        List<Cube> cubes = game.reveals()
                .stream()
                .map(Reveal::cubes)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        int totalGreenCubesOfGame = getCubesOfColour(cubes, "green");
        int totalRedCubesOfGame = getCubesOfColour(cubes, "red");
        int totalBlueCubesOfGame = getCubesOfColour(cubes, "blue");

        return totalGreenCubesOfGame <= TOTAL_GREEN_CUBE_IN_THE_BAG &&
                totalRedCubesOfGame <= TOTAL_RED_CUBE_IN_THE_BAG &&
                totalBlueCubesOfGame <= TOTAL_BLUE_CUBE_IN_THE_BAG;
    }

    private static int getCubesOfColour(List<Cube> cubes, String color) {
        return cubes.stream()
                .filter(cube -> cube.cubeColor().equalsIgnoreCase(color.trim()))
                .mapToInt(Cube::cubeNumber)
                .max()
                .getAsInt();
    }

    private static Game convertToGame(String gameRow) {
        List<String> rowElements = convertRowToList(":", gameRow);
        int gameId = Integer.parseInt(convertRowToList(" ", rowElements.get(0)).get(1).strip());

        String revealString = rowElements.get(1);
        List<Reveal> reveals = convertRowToList(";", revealString.strip())
                .stream()
                .map(Day2::convertToReveal)
                .collect(Collectors.toList());

        return new Game(gameId, reveals);
    }


    private static Reveal convertToReveal(String revealString) {
        List<Cube> cubes = convertRowToList(",", revealString.strip())
                .stream()
                .map(Day2::convertToCube)
                .collect(Collectors.toList());

        return new Reveal(cubes);
    }

    private static Cube convertToCube(String cubeString) {
        List<String> cubeStrings = convertRowToList(" ", cubeString.strip());

        int cubeNumber = Integer.parseInt(cubeStrings.get(0));
        String cubeColor = cubeStrings.get(1);

        return new Cube(cubeNumber, cubeColor);
    }

    private static List<String> convertRowToList(String character, String row) {
        return Arrays.asList(row.split(character));
    }
}


record Game(int id, List<Reveal> reveals) {
}

record Reveal(List<Cube> cubes) {
}

record Cube(int cubeNumber, String cubeColor) {
}
