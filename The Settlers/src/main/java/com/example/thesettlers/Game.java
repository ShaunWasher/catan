package com.example.thesettlers;

import com.example.thesettlers.enums.DevelopmentCardType;
import com.example.thesettlers.enums.GameState;
import com.example.thesettlers.enums.ResourceType;
import com.example.thesettlers.enums.TerrainType;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Game {
    public GameState gameState;
    private ArrayList<Player> players;
    private int longestRoad;
    private int largestArmy;
    private LinkedList<DevelopmentCard> developmentCards;
    private GameBoard gameBoard;
    private int turnCount;
    private Player currentPlayer;
    private GUI gui;
    public Game(int numOfPlayers) throws URISyntaxException, IOException {
        gui = null;
        this.gameBoard = new GameBoard(this);
        gameState = GameState.START;
        longestRoad = 0;
        largestArmy = 0;
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
            developmentCards.addFirst(cards.get(rand.nextInt(25-i)));
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

    public Player nextPlayer(){
        turnCount++;
        // first round of settlement placement
        if(turnCount<players.size()){
            gameBoard.setSettlementPane();
            return currentPlayer = players.get(turnCount);
        }
        // second round
        if(turnCount<players.size()*2){
            gameBoard.setSettlementPane();
            currentPlayer = players.get((2*players.size())-turnCount-1);
            gui.refreshUI();
            return currentPlayer;
        }
        //starting phase over
        gameState = GameState.MAIN;
        currentPlayer = players.get(turnCount%players.size());
        gui.refreshUI();
        return currentPlayer;
    }

    public void rollDice(int die1, int die2){
        if(die1+die2 == 7){
            //TODO deal with robber
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

    public boolean buySettlement(Settlement settlement){
        try {// attempts to buy a settlement
            getCurrentPlayer().placeSettlement(settlement);
            gameBoard.setSettlementPane();
            if(gameState == GameState.START){
                // if its the second round of placement you get the resources on the settlement
                if ((turnCount<players.size()*2)&&(turnCount>=players.size())){
                    for(Tile tile: settlement.getTiles()){
                        getCurrentPlayer().giveResource(terrainToResource(tile.getTileType()), 1);
                    }
                }
                // auto shows the road pane as part of the starting phase
                gameBoard.setRoadPane();
            }
            gui.refreshUI();
            return true;
        } catch (Exception exception){
            System.out.println("cant place settlement there"); // problem shows in console for now
            System.out.println(exception);//TODO send to UI so the player can be told whats wrong
        }
        return false;
    }
    public boolean buyRoad(Road road){
        try {
            getCurrentPlayer().placeRoad(road);
            gameBoard.setRoadPane();
            gui.refreshUI();
            // moves to the next player as part of the starting phase of the game
            if(gameState == GameState.START){
                nextPlayer();
            }
            return true;
        } catch (Exception exception){
            System.out.println("cant place road there");
            System.out.println(exception);//TODO send to UI so the player can be told whats wrong
        }
        return false;
    }
}
