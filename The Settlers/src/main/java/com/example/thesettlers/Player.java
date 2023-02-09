package com.example.thesettlers;
import com.example.thesettlers.enums.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;

public class Player {
    int playerID;
    ArrayList<Settlement> settlements;
    Road[] roads;
    Dictionary<ResourceType,Integer> resourceCards;
    DevelopmentCard[] developmentCards;
    int victoryPoints;
    int longestRoadLength;
    int armySize;
    public Player(int playerNumber){
        settlements = new ArrayList<>();
        roads = new Road[15];
        //resourceCards = new Dictionary<ResourceType, Integer>();
        developmentCards = new DevelopmentCard[25];
        victoryPoints = 0;
        longestRoadLength = 0;
        armySize = 0;
        playerID = playerNumber;
    }
    public void placeSettlement(Settlement settlement) throws Exception {
        for (Road road:settlement.getRoads()){
            if(road.getNextSettlement(settlement).getOwner() != null){
                throw new Exception();
            }
        }
        //if(!gamestate! == "start"){
        //    settlement.setOwner(this);
        //    settlements
        //}
    }
    public int getNumberOfCities(){
        int count = 0;
        for (Settlement settlement: settlements){
            if(settlement.isCity()){
                count++;
            }
        }
        return count;
    }

    public int getArmySize() {
        return armySize;
    }

    public int getLongestRoadLength() {
        return longestRoadLength;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
}
