package com.example.thesettlers;

import com.example.thesettlers.enums.TerrainType;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.sqrt;

public class GameBoard {

    private int rowCount = 5;
    private double yStartOffset = 240;
    private Pane gameBoard;
    private Pane tilePane;
    private Pane circlePane;
    private String mapType;
    private List<TerrainType> tileList;
    private List<Integer> indices;
    private final static double r = 60; // the inner radius from hexagon center to outer corner
    private final static double n = sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis
    public GameBoard(String mapType) {
        this.mapType = mapType;
        gameBoard = new Pane();
        tilePane = new Pane();
        circlePane = new Pane();

        tileList = new ArrayList<>();
        tileList.add(TerrainType.MOUNTAINS);
        tileList.add(TerrainType.PASTURE);
        tileList.add(TerrainType.FOREST);
        tileList.add(TerrainType.FIELDS);
        tileList.add(TerrainType.HILLS);
        tileList.add(TerrainType.PASTURE);
        tileList.add(TerrainType.HILLS);
        tileList.add(TerrainType.FIELDS);
        tileList.add(TerrainType.FOREST);
        tileList.add(TerrainType.DESERT);
        tileList.add(TerrainType.FOREST);
        tileList.add(TerrainType.MOUNTAINS);
        tileList.add(TerrainType.FOREST);
        tileList.add(TerrainType.MOUNTAINS);
        tileList.add(TerrainType.FIELDS);
        tileList.add(TerrainType.PASTURE);
        tileList.add(TerrainType.HILLS);
        tileList.add(TerrainType.FIELDS);
        tileList.add(TerrainType.PASTURE);

        indices = new ArrayList<>();
        for (int i = 0; i < tileList.size(); i++) {
            indices.add(i);
        }
    }

    public Pane getBoard() {
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
                double xCoord = x * (r*2) + (y % 2) * n + xStartOffset;
                double yCoord = y * (n*2) * 0.75 + yStartOffset;
                if (Objects.equals(mapType, "Starting Map")) {
                    Tile tile = new Tile(xCoord, yCoord, tileList.get(count), 10);
                    tilePane.getChildren().add(tile.getHexagon());
                    count++;
                } else if (Objects.equals(mapType, "Random")) {
                    java.util.Collections.shuffle(indices);
                    int randomIndex = indices.remove(indices.size() - 1);
                    TerrainType randomElement = tileList.get(randomIndex);
                    Tile tile = new Tile(xCoord, yCoord, randomElement, 0);
                    tilePane.getChildren().add(tile.getHexagon());
                }

            }
        }
        /*for (int y = 0; y < rowCount; y++) {

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
                Circle circle = new Circle(xCoord,yCoord,12);
                Image img = new Image(this.getClass().getResource("img.png").toExternalForm());
                circle.setFill(new ImagePattern(img));
                circle.setStrokeWidth(4);
                circle.setStroke(Color.web("FFFFFF"));
                circlePane.getChildren().add(circle);
                }
            }*/

/*
        for (int y = 0; y < rowCount; y++) {

            int tilesPerRow;
            double xStartOffset;
            if (y == 0 || y == rowCount - 1) {
                tilesPerRow = 3;
                xStartOffset = 460 + (3 * ((sqrt(3) / 2) * r));
            } else if (y == 1 || y == rowCount - 2) {
                tilesPerRow = 4;
                xStartOffset = 460 + (((sqrt(3) / 2) * r));
            } else {
                tilesPerRow = 5;
                xStartOffset = 460 + (((sqrt(3) / 2) * r));
            }

            for (int x = 0; x < tilesPerRow; x++) {
                double xCoord = x * TILE_WIDTH + (y % 2) * n + xStartOffset;
                double yCoord = y * TILE_HEIGHT * 0.75 + yStartOffset+30;

                Circle circle = new Circle(xCoord,yCoord,15);
                Image img = new Image(this.getClass().getResource("img.png").toExternalForm());
                circle.setFill(new ImagePattern(img));
                circle.setStrokeWidth(9);
                circle.setStroke(Color.web("FFFFFF"));
                circlePane.getChildren().add(circle);
            }
        }*/



        gameBoard.getChildren().addAll(circlePane,tilePane);
        circlePane.toFront();
        return gameBoard;
    }
}