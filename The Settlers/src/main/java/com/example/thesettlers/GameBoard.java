package com.example.thesettlers;

import javafx.scene.layout.Pane;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io. * ;
import static java.lang.Math.sqrt;

public class GameBoard {

    private Pane gameBoard;
    private Pane tilePane;
    private Pane labelPane;
    private Pane settlementPane;
    private Pane roadPane;
    private String mapType;
    private List<String> terrainList;
    private List<Integer> valueList;
    private double yStartOffset = 240;
    private final static double r = 60; // the inner radius from hexagon center to outer corner
    private final static double n = sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis

    public GameBoard(String mapType) throws IOException, URISyntaxException {
        this.mapType = mapType;
        gameBoard = new Pane();
        tilePane = new Pane();
        labelPane = new Pane();
        settlementPane = new Pane();
        roadPane = new Pane();
        terrainList = new ArrayList<>();
        valueList = new ArrayList<>();

        URL fileUrl = getClass().getResource("startingmap.csv");
        File file = new File(fileUrl.toURI());
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        String splitBy = ",";
        while ((line = br.readLine()) != null) {
            String[] data = line.split(splitBy);
            terrainList.add(data[0]);
            valueList.add(Integer.parseInt(data[1]));
        }
    }
    public Pane getBoard() {

        int[] tilesPerRowValues = {3, 4, 5, 4, 3};
        double[] tilesxStartOffsetValue = {(460 + (2 * n)), 460, 460, 460, (460 + (2 * n))};

        int count = 0;
        for (int y = 0; y < 5; y++) {
            int tilesPerRow = tilesPerRowValues[y];
            double tilesxStartOffset = tilesxStartOffsetValue[y];
            for (int x = 0; x < tilesPerRow; x++) {
                double xCoord = (x * (n * 2) + (y % 2) * n) + tilesxStartOffset;
                double yCoord = (y * (r * 2) * 0.75) + yStartOffset;
                Tile tile = new Tile(xCoord, yCoord, terrainList.get(count), valueList.get(count));
                tilePane.getChildren().add(tile.getHexagon());
                labelPane.getChildren().add(tile.getValueLabel());
                count++;
            }
        }

        int[] settlementsPerRowValues = {3,4,4,5,5,6,6,5,5,4,4,3};
        double[] settlementxStartOffsetValue = {460 + (2 * n),460,460+n,460-n,460,460-(2*n),460-n,460-n,460,460,460+n,460+n};

        for (int y = 0; y < 12; y++) {
            int settlementsPerRow = settlementsPerRowValues[y];
            double settlementxStartOffset = settlementxStartOffsetValue[y];
            for (int x = 0; x < settlementsPerRow; x++) {
                double xCoord = x * (n * 2) + (y % 2) * n + settlementxStartOffset;
                double yCoord;
                if (y % 2 == 0) {
                    yCoord = (y / 2) * (r * 2) * 0.75 + yStartOffset;
                } else {
                    yCoord = ((y - 1) / 2) * (r * 2) * 0.75 + yStartOffset + 30;
                }
                Settlement settlement = new Settlement(xCoord + n - 17.5, yCoord - 47.5);
                settlementPane.getChildren().add(settlement.getIcon());
            }
        }
        int[] roadsPerRowValues = {6, 4, 8, 5, 10, 6, 10, 5, 8, 4, 6};
        double[] roadxStartOffsetValue = {460 + (2.5 * n), 460 + (2 * n), 460 + (1.5 * n), 460 + n, 460 + (0.5 * n), 460, 460 + (0.5 * n), 460 + n, 460 + (1.5 * n), 460 + (2 * n), 460 + (2.5 * n)};

        for (int y = 0; y < 11; y++) {
            int version;
            double xCoord;
            double yCoord;
            int roadsPerRow = roadsPerRowValues[y];
            double roadxStartOffset = roadxStartOffsetValue[y];
            for (int x = 0; x < roadsPerRow; x++) {
                yCoord = (y * 45) + yStartOffset - 15;
                if (y % 2 == 0) {
                    xCoord = (x * (n)) + roadxStartOffset;
                    if (y < 6) {
                        if (x % 2 == 0) {
                            version = 1;
                        } else {
                            version = 2;
                        }
                    } else {
                        if (x % 2 == 0) {
                            version = 2;
                        } else {
                            version = 1;
                        }
                    }
                } else {
                    xCoord = (x * (2 * n)) + roadxStartOffset;
                    version = 3;
                }
                Road road = new Road(xCoord - 22.5, yCoord - 22.5, version);
                roadPane.getChildren().add(road.getIcon());
            }
        }

        gameBoard.getChildren().addAll(labelPane, tilePane, settlementPane, roadPane);
        tilePane.toBack();
        settlementPane.toFront();
        return gameBoard;

    }
}