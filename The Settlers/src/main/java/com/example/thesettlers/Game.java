package com.example.thesettlers;

import com.example.thesettlers.enums.DevelopmentCardType;
import com.example.thesettlers.enums.GameState;
import com.example.thesettlers.enums.ResourceType;
import com.example.thesettlers.enums.TerrainType;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

public class Game {
    private GameState gameState;
    private ArrayList<Player> players;
    private int longestRoad;
    private int largestArmy;
    private Queue<DevelopmentCard> developmentCards;
    private GameBoard gameBoard;
    public Game(GameBoard gameBoard, int numOfPlayers){
        this.gameBoard = gameBoard;
        gameState = GameState.START;
        longestRoad = 0;
        largestArmy = 0;
        players = new ArrayList<>();
        for(int i = 0;i<numOfPlayers;i++) {
            players.add(new Player(i + 1));
        }
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
        Random rand = new Random();
        for(int i = 0;i<25;i++) {
            developmentCards.add(cards.get(rand.nextInt(25-i)));
        }
    }

    public void play(){
        // first round of settlement placement
        for(int i = 0; i<players.size(); i++){
            //TODO show player's UI, show settlement placement options then road placement
        }
        for(int i = players.size(); i>0; i--){
            //TODO show player's UI, show settlement placement options then road placement, give resources based on tiles around selected settlement
        }
        //starting phase over
        gameState = GameState.MAIN;
        //int turn = 1;
        //begin taking turns loop ends when someone gets 10 VPs
        int victorID = 0;
        while(victorID == 0){
            int player = 0; // player ID is player+1
            // each player takes a turn, loop breaks if someone hits 10VPs
            while(player < players.size()){
                //TODO show player UI
                //TODO roll dice
                //TODO allow trading and purchasing
                //TODO wait for end turn button press
            }
        }
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
    private static ResourceType terrainToResource(TerrainType terrainType){
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
