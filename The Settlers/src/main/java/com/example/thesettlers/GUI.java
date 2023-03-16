package com.example.thesettlers;

import com.example.thesettlers.enums.GameState;
import com.example.thesettlers.enums.ResourceType;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;

public class GUI {
    Random random = new Random();
    private Pane GUI = new Pane();
    private Pane boardPane;
    private Pane settlementPane;
    private Pane roadPane;
    private Pane permPane;
    private Rectangle dice2;
    private Rectangle dice1;
    private Scene scene = new Scene(GUI);
    private Game game;
    private GameBoard gameBoard;
    private ArrayList<Player> players;
    private ArrayList<Player> nonActivePlayers;
    private Text[] resourceValues;
    private Rectangle currentPlayerIcon;
    private Rectangle currentPlayerIconLabel;
    private String[] playerColours;
    private Rectangle buySettlement;
    private Rectangle buyRoad;
    private Rectangle buyCity;
    private ArrayList<Rectangle> playerIcons;
    private ArrayList<Rectangle> playerIconLabels;
    private Rectangle endTurnMenu;
    private Rectangle nextTurn;
    private Rectangle notEnoughResources;

    public GUI(Game game) throws URISyntaxException, IOException {
        this.game = game;
        this.gameBoard = game.getGameBoard();

        GUI.setId("GUI");
        permPane = new Pane();
        permPane.getChildren().addAll(gameBoard.getRoadPermPane(), gameBoard.getSettlementPermPane());
        gameBoard.getGameBoard().toFront();
        boardPane = gameBoard.getGameBoard();
        settlementPane = gameBoard.getSettlementPane();
        roadPane = gameBoard.getRoadPane();
        playerColours = new String[]{"red", "blue", "gold", "white"};
        players = game.getPlayers();
        nonActivePlayers = new ArrayList<>(players);
        nonActivePlayers.remove(game.getCurrentPlayer());
        playerIcons = new ArrayList<>();
        playerIconLabels = new ArrayList<>();
        nextTurn = new Rectangle(521.5,485,397,50.5);
        nextTurn.setFill(new ImagePattern(new Image(this.getClass().getResource("clicktocontinue.png").toExternalForm())));
        endTurnMenu = new Rectangle(0,0,1440,900);
        nextTurn.setVisible(false);
        endTurnMenu.setVisible(false);
        Rectangle portbg = new Rectangle(0,0,1440,900);
        portbg.setFill(new ImagePattern(new Image(this.getClass().getResource("portbg.png").toExternalForm())));
        GUI.getChildren().addAll(portbg,boardPane,settlementPane,roadPane,endTurnMenu,nextTurn,permPane);
        settlementPane.setVisible(true);
        roadPane.setVisible(false);

        Rectangle notEnoughResources = new Rectangle(644,712.5,270,42.5);
        notEnoughResources.setFill(new ImagePattern(new Image(this.getClass().getResource("notenoughresources.png").toExternalForm())));
        // TODO ADD IMAGE FOR MESSAGE!!!!
        GUI.getChildren().add(notEnoughResources);
        notEnoughResources.setVisible(false);
        notEnoughResources.toFront();

        Rectangle box = new Rectangle(45, 765, 715, 140);
        box.setFill(new ImagePattern(new Image(this.getClass().getResource("box.png").toExternalForm())));
        GUI.getChildren().add(box);

        Rectangle box2 = new Rectangle(774, 765, 140, 140);
        box2.setFill(new ImagePattern(new Image(this.getClass().getResource("box2.png").toExternalForm())));
        GUI.getChildren().add(box2);

        Rectangle currentPlayerBox = new Rectangle(960, 765, 450, 140);
        currentPlayerBox.setFill(new ImagePattern(new Image(this.getClass().getResource("pbox.png").toExternalForm())));

        currentPlayerIcon = new Rectangle(1000, 810,65, 65);
        currentPlayerIcon.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"player.png").toExternalForm())));
        currentPlayerIconLabel = new Rectangle(1020, 860, 25, 25);
        currentPlayerIconLabel.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"playerlabel.png").toExternalForm())));

        Rectangle resCards = new Rectangle(1105, 790, 60, 84);
        Rectangle resCardsLabel = new Rectangle(1105 + 17.5, 860, 25, 25);
        resCards.setFill(new ImagePattern(new Image(this.getClass().getResource("rescards.png").toExternalForm())));
        resCardsLabel.setFill(new ImagePattern(new Image(this.getClass().getResource("rescardlabel.png").toExternalForm())));

        Rectangle devCards = new Rectangle(1180, 790, 60, 84);
        Rectangle devCardsLabel = new Rectangle(1180 + 17.5, 860, 25, 25);
        devCards.setFill(new ImagePattern(new Image(this.getClass().getResource("devcard.png").toExternalForm())));
        devCardsLabel.setFill(new ImagePattern(new Image(this.getClass().getResource("devcardlabel.png").toExternalForm())));

        Rectangle largestArmy = new Rectangle(1255, 790, 60, 84);
        largestArmy.setFill(new ImagePattern(new Image(this.getClass().getResource("largestarmy.png").toExternalForm())));

        Rectangle longestRoad = new Rectangle(1330, 790, 60, 84);
        longestRoad.setFill(new ImagePattern(new Image(this.getClass().getResource("longestroad.png").toExternalForm())));

        GUI.getChildren().addAll(currentPlayerBox,currentPlayerIcon,currentPlayerIconLabel,resCards,resCardsLabel, devCards, devCardsLabel, largestArmy, longestRoad);
        currentPlayerBox.toBack();

        for (int y = 0; y < players.size() - 1; y++) {
            Rectangle playerBox = new Rectangle(960, 270 + (165 * y) , 450, 140);
            playerBox.setFill(new ImagePattern(new Image(this.getClass().getResource("p2box.png").toExternalForm())));

            Rectangle playerIcon = new Rectangle(1000, 315 + (165 * y) ,65, 65);
            playerIcon.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[(nonActivePlayers.get(y)).getPlayerID()-1]+"player.png").toExternalForm())));
            playerIcons.add(playerIcon);

            Rectangle playerIconLabel = new Rectangle(1020, 365 + (165 * y) , 25, 25);
            playerIconLabel.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[(nonActivePlayers.get(y)).getPlayerID()-1]+"playerlabel.png").toExternalForm())));
            playerIconLabels.add(playerIconLabel);

            Rectangle playerResCards = new Rectangle(1105, 295 + (165 * y) , 60, 84);
            Rectangle playerResCardsLabel = new Rectangle(1105 + 17.5, 365 + (165 * y), 25, 25);
            playerResCards.setFill(new ImagePattern(new Image(this.getClass().getResource("rescards.png").toExternalForm())));
            playerResCardsLabel.setFill(new ImagePattern(new Image(this.getClass().getResource("rescardlabel.png").toExternalForm())));

            Rectangle playerDevCards = new Rectangle(1180, 295 + (165 * y) , 60, 84);
            Rectangle layerDevCardsLabel = new Rectangle(1180 + 17.5, 365 + (165 * y), 25, 25);
            playerDevCards.setFill(new ImagePattern(new Image(this.getClass().getResource("devcard.png").toExternalForm())));
            layerDevCardsLabel.setFill(new ImagePattern(new Image(this.getClass().getResource("devcardlabel.png").toExternalForm())));

            Rectangle playerLargestArmy = new Rectangle(1255, 295 + (165 * y) , 60, 84);
            playerLargestArmy.setFill(new ImagePattern(new Image(this.getClass().getResource("largestarmy.png").toExternalForm())));

            Rectangle playerLongestRoad = new Rectangle(1330, 295 + (165 * y) , 60, 84);
            playerLongestRoad.setFill(new ImagePattern(new Image(this.getClass().getResource("longestroad.png").toExternalForm())));

            GUI.getChildren().addAll(playerBox,playerIcon,playerIconLabel,playerResCards,playerResCardsLabel,playerDevCards,layerDevCardsLabel,playerLargestArmy,playerLongestRoad);
            playerBox.toBack();
        }

        Rectangle tbox = new Rectangle(960, 765 - 165 - 165 - 165 - 220 - 25, 450, 220);
        tbox.setFill(new ImagePattern(new Image(this.getClass().getResource("tbox.png").toExternalForm())));
        GUI.getChildren().add(tbox);

        dice1 = new Rectangle(790, 790, 50, 50);
        dice1.setFill(new ImagePattern(new Image(this.getClass().getResource("d6.png").toExternalForm())));
        GUI.getChildren().add(dice1);

        dice2 = new Rectangle(850, 790, 50, 50);
        dice2.setFill(new ImagePattern(new Image(this.getClass().getResource("d2.png").toExternalForm())));
        GUI.getChildren().add(dice2);

        resourceValues = new Text[5]; //stores text boxes for resource values in here
        for (int y = 0; y < 5; y++) {
            Rectangle resCard = new Rectangle(72.5 + (70 * y), 790, 60, 84);
            Rectangle label = new Rectangle(90 + (70 * y), 860, 25, 25);
            resCard.setFill(new ImagePattern(new Image(this.getClass().getResource(ResourceType.values()[y].label + ".png").toExternalForm())));
            label.setFill(new ImagePattern(new Image(this.getClass().getResource(ResourceType.values()[y].label  + "label.png").toExternalForm())));
            Text text = new Text(90 + 6.25 +(70 * y), 860 + 20, String.valueOf(game.getCurrentPlayer().resourceCards.get(ResourceType.values()[y])));
            text.setFont(new Font(20));
            GUI.getChildren().addAll(resCard, label, text);
            resourceValues[y] = text; // adding text to array
        }

        Rectangle devCard = new Rectangle(442.5, 790, 60, 84);
        devCard.setFill(new ImagePattern(new Image(this.getClass().getResource("devcard.png").toExternalForm())));
        GUI.getChildren().add(devCard);

        buyRoad = new Rectangle(532.5 , 790, 60, 39.5);
        buyRoad.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"buyroad.png").toExternalForm())));
        buyRoad.setOnMouseClicked(e -> {
            if(game.getCurrentPlayer().getResourceCards().get(ResourceType.BRICK) > 0 && game.getCurrentPlayer().getResourceCards().get(ResourceType.LUMBER) > 0){
                settlementPane.setVisible(false);
                roadPane.setVisible(true);
            }
            else{
                notEnoughResources.setVisible(true);
                FadeTransition fade = new FadeTransition();
                //setting the duration for the Fade transition
                fade.setDuration(Duration.millis(3500));
                //setting the initial and the target opacity value for the transition
                fade.setFromValue(10);
                fade.setToValue(0);
                //setting Circle as the node onto which the transition will be applied
                fade.setNode(notEnoughResources);
                //playing the transition
                fade.play();
            }
            //TODO allow player to place road
            //TODO hide road spaces
        });

        buySettlement = new Rectangle(602.5, 790, 60, 39.5);
        buySettlement.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"buysettlement.png").toExternalForm())));
        buySettlement.setOnMouseClicked(e -> {
            if(game.getCurrentPlayer().getResourceCards().get(ResourceType.BRICK) > 0 && game.getCurrentPlayer().getResourceCards().get(ResourceType.GRAIN) > 0 && game.getCurrentPlayer().getResourceCards().get(ResourceType.LUMBER) > 0 && game.getCurrentPlayer().getResourceCards().get(ResourceType.WOOL) > 0){
                roadPane.setVisible(false);
                settlementPane.setVisible(true);
            }
            else{
                notEnoughResources.setVisible(true);
                FadeTransition fade = new FadeTransition();
                //setting the duration for the Fade transition
                fade.setDuration(Duration.millis(3500));
                //setting the initial and the target opacity value for the transition
                fade.setFromValue(10);
                fade.setToValue(0);
                //setting Circle as the node onto which the transition will be applied
                fade.setNode(notEnoughResources);
                //playing the transition
                fade.play();

            }
            //TODO check if player has correct roads for a settlement to be placed *** already done
            //TODO allow player to place settlement
            //TODO hide settlement spaces
        });

        buyCity = new Rectangle(672.5, 790, 60, 39.5);
        buyCity.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"buycity.png").toExternalForm())));
        buyCity.setOnMouseClicked(e -> {
            roadPane.setVisible(false);
            //TODO check if player has sufficient resources *** should this be done in deciding weather its clickable?
            //TODO allow player to turn settlement into city
            settlementPane.setVisible(true);
        });

        Rectangle buyDevCard = new Rectangle(532.5, 839.5, 60, 39.5);
        buyDevCard.setFill(new ImagePattern(new Image(this.getClass().getResource("buydev.png").toExternalForm())));
        buyDevCard.setOnMouseClicked(e -> {
            //TODO checks if player has sufficient resources ***done in playerclass*** just use try-catch
            //TODO allow player to buy development card
            //TODO display development card
        });

        Rectangle trade = new Rectangle(602.5, 839.5, 60, 39.5);
        trade.setFill(new ImagePattern(new Image(this.getClass().getResource("trade.png").toExternalForm())));
        trade.setOnMouseClicked(e -> {
            //TODO display trade menu
            //TODO allow players to trade
        });

        Rectangle endTurn = new Rectangle(602.5, 839.5, 130, 39.5);
        endTurn.setFill(new ImagePattern(new Image(this.getClass().getResource("endturn.png").toExternalForm())));
        endTurn.setOnMouseClicked(e -> {
            settlementPane.setVisible(false);
            roadPane.setVisible(false);
            game.nextPlayer();
            endTurnMenu.setFill(new ImagePattern(new Image(this.getClass().getResource("p"+(game.getCurrentPlayer().getPlayerID())+"endturn.png").toExternalForm())));
            endTurnMenu.setVisible(true);
            endTurnMenu.toFront();
            nextTurn.setVisible(true);
            nextTurn.toFront();
            nextTurn.setOnMouseClicked(ee -> {
                endTurnMenu.setVisible(false);
                nextTurn.setVisible(false);
            });
        });

        Rectangle rollDice = new Rectangle(800, 844.5 + 5, 90, 39.5);
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
        });
        GUI.getChildren().addAll(buyRoad, buySettlement, buyCity, buyDevCard, trade, endTurn, rollDice);
        portbg.toBack();
    }
    public void refreshUI() {
        for (int y = 0; y < 5; y++) {
            resourceValues[y].setText(String.valueOf(game.getCurrentPlayer().resourceCards.get(ResourceType.values()[y])));
        }
        currentPlayerIcon.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID() - 1] + "player.png").toExternalForm())));
        currentPlayerIconLabel.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID() - 1] + "playerlabel.png").toExternalForm())));
        buyRoad.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"buyroad.png").toExternalForm())));
        buySettlement.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"buysettlement.png").toExternalForm())));
        buyCity.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"buycity.png").toExternalForm())));
        players = game.getPlayers();
        nonActivePlayers = new ArrayList<>(players);
        nonActivePlayers.remove(game.getCurrentPlayer());
        for (int y = 0; y < players.size() - 1; y++) {
            playerIcons.get(y).setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[(nonActivePlayers.get(y)).getPlayerID() - 1] + "player.png").toExternalForm())));
            playerIconLabels.get(y).setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[(nonActivePlayers.get(y)).getPlayerID() - 1] + "playerlabel.png").toExternalForm())));
        }
    }
    public Scene getGUI() { //TODO neaten up this whole thing the numbers are wack *** the numbers should be relative to the window shape
        return scene;
    }
    public void diceRollAnimation() {
        Thread thread = new Thread() {
            public void run() {
                try {
                    int value1 = 0;
                    int value2 = 0;
                    for (int i = 0; i < 15; i++) {
                        value1 = random.nextInt(6) + 1;
                        value2 = random.nextInt(6) + 1;
                        dice1.setFill(new ImagePattern(new Image(this.getClass().getResource("d" + value1 + ".png").toExternalForm())));
                        dice2.setFill(new ImagePattern(new Image(this.getClass().getResource("d" + value2 + ".png").toExternalForm())));
                        Thread.sleep(50);
                    }
                    game.rollDice(value1, value2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}