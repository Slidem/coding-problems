package skyline;

import javafx.util.Pair;

import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alexandru Mihai
 */
public class Skyline {

    private static final Pattern BUILDINGS_REGEX = Pattern.compile("(\\(\\d+,\\s\\d+,\\s\\d+\\))");

    private List<Building> buildings;

    private int[] heights;

    private List<Pair<Integer, Integer>> skyline;

    private String[][] skylineGui;

    private int maxX;

    private int maxHeight;

    public Skyline(String input) {
        System.out.println("Parsing input...");
        parseInput(input);
        System.out.println("Input parsed...");

        System.out.println("Computing skyline");
        long start = Instant.now().toEpochMilli();
        computeSkyline();
        long end = Instant.now().toEpochMilli();
        System.out.println("Skyline computed! Finished in " + (end - start) + " milliseconds");
    }

    public void printResult() {
        System.out.println(skyline);
    }

    public void printSkyline(){
        for (int height = 0; height <= maxHeight; height++) {
            String[] line = skylineGui[height];
            for (int x = 0; x <= maxX; x++) {
                String toPrint = skylineGui[height][x];
                if (Objects.nonNull(toPrint)) {
                    System.out.print(toPrint);
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private void parseInput(String input) {
        buildings = new ArrayList<>();
        Matcher buildingsMatcher = BUILDINGS_REGEX.matcher(input);
        while (buildingsMatcher.find()) {
            String buildingStr = buildingsMatcher.group(1).replaceAll("[()\\s]", "");
            String[] buildingValues = buildingStr.split(",");
            int start = Integer.parseInt(buildingValues[0]);
            int end = Integer.parseInt(buildingValues[1]);
            int height = Integer.parseInt(buildingValues[2]);

            maxX = Math.max(maxX, start);
            maxX = Math.max(maxX, end);
            maxHeight = Math.max(maxHeight, height);

            buildings.add(new Building(start,end,height));
        }
    }

    private void computeSkyline() {
        skyline = new ArrayList<>();
        skylineGui = new String[maxHeight+1][maxX+1];
        heights = new int[maxX+1];
        for (Building building : buildings) {
            for (int x = building.startX; x <= building.endX; x++) {
                if (building.height > heights[x]) {
                    heights[x] = building.height;
                }
            }
        }

        int currentHeight = 0;

        for (int x = 0; x < heights.length; x++) {
            int height = heights[x];
            if (currentHeight != height) {
                int xToAdd = x;
                if (currentHeight > height) {
                    xToAdd--;
                }
                skyline.add(new Pair<>(xToAdd, height));
                int drawStart = maxHeight - Math.max(currentHeight, height);
                int drawEnd = maxHeight - Math.min(currentHeight, height);
                for (int h = drawStart; h < drawEnd; h++) {
                    skylineGui[h][xToAdd] = "|";
                }
                currentHeight = height;
            } else {
                skylineGui[maxHeight - height][x] = "-";
            }

        }

        skyline.add(new Pair<>(maxX, 0));
    }

    static class Building{

        int startX;

        int endX;

        int height;

        Building(int startX, int endX, int height) {
            this.startX = startX;
            this.endX = endX;
            this.height = height;
        }
    }

    public static void main(String[] args) {
        Skyline skyline = new Skyline("[(0, 15, 3), (4, 11, 5), (19, 23, 4)]");
        skyline.printResult();
        skyline.printSkyline();
    }

}

