package com.example.thesettlers;
import com.example.thesettlers.enums.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.EnumMap;
import java.util.Enumeration;

public class Player {
    private final int MAXSETTLEMENTS = 5;
    private final int MAXCITIES = 4;
    private final int MAXROADS = 15;
    private int playerID;
    private ArrayList<Settlement> settlements;
    private int roadCount;
    private EnumMap<ResourceType,Integer> resourceCards;
    private ArrayList<DevelopmentCard> developmentCards;
    private int victoryPoints;
    private int longestRoadLength;
    private int armySize;
    private Game game;
    public Player(int playerNumber, Game game){
        settlements = new ArrayList<>();
        roadCount = 0;
        resourceCards = new EnumMap<>(ResourceType.class);
        resourceCards.put(ResourceType.BRICK, 0);
        resourceCards.put(ResourceType.LUMBER, 0);
        resourceCards.put(ResourceType.ORE, 0);
        resourceCards.put(ResourceType.WOOL, 0);
        resourceCards.put(ResourceType.GRAIN, 0);
        developmentCards = new ArrayList<>();
        victoryPoints = 0;
        longestRoadLength = 0;
        armySize = 0;
        playerID = playerNumber;
        this.game = game;
    }

    public int getNumberOfCities() {
        int count = 0;
        for (Settlement settlement : settlements) {
            if (settlement.isCity()) {
                count++;
            }
        }
        return count;
    }

    public void placeSettlement(Settlement settlement) throws Exception{
        //check if settlement is empty
        if(settlement.getOwner() != null){
            throw new Exception("settlement is owned");
        }
        //check that there is no settlements one road away
        for (Road road:settlement.getRoads()){
            if(road.getNextSettlement(settlement).getOwner() != null){
                throw new Exception("settlement to close to others");
            }
        }
        //give player settlement based on game state
        if(/*!gamestate! == "start"'*/true){
            settlement.setOwner(this);
            settlements.add(settlement);
        }
        else{
            //check a road is connected to the settlement
            if(!settlement.checkRoadConnection(this)) throw new Exception("no road connecting settlement");
            //check if over settlement limit
            if(settlements.size()-getNumberOfCities() >= MAXSETTLEMENTS) throw new Exception("too many settlements");
            //spend resource on new settlement
            if(resourceCards.get(ResourceType.BRICK) > 0 && resourceCards.get(ResourceType.GRAIN) > 0 && resourceCards.get(ResourceType.LUMBER) > 0 && resourceCards.get(ResourceType.WOOL) > 0){
                resourceCards.merge(ResourceType.BRICK, -1, Integer::sum);
                resourceCards.merge(ResourceType.GRAIN, -1, Integer::sum);
                resourceCards.merge(ResourceType.LUMBER, -1, Integer::sum);
                resourceCards.merge(ResourceType.WOOL, -1, Integer::sum);
                settlement.setOwner(this);
                settlements.add(settlement);
            }
            else{
                throw new Exception("not enough resources");
            }
        }
    }

    public void placeRoad(Road road) throws Exception {
        //check path has no road
        if(road.getOwner() != null) throw new Exception("road already paved");
        //check path is valid
        if(road.getSettlementA().getOwner() != this && road.getSettlementB().getOwner() != this && !road.getSettlementA().checkRoadConnection(this) && !road.getSettlementB().checkRoadConnection(this)){
            throw new Exception("not connected to player");
        }
        //give player road, based on game state
        if(/*!gamestate! == "start"'*/true){
            road.setOwner(this);
            roadCount++;
        }
        else{
            //check if over road limit
            if(roadCount >= MAXROADS) throw new Exception("too many roads");
            //spend resources on road
            if(resourceCards.get(ResourceType.BRICK) > 0 && resourceCards.get(ResourceType.LUMBER) > 0){
                resourceCards.merge(ResourceType.BRICK, -1, Integer::sum);
                resourceCards.merge(ResourceType.LUMBER, -1, Integer::sum);
                road.setOwner(this);
                roadCount++;
            }
            else{
                throw new Exception("not enough resources");
            }
        }
    }

    public void upgradeToCity(Settlement settlement) throws Exception {
        //check if settlement is owned by player
        if(settlement.getOwner() != this){
            throw new Exception("settlement is not owned by player");
        }
        //check if the max cities have been reached
        if(getNumberOfCities() >= MAXCITIES) throw new Exception("too many cities");
        //buy city
        if(resourceCards.get(ResourceType.ORE) > 2 && resourceCards.get(ResourceType.GRAIN) > 1){
            resourceCards.merge(ResourceType.ORE, -3, Integer::sum);
            resourceCards.merge(ResourceType.GRAIN, -2, Integer::sum);
            settlement.makeCity();
        }
        else{
            throw new Exception("not enough resources");
        }
    }

    public void buyDevCard() throws Exception {
        //TODO check if queue is empty
        //spend resources on card
        if(resourceCards.get(ResourceType.ORE) > 0 && resourceCards.get(ResourceType.WOOL) > 0 && resourceCards.get(ResourceType.GRAIN) > 0){
            resourceCards.merge(ResourceType.ORE, -1, Integer::sum);
            resourceCards.merge(ResourceType.WOOL, -1, Integer::sum);
            resourceCards.merge(ResourceType.GRAIN, -1, Integer::sum);
            // TODO developmentCards.add() [take card off queue and put in player's hand]
        }
        else{
            throw new Exception("not enough resources");
        }
    }

    public void giveResource(ResourceType resourceType, int amount) {
        if (resourceType != null) {
            resourceCards.put(resourceType, resourceCards.get(resourceType) + amount);
        }
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
