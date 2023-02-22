package com.example.thesettlers;

import com.example.thesettlers.enums.DevelopmentCardType;
import com.example.thesettlers.enums.GameState;
import com.example.thesettlers.enums.ResourceType;
import com.example.thesettlers.enums.TerrainType;
import javafx.util.Pair;

import java.util.*;

public class Game {
    private GameState gameState;
    private ArrayList<Player> players;
    private int longestRoad;
    private int largestArmy;
    private LinkedList<DevelopmentCard> developmentCards;
    private GameBoard gameBoard;
    private int turnCount;
    public Game(GameBoard gameBoard, int numOfPlayers){
        this.gameBoard = gameBoard;
        gameState = GameState.START;
        longestRoad = 0;
        largestArmy = 0;
        players = new ArrayList<>();
        for(int i = 0;i<numOfPlayers;i++) {
            players.add(new Player(i + 1, this));
        }
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
        return players.get(turnCount%players.size());
    }

    public Player nextPlayer(){
        turnCount++;
        // first round of settlement placement
        if(turnCount<players.size()){
            return players.get(turnCount);
        }
        // second round
        if(turnCount<players.size()*2){
            return players.get((2*players.size())-turnCount-1);
        }
        //starting phase over
        gameState = GameState.MAIN;
        return getCurrentPlayer();
    }

    private Pair<Integer,Integer> rollDice(Player player){
        Random rand = new Random();
        Integer die1 = rand.nextInt(5)+1;
        Integer die2 = rand.nextInt(5)+1;
        //dont deal with 7s here
        if(die1+die2 == 7){
            return new Pair<>(die1,die2);
        }
        //for all settlements
        for(Settlement settlement: gameBoard.settlementList){
            if(settlement.getOwner() != null){ // *owned settlements
                // give players resources based on dice roll
                for(Tile tile: settlement.getTiles()){
                    player.giveResource(terrainToResource(tile.getTileType()), 1);
                }
            }
        }
        return new Pair<>(die1,die2);
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
}
