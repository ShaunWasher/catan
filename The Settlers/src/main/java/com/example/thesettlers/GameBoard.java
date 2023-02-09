package com.example.thesettlers;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.io. * ;
import static java.lang.Math.sqrt;

public class GameBoard {

    private int rowCount = 6;
    private double yStartOffset = 240;
    private Pane gameBoard;
    private Pane tilePane;
    private Pane labelPane;
    private Pane settlementPane;
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
        labelPane = new Pane();
        settlementPane = new Pane();
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

        indices = new ArrayList<>();
        for (int i = 0; i < terrainList.size(); i++) {
            indices.add(i);
        }
    }

    public Pane getBoard() {
        int[] tilesPerRowValues = {3, 4, 5, 4, 3, 0};
        int[] settlementsPerRowValues = {3,4,5,6,5,4};
        int[] settlementsPerRowValues2 = {4,5,6,5,4,3};
        double[] tilesxStartOffsetValue = {(460 + (2 * n)),460,460,460,(460 + (2 * n)),0};
        double[] settlementxStartOffsetValue = {(460 + (2 * n)),460,460,460-(2 * n),460,460};
        double[] settlementxStartOffsetValue2 = {(460 + n),460-n,(460-n),460-n,460+n,460+n};
        int count = 0;
        for (int y = 0; y < rowCount; y++) {
            int tilesPerRow = tilesPerRowValues[y];
            int settlementsPerRow = settlementsPerRowValues[y];
            int settlementsPerRow2 = settlementsPerRowValues2[y];
            double tilesxStartOffset = tilesxStartOffsetValue[y];
            double settlementxStartOffset = settlementxStartOffsetValue[y];
            double settlementxStartOffset2 = settlementxStartOffsetValue2[y];

            for (int x = 0; x < tilesPerRow; x++) {
                double xCoord = x * (n * 2) + (y % 2) * n + tilesxStartOffset;
                double yCoord = y * (r * 2) * 0.75 + yStartOffset;
                if (Objects.equals(mapType, "Starting Map")) {
                    Tile tile = new Tile(xCoord, yCoord, terrainList.get(count), valueList.get(count));
                    tilePane.getChildren().add(tile.getHexagon());
                    labelPane.getChildren().add(tile.getValueLabel());
                    count++;
                } else if (Objects.equals(mapType, "Random")) {
                    // Does not work yet
                    /*java.util.Collections.shuffle(indices);
                    int randomIndex = indices.remove(indices.size() - 1);
                    String randomTile = terrainList.get(randomIndex);
                    int randomValue = valueList.get(randomIndex);
                    Tile tile = new Tile(xCoord, yCoord,randomTile,randomIndex);
                    tilePane.getChildren().add(tile.getHexagon());
                    circlePane.getChildren().add(tile.getValueLabel());

                    Rectangle rect = new Rectangle(xCoord+ n - 17.5,yCoord- 47.5,35,35);
                    rect.setFill(new ImagePattern(new Image(this.getClass().getResource("placementcircle.png").toExternalForm())));
                    rect.setOnMouseClicked(e -> rect.setFill(new ImagePattern(new Image(this.getClass().getResource("redsettlement.png").toExternalForm()))));
                    circlePane.getChildren().add(rect);
                    */
                }

            }
            for (int x = 0; x < settlementsPerRow; x++) {
                System.out.println(y);
                System.out.println(settlementsPerRow);
                System.out.println(settlementxStartOffset);
                double xCoord = x * (n * 2) + (y % 2) * n + settlementxStartOffset;
                double yCoord = y * (r * 2) * 0.75 + yStartOffset;
                Settlement settlement = new Settlement(xCoord + n - 17.5, yCoord - 47.5);
                settlementPane.getChildren().add(settlement.getIcon());
            }

            for (int x = 0; x < settlementsPerRow2; x++) {
                System.out.println(y);
                System.out.println(settlementsPerRow2);
                System.out.println(settlementxStartOffset2);
                double xCoord = x * (n * 2) + (y % 2) * n + settlementxStartOffset2;
                double yCoord = y * (r * 2) * 0.75 + yStartOffset + r -30;
                Settlement settlement = new Settlement(xCoord + n - 17.5, yCoord - 47.5);
                settlementPane.getChildren().add(settlement.getIcon());
            }




        }
        
        gameBoard.getChildren().addAll(labelPane, tilePane, settlementPane);
        tilePane.toBack();
        settlementPane.toFront();
        return gameBoard;
    }
}
