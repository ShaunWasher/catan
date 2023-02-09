package com.example.thesettlers;

import com.example.thesettlers.enums.TerrainType;
import com.opencsv.exceptions.CsvException;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import com.opencsv.CSVReader;
import java.io. * ;
import java.util.Scanner;

import static java.lang.Math.sqrt;

public class GameBoard {

    private int rowCount = 5;
    private double yStartOffset = 240;
    private Pane gameBoard;
    private Pane tilePane;
    private Pane circlePane;
    private String mapType;
    private List<String> terrainList;
    private List<Integer> valueList;
    private List<Integer> indices;
    private final static double r = 60; // the inner radius from hexagon center to outer corner
    private final static double n = sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis

    public GameBoard(String mapType) throws IOException, URISyntaxException {
        this.mapType = mapType;
        gameBoard = new Pane();
        tilePane = new Pane();
        circlePane = new Pane();
        terrainList = new ArrayList<>();
        valueList = new ArrayList<>();

        URL fileUrl = getClass().getResource("startingmap.csv");
        File file = new File(fileUrl.toURI());
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        String splitBy = ",";
        while ((line = br.readLine()) != null) {
            String[] data = line.split(splitBy);    // use comma as separator
            terrainList.add(data[0]);
            valueList.add(Integer.parseInt(data[1]));
        }

        indices = new ArrayList<>();
        for (int i = 0; i < terrainList.size(); i++) {
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
                double xCoord = x * (n * 2) + (y % 2) * n + xStartOffset;
                double yCoord = y * (r * 2) * 0.75 + yStartOffset;
                if (Objects.equals(mapType, "Starting Map")) {
                    Tile tile = new Tile(xCoord, yCoord, terrainList.get(count), valueList.get(count));
                    tilePane.getChildren().add(tile.getHexagon());
                    circlePane.getChildren().add(tile.getValueLabel());
                    count++;

                } else if (Objects.equals(mapType, "Random")) {
                    // Does not work yet
                    /*java.util.Collections.shuffle(indices);
                    int randomIndex = indices.remove(indices.size() - 1);
                    String randomTile = terrainList.get(randomIndex);
                    int randomValue = valueList.get(randomIndex);
                    Tile tile = new Tile(xCoord, yCoord,randomTile,randomIndex);
                    tilePane.getChildren().add(tile.getHexagon());
                    circlePane.getChildren().add(tile.getValueLabel());*/
                }

            }
        }
        /*for (int y = 0; y < rowCount; y++) {

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
                double xCoord = x * (n * 2) + (y % 2) * n + xStartOffset;
                double yCoord = y * (r * 2) * 0.75 + yStartOffset + 30;
                Tile tile = new Tile(xCoord, yCoord, terrainList.get(count), valueList.get(count));
                tilePane.getChildren().add(tile.getValueLabel());
                count++;
            }
        }*/
        gameBoard.getChildren().addAll(circlePane, tilePane);
        circlePane.toFront();
        return gameBoard;
    }
}