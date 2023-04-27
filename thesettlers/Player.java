package com.example.thesettlers;
import com.example.thesettlers.enums.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.EnumMap;
import java.util.Enumeration;

public class Player {
    private final int MAXSETTLEMENTS = 5;//Declare private integer variable for maximum number of settlements which is 5
    private final int MAXCITIES = 4;// Declares private integer variable for maximum number of cities which is 4
    private final int MAXROADS = 15;// Declare private integer variable for maximum number of roads which is 15
    private int playerID;//This line declares a private integer variable called playerID. This variable holds the unique ID of the player.
    private ArrayList<Settlement> settlements;//This line declares a private ArrayList variable called settlements. This variable holds an array of Settlement objects owned by the player.
    private int roadCount;// Declare private integer variable road count, this variable holds the number of roads owned by the player
     EnumMap<ResourceType,Integer> resourceCards;//This line declares a private EnumMap variable called resourceCards. This variable holds a mapping of ResourceType enum values to integer values.
    // This mapping represents the number of resource cards the player has.
    private ArrayList<DevelopmentCard> developmentCards;// This line declares a private ArrayList variable called developmentCards. This variable holds an array of DevelopmentCard objects owned by the player.
    private int victoryPoints;// This line declares a private integer variable "victoryPoints", holds the number of victory points owned by each player.
    private int longestRoadLength;//This line declares a private integer variable called longestRoadLength. This variable holds the length of the longest road owned by the player.
    private int armySize;//This line declares a private integer variable called armySize. This variable holds the size of the player's army.
    private Game game;//This line declares a private Game variable called game. This variable holds the instance of the Game class that the player is playing in.
    public Player(int playerNumber, Game game){
        settlements = new ArrayList<>();//Creates a new ArrayList object to store the settlements of the player.
        roadCount = 0;//initializes the road count of the player to 0.
        resourceCards = new EnumMap<>(ResourceType.class);//Creates a new EnumMap object to store the resource cards of the player.
        resourceCards.put(ResourceType.BRICK, 0);//initializes the resource card BRICK of the player to 0.
        resourceCards.put(ResourceType.LUMBER, 0);//initializes the resource card LUMBER of the player to 0.
        resourceCards.put(ResourceType.ORE, 0);//initializes the resource card ORE of the player to 0.
        resourceCards.put(ResourceType.WOOL, 0);//initializes the resource card WOOL of the player to 0/.
        resourceCards.put(ResourceType.GRAIN, 0);//initializes the resource card GRAIN of the player to 0.
        developmentCards = new ArrayList<>();// Creates a new ArrayList object to store the development cards of the player.
        victoryPoints = 0;// Initializes the victory points of the player to 0.
        longestRoadLength = 0;// Initializes the longest road length of the player to 0.
        armySize = 0;// Initializes the armySize of the player to 0.
        playerID = playerNumber;//Sets the player ID of the player to the specified player number.
        this.game = game;
    }

    public int getNumberOfCities() {
        int count = 0;// Initializes the count variable to 0.
        for (Settlement settlement : settlements) {// This line iterates over each Settlement object in the settlements list.
            if (settlement.isCity()) {//This line checks if the settlement is a city.
                count++;//This line increments the count variable if the settlement is a city.
            }
        }
        return count;// Returns the total count of cities for the player.
    }

