package com.example.thesettlers;

import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io. * ;
import static java.lang.Math.sqrt;

public class GameBoard {
    private Pane gameBoard = new Pane();
    private Pane tilePane = new Pane();;
    private Pane labelPane = new Pane();;
    private Pane settlementPane = new Pane();;
    private Pane roadPane = new Pane();;
    private String mapType;
    private List<String> terrainList;
    private List<Integer> valueList;
    private Integer[][] tileSettlementData;
    private Integer[][] roadSettlementData;
    Settlement[] settlementList;
    private Road[] roadList;
    private Tile[] tileList;
    private double yStartOffset = 155;
    private double xOff = 410;
    private final static double r = 70; // the inner radius from hexagon center to outer corner
    private final static double n = sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis

    public GameBoard() throws URISyntaxException, IOException {
        this.mapType = mapType;
        terrainList = new ArrayList<>();
        valueList = new ArrayList<>();
        tileSettlementData = new Integer[19][6];
        roadSettlementData = new Integer[72][2];
        settlementList = new Settlement[54];
        tileList = new Tile[19];
        roadList = new Road[72];

        String line = "";
        String splitBy = ",";
        URL fileUrlSM = getClass().getResource("startingmap.csv");
        File fileSM = new File(fileUrlSM.toURI());
        FileReader frSM = new FileReader(fileSM);
        BufferedReader brSM = new BufferedReader(frSM);
        while ((line = brSM.readLine()) != null) {
            String[] data = line.split(splitBy);
            terrainList.add(data[0]);
            valueList.add(Integer.parseInt(data[1]));
        }

        URL fileUrlTS = getClass().getResource("tilesettlementdata.csv");
        File fileTS = new File(fileUrlTS.toURI());
        FileReader frTS = new FileReader(fileTS);
        BufferedReader brTS = new BufferedReader(frTS);
        int i = 0;
        while ((line = brTS.readLine()) != null) {
            String[] data = line.split(splitBy);
            for (int j = 0; j < 6; j++) {
                Integer d = Integer.parseInt(data[j]);
                tileSettlementData[i][j]=d;
            }
            i++;
        }

        URL fileUrlRS = getClass().getResource("roadsettlementdata.csv");
        File fileRS = new File(fileUrlRS.toURI());
        FileReader frRS = new FileReader(fileRS);
        BufferedReader brRS = new BufferedReader(frRS);
        i = 0;
        while ((line = brRS.readLine()) != null) {
            String[] data = line.split(splitBy);
            for (int j = 0; j < 2; j++) {
                Integer d = Integer.parseInt(data[j]);
                roadSettlementData[i][j]=d;
            }
            i++;
        }

        int[] tilesPerRowValues = {3, 4, 5, 4, 3};
        double[] tilesxStartOffsetValue = {(xOff + (2 * n)), xOff, xOff, xOff, (xOff + (2 * n))};

        int count = 0;
        for (int y = 0; y < 5; y++) {
            int tilesPerRow = tilesPerRowValues[y];
            double tilesxStartOffset = tilesxStartOffsetValue[y];
            for (int x = 0; x < tilesPerRow; x++) {
                double xCoord = (x * (n * 2) + (y % 2) * n) + tilesxStartOffset;
                double yCoord = (y * (r * 2) * 0.75) + yStartOffset;
                Tile tile = new Tile(xCoord, yCoord, terrainList.get(count), valueList.get(count));
                tileList[count] = tile;
                tilePane.getChildren().add(tile);
                labelPane.getChildren().add(tile.getValueLabel());
                count++;
            }
        }

        int[] roadsPerRowValues = {6, 4, 8, 5, 10, 6, 10, 5, 8, 4, 6};
        double[] roadxStartOffsetValue = {xOff + (2.5 * n), xOff + (2 * n), xOff + (1.5 * n), xOff + n, xOff + (0.5 * n), xOff, xOff + (0.5 * n), xOff + n, xOff + (1.5 * n), xOff + (2 * n), xOff + (2.5 * n)};

        count = 0;
        for (int y = 0; y < 11; y++) {
            int version;
            double xCoord;
            double yCoord;
            int roadsPerRow = roadsPerRowValues[y];
            double roadxStartOffset = roadxStartOffsetValue[y];
            for (int x = 0; x < roadsPerRow; x++) {
                yCoord = (y * (r*3/4)) + yStartOffset - r/4;
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
                Road road = new Road(xCoord - 17.5, yCoord - 17.5, version);
                roadList[count] = road;
                roadPane.getChildren().add(road.getIcon());
                count++;
            }
        }

        int[] settlementsPerRowValues = {3,4,4,5,5,6,6,5,5,4,4,3};
        double[] settlementxStartOffsetValue = {xOff + (2 * n),xOff+n,xOff+n,xOff,xOff,xOff-n,xOff-n,xOff,xOff,xOff+n,xOff+n,xOff+(2*n)};
        count = 0;
        for (int y = 0; y < 12; y++) {
            int settlementsPerRow = settlementsPerRowValues[y];
            double settlementxStartOffset = settlementxStartOffsetValue[y];
            for (int x = 0; x < settlementsPerRow; x++) {
                double xCoord = (x * (2 * n)) + settlementxStartOffset + n;
                double yCoord;
                if (y % 2 == 0) {
                    yCoord = ((y / 2) * 1.5 * r) + yStartOffset - r/2;
                } else {
                    yCoord = (((y - 1) / 2) * 1.5 * r) + yStartOffset;
                }
                Settlement settlement = new Settlement(xCoord - 17.5, yCoord - 17.5);
                settlementList[count] = settlement;
                settlementPane.getChildren().add(settlement.getIcon());
                count++;
            }
        }

        for (int t = 0; t < 19; t++){
            for (int s = 0; s < 6; s++) {
                settlementList[tileSettlementData[t][s]].addTile(tileList[t]);
            }
        }

        for (int r = 0; r < 72; r++){
            for (int s = 0; s < 2; s++) {
                settlementList[roadSettlementData[r][s]].addRoad(roadList[r]);
            }
            roadList[r].addSettlements(settlementList[roadSettlementData[r][0]],settlementList[roadSettlementData[r][1]]);
        }
        gameBoard.getChildren().addAll(labelPane, tilePane);

    }

    public Pane getGameBoard() {
        return gameBoard;
    }

    public Pane getSettlementPane() {
        return settlementPane;
    }

    public Pane getRoadPane() {
        return roadPane;
    }
    public void setSettlementPane() {
        settlementPane.setVisible(false);
    }

    public void setRoadPane() {
        roadPane.setVisible(true);
        roadPane.toFront();
    }


}
