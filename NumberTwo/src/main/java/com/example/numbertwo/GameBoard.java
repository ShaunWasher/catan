package com.example.numbertwo;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.sqrt;

public class GameBoard {

    private int rowCount = 7;
    private double yStartOffset = 240;
    private final static double r = 60; // the inner radius from hexagon center to outer corner
    private final static double n = sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis
    private final static double TILE_HEIGHT = 2 * r;
    private final static double TILE_WIDTH = 2 * n;
    private AnchorPane tileMap;
    private String mapType;
    private List<String> tileList;
    private List<Integer> indices;
    private Random rand;

    public GameBoard(String mapType) {
        this.mapType = mapType;
        tileMap = new AnchorPane();
        tileList = new ArrayList<>();
        tileList.add("MOUNTAIN");
        tileList.add("PASTURE");
        tileList.add("FOREST");
        tileList.add("FIELDS");
        tileList.add("HILLS");
        tileList.add("PASTURE");
        tileList.add("HILLS");
        tileList.add("FIELDS");
        tileList.add("FOREST");
        tileList.add("DESERT");
        tileList.add("FOREST");
        tileList.add("MOUNTAIN");
        tileList.add("FOREST");
        tileList.add("MOUNTAIN");
        tileList.add("FIELDS");
        tileList.add("PASTURE");
        tileList.add("HILLS");
        tileList.add("FIELDS");
        tileList.add("PASTURE");

        indices = new ArrayList<>();
        for (int i = 0; i < tileList.size(); i++) {
            indices.add(i);
        }
        rand = new Random();
    }

    public AnchorPane getBoard(){
        int count = 0;
        for (int y = 0; y < rowCount; y++) {

            int tilesPerRow;
            double xStartOffset;
            if (y == 0 || y == rowCount - 1) {
                tilesPerRow = 3;
                xStartOffset = 460 + (2 * ((sqrt(3) / 2) * r));
            } else if (y == 1 || y == rowCount - 2) {
                tilesPerRow = 4;
                xStartOffset = 460;
            } else {
                tilesPerRow = 5;
                xStartOffset = 460;
            }

            for (int x = 0; x < tilesPerRow; x++) {
                double xCoord = x * TILE_WIDTH + (y % 2) * n + xStartOffset;
                double yCoord = y * TILE_HEIGHT * 0.75 + yStartOffset;
                if (Objects.equals(mapType, "Starting Map")) {
                    Polygon tile = new Tile(r, n, TILE_WIDTH, xCoord, yCoord, tileList.get(count));
                    tileMap.getChildren().add(tile);
                    count++;
                } else if (Objects.equals(mapType, "Random")) {
                    java.util.Collections.shuffle(indices);
                    int randomIndex = indices.remove(indices.size() - 1);
                    String randomElement = tileList.get(randomIndex);
                    Polygon tile = new Tile(r, n, TILE_WIDTH, xCoord, yCoord, randomElement);
                    tileMap.getChildren().add(tile);
                }

            }
        }
        return tileMap;

    }

}


