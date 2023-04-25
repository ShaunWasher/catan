package com.example.thesettlers;

import com.example.thesettlers.enums.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Game {
    public GameState gameState;
    private ArrayList<Player> players;
    private int longestRoad;
    private Player largestArmy;
    private GameVersion gameVersion;
    private BoardType boardType;
    private LinkedList<DevelopmentCard> developmentCards;
    private GameBoard gameBoard;
    private int turnCount;
    private Player currentPlayer;
    public GUI gui;
    private int maxVPs;
    public int roadBuilding;
    private boolean placeRobber;

    //TODO PASS THROUGH LENGTH OF GAME FOR TIMED
    public Game(GameVersion gameVersion, BoardType boardType, int numOfPlayers, int numOfAgents) throws URISyntaxException, IOException {
        placeRobber = false;
        roadBuilding = 0;
        this.gameVersion = gameVersion;
        this.boardType = boardType;
        maxVPs = 10; //I SUPPOSE
        gui = null;
        this.gameBoard = new GameBoard(this);
        gameState = GameState.START;
        longestRoad = 0;
        largestArmy = null;
        players = new ArrayList<>();
        for(int i = 0;i<numOfPlayers;i++) {
            players.add(new Player(i + 1, this));
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

    public void bankTrade(){

    }


    public Player nextPlayer(){
        turnCount++;
        // first round of settlement placement
        if(turnCount<players.size()){
            gameBoard.setSettlementPane();
            currentPlayer = players.get(turnCount);
            gui.endTurnMenu();
            return currentPlayer;
        }
        // second round
        if(turnCount<players.size()*2){
            gameBoard.setSettlementPane();
            currentPlayer = players.get((2*players.size())-turnCount-1);
            gui.refreshUI();
            gui.endTurnMenu();
            return currentPlayer;
        }
        //starting phase over
        gameState = GameState.MAIN;
        currentPlayer = players.get(turnCount%players.size());
        gui.refreshUI();
        gui.setDiceCanBeRolledTrue();
        return currentPlayer;
    }

    public void rollDice(int die1, int die2){
        if(die1+die2 == 7){
            gameBoard.getRobberPane().setVisible(true);
            //TODO take card from player
        }
        //for all settlements
        for(Settlement settlement: gameBoard.settlementList){
            if((settlement.getOwner() != null)){ // *owned settlements
                // give players resources based on dice roll
                for(Tile tile: settlement.getTiles()){
                    if(tile.getValue() == die1+die2) {
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
            gameBoard.getRobberPane().setVisible(true);
            //TODO take card from player
            getCurrentPlayer().increaseArmySize();
            if(largestArmy == null){
                largestArmy = getCurrentPlayer();
                largestArmy.addVP(2);
            }
            else if(getCurrentPlayer().getArmySize()>largestArmy.getArmySize()){
                largestArmy.addVP(-2);
                largestArmy = getCurrentPlayer();
                largestArmy.addVP(2);
            }
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

    public boolean useYearOfPlentyCard(){
        if(getCurrentPlayer().useDevCard(DevelopmentCardType.YEAROFPLENTY)){
            //TODO add card functionality
        }
        return false;
    }

    public boolean useMonopolyCard(){
        if(getCurrentPlayer().useDevCard(DevelopmentCardType.MONOPOLY)){
            //TODO add card functionality
        }
        return false;
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
            System.out.println("cant place settlement there"); // problem shows in console for now
            System.out.println(exception);//TODO send to UI so the player can be told whats wrong
        }
        return false;
    }
    public boolean buyRoad(Road road){
        try {
            getCurrentPlayer().placeRoad(road);
            gameBoard.setRoadPane();

            //TODO find if longest road has changed

            gui.refreshUI();
            return true;
        } catch (Exception exception){
            gui.cantPlaceRoadError();
            System.out.println("cant place road there");
            System.out.println(exception);//TODO send to UI so the player can be told whats wrong
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
            System.out.println(exception);//TODO send to UI so the player can be told whats wrong
        }
        return false;
    }

    public void winGame(){
        gui.winMessage();
        //TODO send data to UI to show scores
    }

    public BoardType getMapType() {
        return boardType;
    }

    public boolean getPlaceRobber() {
        return placeRobber;
    }

    public void setAllRobbersInactive() {
        for (Robber robber : gameBoard.getRobbers()) {
            robber.setActive(false);
        }
    }
}
