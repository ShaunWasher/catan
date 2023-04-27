package com.example.thesettlers;

import com.example.thesettlers.enums.*;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Represents a player in the game of The Settlers.
 */
public class Player {
    public final int MAXSETTLEMENTS = 5;
    public final int MAXCITIES = 4;
    public final int MAXROADS = 15;
    public int playerID;
    public boolean hasLongestRoad;
    public boolean hasLargestArmy;
    public ArrayList<Settlement> settlements;
    public int roadCount;
    public EnumMap<ResourceType,Integer> resourceCards;
    public ArrayList<DevelopmentCard> developmentCards;
    public ArrayList<DevelopmentCard> newDevelopmentCards;
    public boolean devCardUsed;
    public int victoryPoints;
    public int longestRoadLength;
    public int armySize;
    public int resourceCount;
    public Game game;
    public EnumMap<DevelopmentCardType,Integer> developmentCardCount;

    /**
     * Constructs a new Player object with a given player number and associated game.
     *
     * @param playerNumber the unique identifier of the player.
     * @param game the associated game that the player is participating in.
     */
    public Player(int playerNumber, Game game){
        settlements = new ArrayList<>();
        roadCount = 0;
        resourceCards = new EnumMap<>(ResourceType.class);
        resourceCards.put(ResourceType.WOOL,0);
        resourceCards.put(ResourceType.ORE,0);
        resourceCards.put(ResourceType.BRICK,0);
        resourceCards.put(ResourceType.LUMBER,0);
        resourceCards.put(ResourceType.GRAIN,0);
        devCardUsed = false;
        developmentCards = new ArrayList<>();
        newDevelopmentCards = new ArrayList<>();
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

    /**
     * Returns the number of cities owned by the player.
     *
     * @return the number of cities owned by the player.
     */
    public int getNumberOfCities() {
        int count = 0;
        for (Settlement settlement : settlements) {
            if (settlement.isCity()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Attempts to place a settlement for the player at the given location.
     *
     * @param settlement the settlement to be placed.
     * @throws Exception if the settlement cannot be placed due to various conditions.
     */
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

    /**
     * Attempts to place a road for the player at the given location.
     *
     * @param road the road to be placed.
     * @throws Exception if the road cannot be placed due to various conditions.
     */
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

    /**
     * Attempts to upgrade the given settlement to a city for the player.
     *
     * @param settlement the settlement to be upgraded.
     * @throws Exception if the settlement cannot be upgraded due to various conditions.
     */
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

    /**
     * Attempts to buy a development card for the player.
     *
     * @throws Exception if the development card cannot be bought due to various conditions.
     */
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
                developmentCards.add(devCard);
            }
            else{
                newDevelopmentCards.add(devCard);
            }
        }
        else{
            throw new Exception("not enough resources");
        }
    }

    /**
     * Attempts to use a development card of the specified type for the player.
     *
     * @param type the type of the development card to be used.
     * @return true if the development card was used, false otherwise.
     */
    public boolean useDevCard(DevelopmentCardType type){
        if (developmentCardCount.get(type) != 0 && !devCardUsed) {
            for(DevelopmentCard card: developmentCards){
                if(card.getCardType() == type){
                    developmentCards.remove(card);
                    developmentCardCount.put(type, developmentCardCount.get(type) - 1);
                    devCardUsed = true;
                    return true;
                }
            }
            //TODO make the UI say "cant play the card this turn"
            System.out.println("cant play the card this turn");
        }
        return false;
    }

    /**
     * Gives the player a specified amount of a specific resource type.
     *
     * @param resourceType the type of resource to be given.
     * @param amount the amount of the resource to be given.
     */
    public void giveResource(ResourceType resourceType, int amount) {
        if (resourceType != null) {
            resourceCards.put(resourceType, resourceCards.get(resourceType) + amount);
        }
    }

    /**
     * Adds 1 victory point to the player's current total. If the player has
     * reached the maximum victory points needed to win the game, the game
     * is ended.
     */
    public void addVP(){
        victoryPoints++;
        if(victoryPoints >= game.getMaxVPs()){
            game.winGame(this);
        }
    }

    /**
     * Adds the specified quantity of victory points to the player's current
     * total. If the player has reached the maximum victory points needed to
     * win the game, the game is ended.
     *
     * @param quantity The number of victory points to add.
     */
    public void addVP(int quantity){
        victoryPoints += quantity;
        if(victoryPoints >= game.getMaxVPs()){
            game.winGame(this);
        }
    }

    /**
     * Returns the player's current army size.
     *
     * @return The player's army size.
     */
    public int getArmySize() {
        return armySize;
    }

    /**
     * Increases the player's army size by 1.
     */
    public void increaseArmySize(){
        armySize++;
    }

    /**
     * Returns the player's longest road length.
     *
     * @return The player's longest road length.
     */
    public int getLongestRoadLength() {
        return longestRoadLength;
    }

    /**
     * Returns the player's unique ID.
     *
     * @return The player's ID.
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Returns the player's resource cards as an EnumMap.
     *
     * @return The player's resource cards.
     */
    public EnumMap<ResourceType, Integer> getResourceCards() {
        return resourceCards;
    }

    /**
     * Returns the player's development cards as an ArrayList.
     *
     * @return The player's development cards.
     */
    public ArrayList<DevelopmentCard> getDevelopmentCards() {
        return developmentCards;
    }

    /**
     * Returns the player's current victory points.
     *
     * @return The player's victory points.
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * Checks if the player has too many settlements.
     *
     * @return true if the player has reached the maximum number of settlements, false otherwise.
     */
    public boolean checkTooManySettlements(){
        return (settlements.size() >= MAXSETTLEMENTS);
    }

    /**
     * Checks if the player has too many roads.
     *
     * @return true if the player has reached the maximum number of roads, false otherwise.
     */
    public boolean checkTooManyRoads(){
        return (roadCount >= MAXROADS);
    }

    /**
     * Checks if the player has too many cities.
     *
     * @return true if the player has reached the maximum number of cities, false otherwise.
     */
    public boolean checkTooManyCities() {
        return (getNumberOfCities() >= MAXCITIES);
    }

    /**
     * Returns the count of each type of development card the player has as an int array.
     *
     * @return An int array representing the count of each type of development card.
     */
    public int[] getDevelopmentCardCount() {
        return new int[]{developmentCardCount.get(DevelopmentCardType.KNIGHT),developmentCardCount.get(DevelopmentCardType.VP),developmentCardCount.get(DevelopmentCardType.ROADBUILDING),developmentCardCount.get(DevelopmentCardType.YEAROFPLENTY),developmentCardCount.get(DevelopmentCardType.MONOPOLY)};
    }

    /**
     * Makes all new development cards active and resets the flag for whether a development card
     * has been used this turn.
     */
    public void makeNewDevCardsActive(){
        developmentCards.addAll(newDevelopmentCards);
        newDevelopmentCards.clear();
        devCardUsed = false;
    }

    /**
     * Returns the player's settlements as an ArrayList.
     *
     * @return The player's settlements.
     */
    public ArrayList<Settlement> getSettlements() {
        return settlements;
    }

    /**
     * Sets the player's longest road length.
     *
     * @param longestRoadLength The player's new longest road length.
     */
    public void setLongestRoadLength(int longestRoadLength) {
        this.longestRoadLength = longestRoadLength;
    }

    /**
     * Sets whether the player has the longest road.
     *
     * @param hasLongestRoad true if the player has the longest road, false otherwise.
     */
    public void setHasLongestRoad(boolean hasLongestRoad) {
        this.hasLongestRoad = hasLongestRoad;
    }

    /**
     * Returns whether the player has the longest road.
     *
     * @return true if the player has the longest road, false otherwise.
     */
    public boolean getHasLongestRoad() {
        return hasLongestRoad;
    }

    /**
     * Sets whether the player has the largest army.
     *
     * @param hasLargestArmy true if the player has the largest army, false otherwise.
     */
    public void setHasLargestArmy(boolean hasLargestArmy) {
        this.hasLargestArmy = hasLargestArmy;
    }

    /**
     * Returns whether the player has the largest army.
     *
     * @return true if the player has the largest army, false otherwise.
     */
    public boolean getHasLargestArmy() {
        return hasLargestArmy;
    }

    /**
     * Calculates and returns the player's total resource count.
     *
     * @return The player's total resource count.
     */
    public int getResourceCount() {
        resourceCount = 0;
        for (int y = 0; y < 5; y++) {
            resourceCount += resourceCards.get(ResourceType.values()[y]);
        }
        return resourceCount;
    }
}