    public void placeSettlement(Settlement settlement) throws Exception{//This method is called when a player attempts to place a settlement on the game board.

        if(settlement.getOwner() != null){//This line check if the settlement is already owned by a player. If it is, throw an exception with an error message.
            throw new Exception("settlement is owned");
        }
        //check that there is no settlements one road away
        for (Road road:settlement.getRoads()){//Check if there are any settlements owned by other players that are directly connected to the settlement being placed.
            // If there are, throw an exception with an error message.
            if(road.getNextSettlement(settlement).getOwner() != null){
                throw new Exception("settlement to close to others");
            }
        }
        //give player settlement based on game state
        if(game.gameState == GameState.START){// This line checks if the game is in the starting state.
            // If it is, then assign the settlement to the current player and add it to their list of settlements. If not, move on to the next check.
            settlement.setOwner(this);
            settlements.add(settlement);
        }
        else{
            //This line checks if there is at least one road owned by the current player that is connected to the settlement being placed. If there is not, throw an exception with an error message.
            if(!settlement.checkRoadConnection(this)) throw new Exception("no road connecting settlement");
            //This line checks if the player has already placed the maximum number of settlements allowed. If they have, throw an exception with an error message.
            if(settlements.size()-getNumberOfCities() >= MAXSETTLEMENTS) throw new Exception("too many settlements");
            //Check if the player has the necessary resources (brick, grain, lumber, and wool) to place a settlement.
            // If they do, deduct the required resources from their resource cards, assign the settlement to the current player, and add it to their list of settlements. If they don't, throw an exception with an error message.
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
        //This method is called to place a road for the player.
        //Check if the road is already owned by a player.
        if(road.getOwner() != null) throw new Exception("road already paved");
        //Check if the road is connected to the player's settlements or roads.
        if(road.getSettlementA().getOwner() != this && road.getSettlementB().getOwner() != this && !road.getSettlementA().checkRoadConnection(this) && !road.getSettlementB().checkRoadConnection(this)){
            throw new Exception("not connected to player");
        }
        //If the game is in the start phase, assign the road to the player and increment the player's road count.
        if(game.gameState == GameState.START){
            road.setOwner(this);
            roadCount++;
        }
        else{
            //Check if the player has reached their maximum allowed number of roads.
            if(roadCount >= MAXROADS) throw new Exception("too many roads");

            //Check if the player has enough resources to build the road. If they do, spend the resources and assign the road to the player.
            if(resourceCards.get(ResourceType.BRICK) > 0 && resourceCards.get(ResourceType.LUMBER) > 0){
                resourceCards.merge(ResourceType.BRICK, -1, Integer::sum);
                resourceCards.merge(ResourceType.LUMBER, -1, Integer::sum);
                road.setOwner(this);
                roadCount++;
            }
            else{
                throw new Exception("not enough resources");//If the player does not have enough resources to build the road, throw an exception.
            }
        }
    }

    public void upgradeToCity(Settlement settlement) throws Exception {
        //check if settlement is owned by player
        if(settlement.getOwner() != this){
            throw new Exception("settlement is not owned by player");//Checks if the Settlement object is owned by the player. If not, throws an exception.
        }
        // check if city
        if(settlement.isCity){
            throw new Exception("settlement is already a city");
        }
        //Check if the max cities have been reached
        if(getNumberOfCities() >= MAXCITIES) throw new Exception("too many cities");//checks if the player has reached the maximum number of cities they can own. If yes, throws an exception.
        //Spend resources to buy the city
        if(resourceCards.get(ResourceType.ORE) > 2 && resourceCards.get(ResourceType.GRAIN) > 1){//Checks if the player has enough resources (3 ores and 2 grains) to upgrade the Settlement to a City.
            resourceCards.merge(ResourceType.ORE, -3, Integer::sum);// Subtracts 3 ores from the player's resources using the merge method of the Map interface.
            resourceCards.merge(ResourceType.GRAIN, -2, Integer::sum);//Subtracts 2 grains from the player's resources using the merge method of the Map interface.
            settlement.makeCity();//Upgrades the Settlement object to a City.
        }
        else{
            throw new Exception("not enough resources");//Throws an exception with a message if the player does not have enough resources to upgrade the Settlement object to a City.
        }
    }

    public void buyDevCard() throws Exception {
        //Check if there are any development cards left in the queue
        if(game.getDevQueueSize() <= 0){
            throw new Exception("dev card queue empty");
        }
        //Check if player has enough resources to purchase a card
        if(resourceCards.get(ResourceType.ORE) > 0 && resourceCards.get(ResourceType.WOOL) > 0 && resourceCards.get(ResourceType.GRAIN) > 0){
            resourceCards.merge(ResourceType.ORE, -1, Integer::sum);
            resourceCards.merge(ResourceType.WOOL, -1, Integer::sum);
            resourceCards.merge(ResourceType.GRAIN, -1, Integer::sum);
            developmentCards.add(game.getDevCard());
            //Add the development card to the player's hand
        }
        else{
            throw new Exception("not enough resources");
        }
    }

    //TODO use dev card

    public void giveResource(ResourceType resourceType, int amount) {
        if (resourceType != null) {
            resourceCards.put(resourceType, resourceCards.get(resourceType) + amount);
        }
    }

    public boolean addVP(){
        victoryPoints++;
        if(victoryPoints >= game.getMaxVPs()){
            return true;
        }
        return false;
    }

    public int getArmySize() {
        return armySize;
    }// return Army size

    public int getLongestRoadLength() {
        return longestRoadLength;
    } //return longest Road length

    public int getPlayerID() {
        return playerID;
    }// return player ID
}
