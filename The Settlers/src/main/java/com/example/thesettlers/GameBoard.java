package com.example.thesettlers;

import com.example.thesettlers.enums.BoardType;
import com.example.thesettlers.enums.PortType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.io. * ;

import static java.lang.Math.sqrt;

public class GameBoard {
    String[] ports;
    private Pane settlementPermPane = new Pane();
    private Pane roadPermPane = new Pane();
    private Pane gameBoard = new Pane();
    private Pane tilePane = new Pane();;
    private Pane labelPane = new Pane();;
    private Pane settlementPane = new Pane();;
    private Pane roadPane = new Pane();;
    private Pane portsPane = new Pane();
    private Pane robberPane = new Pane();
    private List<String> startingTerrainList;
    private List<String> randomTerrainList;
    private List<String> terrainList;
    private List<Integer> randomValueList;
    private List<Integer> startingValueList;
    private List<Integer> valueList;
    private Integer[][] tileSettlementData;
    private Integer[][] roadSettlementData;
    private Integer[][] portSettlementData;
    Settlement[] settlementList;
    private Road[] roadList;
    private Tile[] tileList;
    private double yStartOffset = 155;
    private double xOff = 145;
    private final static double r = 70; // the inner radius from hexagon center to outer corner
    private final static double n = sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis
    private Game game;

    public GameBoard(Game game) throws URISyntaxException, IOException {
        this.game = game;
        startingTerrainList = new ArrayList<>();
        startingValueList = new ArrayList<>();
        tileSettlementData = new Integer[19][6];
        roadSettlementData = new Integer[72][2];
        portSettlementData = new Integer[9][2];
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
            startingTerrainList.add(data[0]);
            startingValueList.add(Integer.parseInt(data[1]));
        }

        randomTerrainList = new ArrayList<>(startingTerrainList);
        randomValueList = new ArrayList<>(startingValueList);
        int desertIndex = randomTerrainList.indexOf("DESERT");
        randomTerrainList.remove(desertIndex);
        randomValueList.remove(desertIndex);
        Collections.shuffle(randomTerrainList);
        Collections.shuffle(randomValueList);
        int randomIndex = new Random().nextInt(randomTerrainList.size() + 1);
        randomTerrainList.add(randomIndex, "DESERT");
        randomValueList.add(randomIndex, 0);

        if (game.getMapType() == BoardType.STARTING){
            terrainList = startingTerrainList;
            valueList = startingValueList;
        }
        else {
            terrainList = randomTerrainList;
            valueList = randomValueList;
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

        ports = new String[9];
        URL fileUrlPS = getClass().getResource("portsettlementdata.csv");
        File filePS = new File(fileUrlPS.toURI());
        FileReader frPS = new FileReader(filePS);
        BufferedReader brPS = new BufferedReader(frPS);
        i = 0;
        while ((line = brPS.readLine()) != null) {
            String[] data = line.split(splitBy);
            for (int j = 0; j < 2; j++) {
                Integer d = Integer.parseInt(data[j]);
                portSettlementData[i][j]=d;
            }
            ports[i] = data[2];
            i++;
        }

        if (game.getMapType() == BoardType.RANDOM){
            Collections.shuffle(Arrays.asList(ports));
        }

        Rectangle port1 = new Rectangle((2*n)+93,10,74,74);
        port1.setFill(new ImagePattern(new Image(this.getClass().getResource(ports[0]+"port.png").toExternalForm())));

        Rectangle port2 = new Rectangle((6*n)+108,10,74,74);
        port2.setFill(new ImagePattern(new Image(this.getClass().getResource(ports[1]+"port.png").toExternalForm())));

        Rectangle port3 = new Rectangle((9*n)+118,-(0.5*r)+155,74,74);
        port3.setFill(new ImagePattern(new Image(this.getClass().getResource(ports[2]+"port.png").toExternalForm())));

        Rectangle port4 = new Rectangle((11*n)+118,(2.5*r)+165,74,74);
        port4.setFill(new ImagePattern(new Image(this.getClass().getResource(ports[3]+"port.png").toExternalForm())));

        Rectangle port5 = new Rectangle((9*n)+118,(5.5*r)+190,74,74);
        port5.setFill(new ImagePattern(new Image(this.getClass().getResource(ports[4]+"port.png").toExternalForm())));

        Rectangle port6 = new Rectangle((6*n)+108,(5*r)+330,74,74);
        port6.setFill(new ImagePattern(new Image(this.getClass().getResource(ports[5]+"port.png").toExternalForm())));

        Rectangle port7 = new Rectangle((2*n)+93,(5*r)+330,74,74);
        port7.setFill(new ImagePattern(new Image(this.getClass().getResource(ports[6]+"port.png").toExternalForm())));

        Rectangle port8 = new Rectangle(150-r,(2*r)+320,74,74);
        port8.setFill(new ImagePattern(new Image(this.getClass().getResource(ports[7]+"port.png").toExternalForm())));

        Rectangle port9 = new Rectangle(150-r,315-r,74,74);
        port9.setFill(new ImagePattern(new Image(this.getClass().getResource(ports[8]+"port.png").toExternalForm())));

        portsPane.getChildren().addAll(port1,port2,port3,port4,port5,port6,port7,port8,port9);

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
                Road road = new Road(xCoord - 17.5, yCoord - 17.5, version, game);
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
                Settlement settlement = new Settlement(xCoord - 17.5, yCoord - 17.5, game);
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

        for (int q = 0; q < 9; q++){
            for (int s = 0; s < 2; s++) {
                settlementList[portSettlementData[q][s]].setPort(PortType.fromString(ports[q]));
            }
        }

        for (int r = 0; r < 72; r++){
            for (int s = 0; s < 2; s++) {
                settlementList[roadSettlementData[r][s]].addRoad(roadList[r]);
            }
            roadList[r].addSettlements(settlementList[roadSettlementData[r][0]],settlementList[roadSettlementData[r][1]]);
        }
        gameBoard.getChildren().addAll(labelPane, tilePane,portsPane);
        roadPane.setPickOnBounds(false);
        settlementPane.setPickOnBounds(false);
        labelPane.setPickOnBounds(false);
        labelPane.toFront();
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
        settlementPane.setVisible(!settlementPane.isVisible());
        settlementPane.toFront();
    }

    public void setRoadPane() {
        roadPane.setVisible(!roadPane.isVisible());
        roadPane.toFront();
    }

    public Settlement[] getSettlementList() {
        return settlementList;
    }

    public Road[] getRoadList(){return roadList;}

    public Pane getRoadPermPane() {
        return roadPermPane;
    }

    public Pane getSettlementPermPane() {
        return settlementPermPane;
    }

    public String[] getPorts() {
        return ports;
    }

}

