package com.example.thesettlers;

import com.example.thesettlers.enums.*;
import java.util.ArrayList;
import java.util.EnumMap;

public class Player {
    private final int MAXSETTLEMENTS = 5;
    private final int MAXCITIES = 4;
    private final int MAXROADS = 15;
    private int playerID;
    private boolean hasLongestRoad;
    private boolean hasLargestArmy;
    private ArrayList<Settlement> settlements;
    private int roadCount;
    EnumMap<ResourceType,Integer> resourceCards;
    private ArrayList<DevelopmentCard> developmentCards;
    private int victoryPoints;
    private int longestRoadLength;
    private int armySize;
    private Game game;
    private EnumMap<DevelopmentCardType,Integer> developmentCardCount;
    public Player(int playerNumber, Game game){
        settlements = new ArrayList<>();
        roadCount = 0;
        resourceCards = new EnumMap<>(ResourceType.class);
        resourceCards.put(ResourceType.WOOL,0);
        resourceCards.put(ResourceType.ORE,0);
        resourceCards.put(ResourceType.BRICK,0);
        resourceCards.put(ResourceType.LUMBER,0);
        resourceCards.put(ResourceType.GRAIN,0);
        developmentCards = new ArrayList<>();
        developmentCardCount = new EnumMap<>(DevelopmentCardType.class);
        developmentCardCount.put(DevelopmentCardType.KNIGHT,0);
        developmentCardCount.put(DevelopmentCardType.VP,0);
        developmentCardCount.put(DevelopmentCardType.ROADBUILDING,0);
        developmentCardCount.put(DevelopmentCardType.YEAROFPLENTY,0);
        developmentCardCount.put(DevelopmentCardType.MONOPOLY,0);
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
        if(game.gameState == GameState.START){
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
        if(game.gameState == GameState.START){
            road.setOwner(this);
            roadCount++;
        }
        else{
            //check if over road limit
            if(roadCount >= MAXROADS) throw new Exception("too many roads");
            //place road if using roadbuilding card
            if(game.roadBuilding > 0){
                road.setOwner(this);
                roadCount++;
            }
            //spend resources on road
            else if(resourceCards.get(ResourceType.BRICK) > 0 && resourceCards.get(ResourceType.LUMBER) > 0){
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
        // check if city
        if(settlement.isCity){
            throw new Exception("settlement is already a city");
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
        if(game.getDevQueueSize() <= 0){
            throw new Exception("dev card queue empty");
        }
        //spend resources on card
        if(resourceCards.get(ResourceType.ORE) > 0 && resourceCards.get(ResourceType.WOOL) > 0 && resourceCards.get(ResourceType.GRAIN) > 0){
            resourceCards.merge(ResourceType.ORE, -1, Integer::sum);
            resourceCards.merge(ResourceType.WOOL, -1, Integer::sum);
            resourceCards.merge(ResourceType.GRAIN, -1, Integer::sum);
            DevelopmentCard devCard = game.getDevCard();  // takes card off stack
            developmentCardCount.put(devCard.getCardType(),developmentCardCount.get(devCard.getCardType())+1);
            if (devCard.getCardType() == DevelopmentCardType.VP){
                addVP();
            }
            developmentCards.add(devCard);
        }
        else{
            throw new Exception("not enough resources");
        }
    }

    public boolean useDevCard(DevelopmentCardType type){
        if (developmentCardCount.get(type) != 0) {
            developmentCardCount.put(type, developmentCardCount.get(type) - 1);
            for(DevelopmentCard card: developmentCards){
                if(card.getCardType() == type && type == DevelopmentCardType.KNIGHT){
                    developmentCards.remove(card);
                    return true;
                } else if(card.getCardType() == type){
                    developmentCards.remove(card);
                    game.returnDevCard(card);
                    return true;
                }
            }
        }
        return false;
    }

    public void giveResource(ResourceType resourceType, int amount) {
        if (resourceType != null) {
            resourceCards.put(resourceType, resourceCards.get(resourceType) + amount);
        }
    }

    public void addVP(){
        victoryPoints++;
        if(victoryPoints >= game.getMaxVPs()){
            game.winGame();
        }
    }

    public void addVP(int quantity){
        victoryPoints += quantity;
        if(victoryPoints >= game.getMaxVPs()){
            game.winGame();
        }
    }

    public int getArmySize() {
        return armySize;
    }

    public void increaseArmySize(){
        armySize++;
    }

    public int getLongestRoadLength() {
        return longestRoadLength;
    }

    public int getPlayerID() {
        return playerID;
    }

    public EnumMap<ResourceType, Integer> getResourceCards() {
        return resourceCards;
    }

    public ArrayList<DevelopmentCard> getDevelopmentCards() {
        return developmentCards;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    // returns true if the player has to many settlements
    public boolean checkTooManySettlements(){
        return (settlements.size() >= MAXSETTLEMENTS);
    }

    // returns true if the player has to many roads
    public boolean checkTooManyRoads(){
        return (roadCount >= MAXROADS);
    }

    // returns true if the player has to many cities
    public boolean checkTooManyCities() {
        return (getNumberOfCities() >= MAXCITIES);
    }

    // returns get dev cards as an int array
    public int[] getDevelopmentCardCount() {
        return new int[]{developmentCardCount.get(DevelopmentCardType.KNIGHT),developmentCardCount.get(DevelopmentCardType.VP),developmentCardCount.get(DevelopmentCardType.ROADBUILDING),developmentCardCount.get(DevelopmentCardType.YEAROFPLENTY),developmentCardCount.get(DevelopmentCardType.MONOPOLY)};
    }

    public ArrayList<Settlement> getSettlements() {
        return settlements;
    }

    public void setLongestRoadLength(int longestRoadLength) {
        this.longestRoadLength = longestRoadLength;
    }


    public void setHasLongestRoad(boolean hasLongestRoad) {
        this.hasLongestRoad = hasLongestRoad;
    }

    public boolean getHasLongestRoad() {
        return hasLongestRoad;
    }

    public void setHasLargestArmy(boolean hasLargestArmy) {
        this.hasLargestArmy = hasLargestArmy;
    }
    public boolean getHasLargestArmy() {
        return hasLargestArmy;
    }
}

