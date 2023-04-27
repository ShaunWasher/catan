package com.example.thesettlers;

import com.example.thesettlers.enums.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Game {
    public GameState gameState;
    private ArrayList<Player> players;
    private int longestRoad;
    private Player largestArmy;
    private GameVersion gameVersion;
    private long endTime;
    private BoardType boardType;
    private LinkedList<DevelopmentCard> developmentCards;
    private GameBoard gameBoard;
    private int turnCount;
    private Player currentPlayer;
    public GUI gui;
    private int maxVPs;
    public int roadBuilding;
    private boolean placeRobber;
    private Tile robber;

    public Game(GameVersion gameVersion,int gameLength, BoardType boardType, String[] order) throws URISyntaxException, IOException {
        placeRobber = false;
        roadBuilding = 0;
        this.gameVersion = gameVersion;
        this.boardType = boardType;
        if(gameVersion == GameVersion.VP) {
            maxVPs = 10;
        } else {
            maxVPs = 100;
            endTime = System.currentTimeMillis() + (gameLength * 60000L);
        }
        gui = null;
        this.gameBoard = new GameBoard(this);
        gameState = GameState.START;
        longestRoad = 0;
        largestArmy = null;
        players = new ArrayList<>();
        for(int i = 0;i< order.length;i++) {
            if (Objects.equals(order[i], "player"))
                players.add(new Player(i + 1, this));
            else if (Objects.equals(order[i], "ai")) {
                players.add(new AIPlayer(i + 1, this));
            }
        }
        currentPlayer = players.get(0);
        turnCount = 0;
        ArrayList<DevelopmentCard> cards = new ArrayList<>();
        for(int i = 0;i<14;i++) {
            cards.add(new DevelopmentCard(DevelopmentCardType.KNIGHT));
        }
        for(int i = 0;i<5;i++) {
            cards.add(new DevelopmentCard(DevelopmentCardType.VP));
        }
        for(int i = 0;i<2;i++) {
            cards.add(new DevelopmentCard(DevelopmentCardType.ROADBUILDING));
        }
        for(int i = 0;i<2;i++) {
            cards.add(new DevelopmentCard(DevelopmentCardType.YEAROFPLENTY));
        }
        for(int i = 0;i<2;i++) {
            cards.add(new DevelopmentCard(DevelopmentCardType.MONOPOLY));
        }
        developmentCards = new LinkedList<>();
        Random rand = new Random();
        for(int i = 0;i<25;i++) {
            developmentCards.addFirst(cards.remove(rand.nextInt(25-i)));
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> gui.updateCountdown()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    public void setGUI(GUI gui){
        this.gui = gui;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
    public DevelopmentCard getDevCard(){
        return developmentCards.removeLast();
    }

    public void returnDevCard(DevelopmentCard card){
        developmentCards.addFirst(card);
    }

    public int getDevQueueSize(){
        return developmentCards.size();
    }

    // don't use in second half of opening turns as the reversed order of the opening turns won't be represented
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public Player nextPlayer(){
        turnCount++;
        // first round of settlement placement
        if(turnCount<players.size()){
            gameBoard.setSettlementPane();
            currentPlayer = players.get(turnCount);
            if(currentPlayer.getClass() == Player.class) {
                gui.endTurnMenu();
            } else {
                ((AIPlayer)currentPlayer).playTurn();
            }
            return currentPlayer;
        }
        // second round
        if(turnCount<players.size()*2){
            gameBoard.setSettlementPane();
            currentPlayer = players.get((2*players.size())-turnCount-1);
            gui.refreshUI();
            if(currentPlayer.getClass() == Player.class) {
                gui.endTurnMenu();
            } else {
                ((AIPlayer)currentPlayer).playTurn();
            }
            return currentPlayer;
        }
        //starting phase over
        gameState = GameState.MAIN;
        currentPlayer.makeNewDevCardsActive();
        currentPlayer = players.get(turnCount%players.size());
        gui.refreshUI();
        gui.setDiceCanBeRolledTrue();
        if(gameVersion == GameVersion.TIMED){
            if(System.currentTimeMillis() > endTime && currentPlayer.getPlayerID() == 1){
                Player winner = null;
                int maxVictoryPoints = Integer.MIN_VALUE;
                for (Player player : players) {
                    if (player.getVictoryPoints() > maxVictoryPoints) {
                        maxVictoryPoints = player.getVictoryPoints();
                        winner = player;
                    }
                }
                winGame(winner);
            }
        }
        if(currentPlayer.getClass() == AIPlayer.class) {
            ((AIPlayer)currentPlayer).playTurn();
        }
        return currentPlayer;
    }

    public void rollDice(int die1, int die2){
        if(die1+die2 == 7 && getCurrentPlayer().getClass() == Player.class){
            gameBoard.transparency(true);
            placeRobber = true;
            Platform.runLater(() -> {
                if (getCurrentPlayer().getResourceCount() > 7){
                    gui.throwAway();
                }
                // Update the GUI here
            });
        }
        //for all settlements
        for(Settlement settlement: gameBoard.settlementList){
            if((settlement.getOwner() != null)){ // *owned settlements
                // give players resources based on dice roll
                for(Tile tile: settlement.getTiles()){
                    if(tile.getValue() == die1+die2 && !tile.isRobber()) {
                        settlement.getOwner().giveResource(terrainToResource(tile.getTileType()), 1);
                    }
                }
            }
        }
        gui.refreshUI();
    }

    // converts terrain to resource enums returns null if dessert
    public static ResourceType terrainToResource(TerrainType terrainType){
        if(terrainType == TerrainType.HILLS){
            return ResourceType.BRICK;
        }
        if(terrainType == TerrainType.FIELDS){
            return ResourceType.GRAIN;
        }
        if(terrainType == TerrainType.FOREST){
            return ResourceType.LUMBER;
        }
        if(terrainType == TerrainType.MOUNTAINS){
            return ResourceType.ORE;
        }
        if(terrainType == TerrainType.PASTURE){
            return ResourceType.WOOL;
        }
        return null;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getMaxVPs() {
        return maxVPs;
    }

    public void useKnightCard(){
        if(getCurrentPlayer().useDevCard(DevelopmentCardType.KNIGHT)){ //checks if player has dev card and if so puts it back on the stack
            if(currentPlayer.getClass() == Player.class) {
                gameBoard.transparency(true);
                placeRobber = true;
            }
            getCurrentPlayer().increaseArmySize();
            if(largestArmy == null && getCurrentPlayer().getArmySize() >= 3){
                largestArmy = getCurrentPlayer();
                largestArmy.addVP(2);
                largestArmy.setHasLargestArmy(true);
            }
            else if(largestArmy != null && getCurrentPlayer().getArmySize()>largestArmy.getArmySize()){
                largestArmy.addVP(-2);
                largestArmy.setHasLargestArmy(false);
                largestArmy = getCurrentPlayer();
                largestArmy.addVP(2);
                largestArmy.setHasLargestArmy(true);
            }
            gui.refreshUI();
        }
    }

    public void useRoadBuildingCard(){
        if(roadBuilding == 0 && getCurrentPlayer().useDevCard(DevelopmentCardType.ROADBUILDING)){
            roadBuilding = 1;
            gui.showRoads();
        }
        else if(roadBuilding == 1){
            gui.showRoads();
            roadBuilding++;
        } else {
            roadBuilding = 0;
        }
    }

    public void useYearOfPlentyCard(Integer[] yearOfPlentyValues){
        for (int y = 0; y < 5; y++) {
            getCurrentPlayer().getResourceCards().merge(ResourceType.getByIndex(y),yearOfPlentyValues[y],Integer::sum);
        }
        gui.refreshUI();
    }

    public void useMonopolyCard(ResourceType monopolyResType){
        for (Player nonActivePlayer : players) {
            int value = nonActivePlayer.resourceCards.get(monopolyResType);
            currentPlayer.getResourceCards().merge(monopolyResType, value, Integer::sum);
            nonActivePlayer.getResourceCards().merge(monopolyResType, -value, Integer::sum);
        }
        gui.refreshUI();
    }

    public boolean buySettlement(Settlement settlement){
        try {// attempts to buy a settlement
            getCurrentPlayer().placeSettlement(settlement);
            getCurrentPlayer().addVP();
            gameBoard.setSettlementPane();
            if(gameState == GameState.START){
                // if its the second round of placement you get the resources on the settlement
                if ((turnCount<players.size()*2)&&(turnCount>=players.size())){
                    for(Tile tile: settlement.getTiles()){
                        getCurrentPlayer().giveResource(terrainToResource(tile.getTileType()), 1);
                    }
                }
                // auto shows the road pane as part of the starting phase
                for (Road road : gameBoard.getRoadList()) {
                    if (road.getSettlementA() == settlement || road.getSettlementB() == settlement) {
                        road.getIcon().setVisible(true);
                    } else {
                        road.getIcon().setVisible(false);
                    }
                    if (road.getOwner() != null) {
                        road.getIcon().setVisible(true);
                    }
                }
                gameBoard.setRoadPane();
            }
            gui.refreshUI();
            return true;
        } catch (Exception exception){
            gui.cantPlaceSettlementError();
            System.out.println("cant place settlement there");
            System.out.println(exception);
        }
        return false;
    }
    public boolean buyRoad(Road road){
        try {
            getCurrentPlayer().placeRoad(road);
            gameBoard.setRoadPane();
            getCurrentPlayer().setLongestRoadLength(findLongestRoad(getCurrentPlayer()));
            updateLongestRoad();
            gui.refreshUI();
            return true;
        } catch (Exception exception){
            gui.cantPlaceRoadError();
            System.out.println("cant place road there");
            System.out.println(exception);
        }
        return false;
    }
    public boolean upgradeToCity(Settlement settlement){
        try {
            getCurrentPlayer().upgradeToCity(settlement);
            getCurrentPlayer().addVP();
            gui.refreshUI();
            return true;
        } catch (Exception exception){
            System.out.println("cant place city there");
            System.out.println(exception);
        }
        return false;
    }

    public boolean bankTrade(Integer[] playerCards, Integer[] bankCards){
        if(bankCards[0]+bankCards[1]+bankCards[2]+bankCards[3]+bankCards[4] != 1){
            return false;
            //TODO tell the player that only one card can be bought
        }
        int[] exchangeRates = new int[]{4,4,4,4,4};
        boolean[] hasPorts = new boolean[]{false,false,false,false,false,false};
        for(Settlement settlement: currentPlayer.getSettlements()) {
            if (settlement.getPort() == PortType.BRICK) {
                hasPorts[0] = true;
            } else if (settlement.getPort() == PortType.LUMBER) {
                hasPorts[1] = true;
            } else if (settlement.getPort() == PortType.ORE) {
                hasPorts[2] = true;
            } else if (settlement.getPort() == PortType.GRAIN) {
                hasPorts[3] = true;
            } else if (settlement.getPort() == PortType.WOOL) {
                hasPorts[4] = true;
            } else if (settlement.getPort() == PortType.ANY) {
                hasPorts[5] = true;
            }
        }
        if(hasPorts[5]){
            exchangeRates = new int[]{3,3,3,3,3};
        }
        if(hasPorts[0]){
            exchangeRates[0] = 2;
        }
        if(hasPorts[1]){
            exchangeRates[1] = 2;
        }
        if(hasPorts[2]){
            exchangeRates[2] = 2;
        }
        if(hasPorts[3]){
            exchangeRates[3] = 2;
        }
        if(hasPorts[4]){
            exchangeRates[4] = 2;
        }
        if(playerCards[0]>=exchangeRates[0]){
            currentPlayer.resourceCards.merge(ResourceType.BRICK, -exchangeRates[0], Integer::sum);
        } else if(playerCards[1]>=exchangeRates[1]){
            currentPlayer.resourceCards.merge(ResourceType.LUMBER, -exchangeRates[1], Integer::sum);
        } else if(playerCards[2]>=exchangeRates[2]){
            currentPlayer.resourceCards.merge(ResourceType.ORE, -exchangeRates[2], Integer::sum);
        } else if(playerCards[3]>=exchangeRates[3]){
            currentPlayer.resourceCards.merge(ResourceType.GRAIN, -exchangeRates[3], Integer::sum);
        } else if(playerCards[4]>=exchangeRates[4]){
            currentPlayer.resourceCards.merge(ResourceType.WOOL, -exchangeRates[4], Integer::sum);
        } else {
            return false;
        }
        if (bankCards[0] == 1){
            currentPlayer.resourceCards.merge(ResourceType.BRICK, 1, Integer::sum);
        } else if (bankCards[1] == 1){
            currentPlayer.resourceCards.merge(ResourceType.LUMBER, 1, Integer::sum);
        } else if (bankCards[2] == 1){
            currentPlayer.resourceCards.merge(ResourceType.ORE, 1, Integer::sum);
        } else if (bankCards[3] == 1){
            currentPlayer.resourceCards.merge(ResourceType.GRAIN, 1, Integer::sum);
        } else if (bankCards[4] == 1){
            currentPlayer.resourceCards.merge(ResourceType.WOOL, 1, Integer::sum);
        }
        return true;
    }

    public void winGame(Player player){
        gui.winMessage(player);
    }

    public BoardType getMapType() {
        return boardType;
    }

    public void setRobber(Tile robber) {
        this.robber = robber;
    }

    public Tile getRobber() {
        return robber;
    }

    public int findLongestRoad(Player player) {
        int longestRoad = 0;
        for (Road road : gameBoard.getRoadList()) {
            if (road.getOwner() == player) {
                for (Settlement settlement : Arrays.asList(road.getSettlementA(), road.getSettlementB())) {
                    Set<Road> visitedRoads = new HashSet<>();
                    int roadLength = findLongestRoadRecursively(player, settlement, visitedRoads);
                    longestRoad = Math.max(longestRoad, roadLength);
                }
            }
        }
        return longestRoad;
    }

    private int findLongestRoadRecursively(Player player, Settlement currentSettlement, Set<Road> visitedRoads) {
        int maxLength = 0;
        for (Road connectedRoad : getConnectedRoads(currentSettlement, player)) {
            if (!visitedRoads.contains(connectedRoad)) {
                visitedRoads.add(connectedRoad);
                Settlement nextSettlement = connectedRoad.getOtherSettlement(currentSettlement);
                int roadLength = 1 + findLongestRoadRecursively(player, nextSettlement, visitedRoads);
                maxLength = Math.max(maxLength, roadLength);
                visitedRoads.remove(connectedRoad);
            }
        }
        return maxLength;
    }


    private List<Road> getConnectedRoads(Settlement settlement, Player player) {
        List<Road> connectedRoads = new ArrayList<>();
        for (Road road : gameBoard.getRoadList()) {
            if (road.getOwner() == player && (road.getSettlementA() == settlement || road.getSettlementB() == settlement)) {
                connectedRoads.add(road);
            }
        }
        return connectedRoads;
    }

    public void updateLongestRoad() {
        int minLongestRoadLength = 5;
        Player currentLongestRoadHolder = null;
        int currentLongestRoadLength = 0;

        // Find the current longest road holder and length
        for (Player player : players) {
            if (player.getHasLongestRoad()) {
                currentLongestRoadHolder = player;
                currentLongestRoadLength = findLongestRoad(player);
                break;
            }
        }

        // Check if any other player has a longer road
        for (Player player : players) {
            if (player != currentLongestRoadHolder) {
                int roadLength = findLongestRoad(player);
                if (roadLength >= minLongestRoadLength && roadLength > currentLongestRoadLength) {
                    if (currentLongestRoadHolder != null) {
                        currentLongestRoadHolder.setHasLongestRoad(false);
                        currentLongestRoadHolder.addVP(-2); // Remove 2 victory points from the previous holder
                    }
                    currentLongestRoadHolder = player;
                    currentLongestRoadLength = roadLength;
                    currentLongestRoadHolder.setHasLongestRoad(true);
                    currentLongestRoadHolder.addVP(2); // Add 2 victory points to the new holder
                }
            }
        }
    }

    public boolean throwAwayCards(Integer[] throwAwayValues){
        int throwCount = 0;
        for (int y = 0; y < 5; y++) {
           throwCount += throwAwayValues[y];
        }
        if (throwCount == (int) Math.floor(getCurrentPlayer().getResourceCount() / 2.0)){
            for (int y = 0; y < 5; y++) {
                getCurrentPlayer().getResourceCards().merge(ResourceType.getByIndex(y),-throwAwayValues[y],Integer::sum);
            }
            gui.refreshUI();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean getPlaceRobber() {
        return placeRobber;
    }

    public void setPlaceRobber(boolean placeRobber) {
        this.placeRobber = placeRobber;
    }

    public long getEndTime() {
        return endTime;
    }

    public void stealCardOptions(){
        ArrayList<Player> stealOptions = new ArrayList<>();
        for (Player player : players){
            if (player != currentPlayer){
                for (Settlement settlement : player.getSettlements()){
                    for (Tile tile : settlement.getTiles()){
                        if (tile.isRobber()){
                            if (!stealOptions.contains(player)) {
                                stealOptions.add(player);
                            }
                        }
                    }
                }
            }
        }
        if (stealOptions.size() > 1 && getCurrentPlayer().getClass() == Player.class) {
            gui.stealCardSelect(stealOptions);
        } else if (stealOptions.size() == 1 || (getCurrentPlayer().getClass() == AIPlayer.class && stealOptions.size()>0)) {
            stealCard(stealOptions.get(0));
        }
    }

    public void stealCard(Player player){
        ArrayList<Integer> indexOptions = new ArrayList<>();
        for (int y = 0; y < 5; y++) {
            if (player.resourceCards.get(ResourceType.values()[y]) > 0){
                indexOptions.add(y);
            }
        }
        if (indexOptions.size() != 0){
            Random random = new Random();
            int index = random.nextInt(indexOptions.size());
            int randomNumber = indexOptions.get(index);
            currentPlayer.getResourceCards().merge(ResourceType.getByIndex(randomNumber),1,Integer::sum);
            player.getResourceCards().merge(ResourceType.getByIndex(randomNumber),-1,Integer::sum);
            gui.refreshUI();
        }
    }


}
