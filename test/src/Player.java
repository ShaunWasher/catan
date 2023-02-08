import java.util.Arraylist;
public class Player {
    int playerID;
    ArrayList settlements;
    Road[] roads;
    ResourceCard[] resourceCards;
    DevelopmentCard[] developmentCards;
    int victoryPoints;
    int longestRoadLength;
    int armySize;
    public Player(int playerNumber){
        settlements = new Settlement[5];
        roads = new Road[15];
        resourceCards = new ResourceCard[95];
        developmentCards = new DevelopmentCard[25];
        victoryPoints = 0;
        longestRoadLength = 0;
        armySize = 0;
        playerID = playerNumber;
    }
    public void placeSettlement(Settlement settlement){
        for (Road road:settlement.getRoads()){
            if(road.getNextSettlement(settlement).getOwner() != null){
                throw new Exception();
            }
        }
        if(!gamestate! == "start"){
            settlement.setOwner(this);
            settlements
        }
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
