package com.example.thesettlers;

import com.example.thesettlers.enums.DevelopmentCardType;
import com.example.thesettlers.enums.GameState;
import com.example.thesettlers.enums.ResourceType;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;

import java.util.EnumMap;
import java.util.List;
import java.util.Random;

public class AIPlayer extends Player {

    public AIPlayer(int playerNumber, Game game) {
        super(playerNumber, game);
    }

    public void playTurn(){
        Random random = new Random();
        MouseEvent mouseEvent = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                0, 0, 0, MouseButton.PRIMARY, 1,
                false, false, false, false, true, false, false, false, false, false, null);
        if(game.gameState == GameState.START){
            int randSettlement;
            boolean tooClose;
            do {
                tooClose = false;
                randSettlement = random.nextInt(54);
                for (Road road : game.getGameBoard().settlementList[randSettlement].getRoads()) {
                    if ((road.getNextSettlement(game.getGameBoard().settlementList[randSettlement]).getOwner() != null) || game.getGameBoard().settlementList[randSettlement].getRoads().size() == 2) {
                        tooClose = true;
                    }
                }
            } while (game.getGameBoard().settlementList[randSettlement].getOwner() != null || tooClose);
            game.getGameBoard().getSettlementList()[randSettlement].rectangle.fireEvent(mouseEvent);
            game.getGameBoard().getSettlementList()[randSettlement].getRoads().get(random.nextInt(game.getGameBoard().getSettlementList()[randSettlement].getRoads().size())).rectangle.fireEvent(mouseEvent);

        } else {
            //roll dice
            int value1 = random.nextInt(6) + 1;
            int value2 = random.nextInt(6) + 1;
            game.rollDice(value1, value2);
            game.gui.diceCanBeRolled = false;
            game.gui.dice1.setFill(new ImagePattern(new Image(this.getClass().getResource("d" + value1 + ".png").toExternalForm())));
            game.gui.dice2.setFill(new ImagePattern(new Image(this.getClass().getResource("d" + value2 + ".png").toExternalForm())));
            if (value1 + value2 == 7) {
                useRobber();
                game.throwAwayCards(pickCards((int) Math.floor(getResourceCount() / 2.0)));
            }
            //upgrade to city
            if(resourceCards.get(ResourceType.ORE) >= 3 && resourceCards.get(ResourceType.GRAIN) >= 2){
                for(Settlement settlement:settlements){
                    if(!settlement.isCity){
                        try {
                            game.upgradeToCity(settlement);
                            String[] playerColours =  {"red","blue","gold","white"};
                            settlement.rectangle.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"city.png").toExternalForm())));
                            break;
                        } catch (Exception e){
                            System.out.println("couldn't upgrade");
                        }
                    }
                }
            }
            //buy settlement
            if(resourceCards.get(ResourceType.BRICK) >= 1 && resourceCards.get(ResourceType.GRAIN) >= 1 && resourceCards.get(ResourceType.LUMBER) >= 1 && resourceCards.get(ResourceType.WOOL) >= 1){
                for(Settlement settlement:game.getGameBoard().getSettlementList()){
                    settlement.rectangle.fireEvent(mouseEvent);
                }
            }
            //buy road
            if(resourceCards.get(ResourceType.BRICK) >= 2 && resourceCards.get(ResourceType.LUMBER) >= 2){
                for(Road road:game.getGameBoard().getRoadList()){
                    if(resourceCards.get(ResourceType.BRICK) >= 2 && resourceCards.get(ResourceType.LUMBER) >= 2) {
                        if (random.nextInt(4) == 1) {
                            road.rectangle.fireEvent(mouseEvent);
                        }
                    }
                }
            }
            //use development card
            if(developmentCards.size()>0){
                if(developmentCardCount.get(DevelopmentCardType.KNIGHT)>0){
                    game.useKnightCard();
                    useRobber();
                } else if(developmentCardCount.get(DevelopmentCardType.ROADBUILDING)>0){
                    game.useRoadBuildingCard();
                    for(Road road:game.getGameBoard().getRoadList()){
                        road.rectangle.fireEvent(mouseEvent);
                    }
                } else if(developmentCardCount.get(DevelopmentCardType.YEAROFPLENTY)>0){
                    Integer[] randCards = new Integer[]{0,0,0,0,0};
                    randCards[random.nextInt(5)]++;
                    randCards[random.nextInt(5)]++;
                    game.useYearOfPlentyCard(randCards);
                } else if(developmentCardCount.get(DevelopmentCardType.MONOPOLY)>0){
                    game.useMonopolyCard(ResourceType.getByIndex(random.nextInt(5)));
                }
            }
            //buy development card
            if(resourceCards.get(ResourceType.GRAIN) >= 1 && resourceCards.get(ResourceType.ORE) >= 1 && resourceCards.get(ResourceType.WOOL) >= 1){
                game.gui.buyDevCardButton.fireEvent(mouseEvent);
            }
            //trade with bank
            Integer[] tradeOut = new Integer[]{0,0,0,0,0};
            boolean trade = true;
            if(resourceCards.get(ResourceType.BRICK)>4){
                tradeOut[0] = 4;
            } else if(resourceCards.get(ResourceType.LUMBER)>4){
                tradeOut[1] = 4;
            } else if(resourceCards.get(ResourceType.ORE)>4){
                tradeOut[2] = 4;
            } else if(resourceCards.get(ResourceType.GRAIN)>4){
                tradeOut[3] = 4;
            } else if(resourceCards.get(ResourceType.WOOL)>4){
                tradeOut[4] = 4;
            } else {
                trade = false;
            }
            if(trade){
                Integer[] tradeIn = new Integer[]{0,0,0,0,0};
                int smallest = 0;
                int smallestQuantity = 100;
                for(int i = 0;i<5;i++){
                    if(resourceCards.get(ResourceType.getByIndex(i))<smallestQuantity){
                        smallest = i;
                        smallestQuantity = resourceCards.get(ResourceType.getByIndex(i));
                    }
                }
                tradeIn[smallest] = 1;
                game.bankTrade(tradeOut,tradeIn);


            }
            //end turn
            game.gui.settlementPane.setVisible(false);
            game.gui.roadPane.setVisible(false);
            game.gui.developmentCards.setVisible(false);
            game.nextPlayer();
            game.gui.endTurnMenu();
        }
    }

    private Integer[] pickCards(int number){
        Integer[] pickedCards = new Integer[]{0,0,0,0,0};
        Random random = new Random();
        int randNum = random.nextInt(5);
        int counter = 0;
        while(counter < number){
            if(randNum == 0 && resourceCards.get(ResourceType.BRICK)-pickedCards[0] > 0){
                pickedCards[0]++;
                counter++;
            } else if(randNum == 1 && resourceCards.get(ResourceType.LUMBER)-pickedCards[1] > 0) {
                pickedCards[1]++;
                counter++;
            } else if(randNum == 2 && resourceCards.get(ResourceType.ORE)-pickedCards[2] > 0){
                pickedCards[2]++;
                counter++;
            } else if(randNum == 3 && resourceCards.get(ResourceType.GRAIN)-pickedCards[3] > 0){
                pickedCards[3]++;
                counter++;
            } else if(randNum == 4 && resourceCards.get(ResourceType.WOOL)-pickedCards[4] > 0){
                pickedCards[4]++;
                counter++;
            }
            randNum = random.nextInt(5);
        }
        return pickedCards;
    }
    private void useRobber(){
        Random random = new Random();
        int randTile = random.nextInt(19);
        while (game.getGameBoard().tileList[randTile].isRobber()) {//TODO make more complex
            randTile = random.nextInt(19);
        }
        game.getRobber().setRobber(false);
        game.getGameBoard().tileList[randTile].setRobber(true);
        game.setRobber(game.getGameBoard().tileList[randTile]);
        game.stealCardOptions();
    }

    // Add other AI-specific methods or overrides as needed
}
