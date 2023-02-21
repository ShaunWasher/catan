package com.example.thesettlers;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.Random;

public class GUI {
    Random random = new Random();
    private Pane GUI = new Pane();
    private Pane boardPane;
    private Pane settlementPane;
    private Pane roadPane;
    private Rectangle box;
    private Rectangle dice2;
    private Rectangle dice1;
    private GameBoard gameBoard;
    private Scene scene = new Scene(GUI);
    private Game game;
    private Player currentPlayer;

    public GUI(GameBoard gameboard){
        this.gameBoard = gameboard;
        GUI.setId("GUI");
        boardPane = gameBoard.getBoard();
        settlementPane = gameboard.getSettlementPane();
        roadPane = gameboard.getRoadPane();
        game = new Game(gameBoard, 4); //TODO get number of players from setup screen
        currentPlayer = game.getCurrentPlayer();
    }
    public Scene getGUI() { //TODO neaten up this whole thing the numbers are wack *** the numbers should be relative to the window shape
        GUI.getChildren().addAll(boardPane,settlementPane,roadPane);
        settlementPane.setVisible(false);
        roadPane.setVisible(false);

        box = new Rectangle(360 - 75, 765, 715, 140);
        box.setFill(new ImagePattern(new Image(this.getClass().getResource("box.png").toExternalForm())));
        GUI.getChildren().add(box);

        Rectangle box2 = new Rectangle(1090 - 75, 765, 140, 140);
        box2.setFill(new ImagePattern(new Image(this.getClass().getResource("box2.png").toExternalForm())));
        GUI.getChildren().add(box2);

        dice1 = new Rectangle(1090 - 60, 790, 50, 50);
        dice1.setFill(new ImagePattern(new Image(this.getClass().getResource("d6.png").toExternalForm())));
        GUI.getChildren().add(dice1);

        dice2 = new Rectangle(1090, 790, 50, 50);
        dice2.setFill(new ImagePattern(new Image(this.getClass().getResource("d2.png").toExternalForm())));
        GUI.getChildren().add(dice2);

        String[] resourceTypes = {"brick", "lumber", "ore", "grain", "wool"};
        for (int y = 0; y < 5; y++) {
            Rectangle resCard = new Rectangle(380 - 67.5 + (70 * y), 790, 60, 84);
            Rectangle label = new Rectangle(397.5 - 67.5 + (70 * y), 860, 25, 25);
            resCard.setFill(new ImagePattern(new Image(this.getClass().getResource(resourceTypes[y] + ".png").toExternalForm())));
            label.setFill(new ImagePattern(new Image(this.getClass().getResource(resourceTypes[y] + "label.png").toExternalForm())));
            GUI.getChildren().addAll(resCard, label);

        }
        Rectangle devCard = new Rectangle(750 - 67.5, 790, 60, 84);
        devCard.setFill(new ImagePattern(new Image(this.getClass().getResource("devcard.png").toExternalForm())));
        GUI.getChildren().add(devCard);

        Rectangle buyRoad = new Rectangle(840 - 67.5, 790, 60, 39.5);
        buyRoad.setFill(new ImagePattern(new Image(this.getClass().getResource("buyroad.png").toExternalForm())));
        buyRoad.setOnMouseClicked(e -> {
            //TODO check if player has sufficient resources *** should this be done in deciding weather its clickable?
            roadPane.setVisible(true);
            //TODO allow player to place road
            //TODO hide road spaces
        });

        Rectangle buySettlement = new Rectangle(910 - 67.5, 790, 60, 39.5);
        buySettlement.setFill(new ImagePattern(new Image(this.getClass().getResource("buysettlement.png").toExternalForm())));
        buySettlement.setOnMouseClicked(e -> {
            //TODO check if player has sufficient resources *** should this be done in deciding weather its clickable?
            //TODO check if player has correct roads for a settlement to be placed *** already done
            settlementPane.setVisible(true);
            //TODO allow player to place settlement
            //TODO hide settlement spaces
        });

        Rectangle buyCity = new Rectangle(980 - 67.5, 790, 60, 39.5);
        buyCity.setFill(new ImagePattern(new Image(this.getClass().getResource("buycity.png").toExternalForm())));
        buyCity.setOnMouseClicked(e -> {
            //TODO check if player has sufficient resources *** should this be done in deciding weather its clickable?
            //TODO allow player to turn settlement into city
        });

        Rectangle buyDevCard = new Rectangle(840 - 67.5, 839.5, 60, 39.5);
        buyDevCard.setFill(new ImagePattern(new Image(this.getClass().getResource("buydev.png").toExternalForm())));
        buyDevCard.setOnMouseClicked(e -> {
            //TODO checks if player has sufficient resources ***done in playerclass*** just use try-catch
            //TODO allow player to buy development card
            //TODO display development card
        });

        Rectangle trade = new Rectangle(910 - 67.5, 839.5, 60, 39.5);
        trade.setFill(new ImagePattern(new Image(this.getClass().getResource("trade.png").toExternalForm())));
        trade.setOnMouseClicked(e -> {
            //TODO display trade menu
            //TODO allow players to trade
        });

        Rectangle endTurn = new Rectangle(980 - 67.5, 839.5, 60, 39.5);
        endTurn.setFill(new ImagePattern(new Image(this.getClass().getResource("endturn.png").toExternalForm())));
        endTurn.setOnMouseClicked(e -> {
            //TODO end players turn
        });


        Rectangle rollDice = new Rectangle(1040, 844.5 + 5, 90, 39.5);
        rollDice.setFill(new ImagePattern(new Image(this.getClass().getResource("roll.png").toExternalForm())));
        rollDice.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                scene.setCursor(Cursor.HAND);
            }
        });
        rollDice.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });

        rollDice.setOnMouseClicked(e -> {
            diceRollAnimation();
            //TODO return dice roll value
            dice1.setFill(new ImagePattern(new Image(this.getClass().getResource("d1.png").toExternalForm())));
            dice2.setFill(new ImagePattern(new Image(this.getClass().getResource("d1.png").toExternalForm())));
        });

        GUI.getChildren().addAll(buyRoad, buySettlement, buyCity, buyDevCard, trade, endTurn, rollDice);
        return scene;
    }
    public void diceRollAnimation() {
        Thread thread = new Thread() {
            public void run() {
                try {
                    for (int i = 0; i < 15; i++) {
                        dice1.setFill(new ImagePattern(new Image(this.getClass().getResource("d" + (random.nextInt(6) + 1) + ".png").toExternalForm())));
                        dice2.setFill(new ImagePattern(new Image(this.getClass().getResource("d" + (random.nextInt(6) + 1) + ".png").toExternalForm())));
                        Thread.sleep(50);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}