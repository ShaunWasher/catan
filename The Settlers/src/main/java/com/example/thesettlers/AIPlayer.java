package com.example.thesettlers;

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

    // Implement AI-specific methods and override existing methods as needed
    public void makeMove() {
        // Implement the logic for the AI to make a move in the game
    }

    @Override
    public void placeSettlement(Settlement settlement) throws Exception {
        // Implement AI-specific logic for placing settlements
        super.placeSettlement(settlement);
    }

    @Override
    public void placeRoad(Road road) throws Exception {
        // Implement AI-specific logic for placing roads
        super.placeRoad(road);
    }

    @Override
    public void upgradeToCity(Settlement settlement) throws Exception {
        // Implement AI-specific logic for upgrading to cities
        super.upgradeToCity(settlement);
    }

    @Override
    public void buyDevCard() throws Exception {
        // Implement AI-specific logic for buying development cards
        super.buyDevCard();
    }

    public void playTurn(){
        Random random = new Random();
        if(game.gameState == GameState.START){
            int randSettlement = random.nextInt(54);
            boolean tooClose = false;
            for (Road road:game.getGameBoard().settlementList[randSettlement].getRoads()){
                if(road.getNextSettlement(game.getGameBoard().settlementList[randSettlement]).getOwner() != null){
                    tooClose = true;
                }
            }
            while (game.getGameBoard().settlementList[randSettlement].getOwner() != null || tooClose){
                tooClose = false;
                randSettlement = random.nextInt(54);
                for (Road road:game.getGameBoard().settlementList[randSettlement].getRoads()){
                    if(road.getNextSettlement(game.getGameBoard().settlementList[randSettlement]).getOwner() != null){
                        tooClose = true;
                    }
                }
            }
            MouseEvent mouseEvent = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                    0, 0, 0, MouseButton.PRIMARY, 1,
                    false, false, false, false, true, false, false, false, false, false, null);
            game.getGameBoard().getSettlementList()[randSettlement].rectangle.fireEvent(mouseEvent);
            game.getGameBoard().getSettlementList()[randSettlement].getRoads().get(random.nextInt(game.getGameBoard().getSettlementList()[randSettlement].getRoads().size())).rectangle.fireEvent(mouseEvent);

        } else {
            int value1 = random.nextInt(6) + 1;
            int value2 = random.nextInt(6) + 1;
            game.rollDice(value1, value2);
            game.gui.dice1.setFill(new ImagePattern(new Image(this.getClass().getResource("d" + value1 + ".png").toExternalForm())));
            game.gui.dice2.setFill(new ImagePattern(new Image(this.getClass().getResource("d" + value2 + ".png").toExternalForm())));
            if (value1 + value2 == 7) {
                int randTile = random.nextInt(19);
                while (game.getGameBoard().tileList[randTile].isRobber()) {//TODO make more complex
                    randTile = random.nextInt(19);
                }
                game.getRobber().setRobber(false);
                game.getGameBoard().tileList[randTile].setRobber(true);
                game.setRobber(game.getGameBoard().tileList[randTile]);
                game.stealCardOptions();
                game.throwAwayCards(pickCards((int) Math.floor(getResourceCount() / 2.0)));
            }
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
            if(randNum == 0 && resourceCards.get(ResourceType.BRICK) > 0){
                pickedCards[0]++;
                counter++;
            } else if(randNum == 1 && resourceCards.get(ResourceType.LUMBER) > 0) {
                pickedCards[1]++;
                counter++;
            } else if(randNum == 2 && resourceCards.get(ResourceType.ORE) > 0){
                pickedCards[2]++;
                counter++;
            } else if(randNum == 3 && resourceCards.get(ResourceType.GRAIN) > 0){
                pickedCards[3]++;
                counter++;
            } else if(randNum == 4 && resourceCards.get(ResourceType.WOOL) > 0){
                pickedCards[4]++;
                counter++;
            }
            randNum = random.nextInt(5);
        }
        return pickedCards;
    }

    // Add other AI-specific methods or overrides as needed
}
