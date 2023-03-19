package com.example.thesettlers;

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
    private Pane permanentPane;
    private Rectangle dice2;
    private Rectangle dice1;
    private Scene scene = new Scene(GUI);
    private Game game;
    private GameBoard gameBoard;
    private ArrayList<Player> players;
    private ArrayList<Player> nonActivePlayers;
    private Text[] currentResourceValues;
    private Text[] resCardsCount;
    private Text[] devCardsCount;
    private Text[] VPCount;
    private Text[] playerLargestArmyValue;
    private Text[] playerLongestRoadValue;
    private Rectangle CPIcon;
    private Rectangle CPIconLabel;
    private String[] playerColours;
    private Rectangle buySettlementButton;
    private Rectangle buyRoadButton;
    private Rectangle buyCityButton;
    private ArrayList<Rectangle> playerIcons;
    private ArrayList<Rectangle> playerIconLabels;
    private Rectangle endTurnPopUp;
    private Rectangle clickToContinueButton;
    private Rectangle notEnoughResourcesError;
    private Rectangle cantPlaceRoadError;
    private Rectangle cantPlaceSettlementError;
    private Rectangle rollDiceFirstError;
    private Rectangle winMessage;
    private boolean diceCanBeRolled;
    private Text CPResCardsCount;
    private Text CPLargestArmyValue;
    private Text CPLongestRoadValue;
    private Text CPDevCardsCount;
    private Text CPVPCount;
    //TODO NEATEN THIS CLASS
    //TODO RENAME THINGS TO MEANINGFUL NAMES
    public GUI(Game game) throws URISyntaxException, IOException {
        GUI.setId("GUI");

        this.game = game;
        gameBoard = game.getGameBoard();
        boardPane = gameBoard.getGameBoard();
        settlementPane = gameBoard.getSettlementPane();
        settlementPane.setVisible(true);
        roadPane = gameBoard.getRoadPane();
        roadPane.setVisible(false);
        permanentPane = new Pane();
        permanentPane.getChildren().addAll(gameBoard.getRoadPermPane(), gameBoard.getSettlementPermPane());

        Rectangle background = new Rectangle(0,0,1440,900);
        background.setFill(new ImagePattern(new Image(this.getClass().getResource("portbg.png").toExternalForm())));

        GUI.getChildren().addAll(background,boardPane,settlementPane,roadPane,permanentPane);

        playerColours = new String[]{"red", "blue", "gold", "white"};
        players = game.getPlayers();
        diceCanBeRolled = false;
        nonActivePlayers = new ArrayList<>(players);
        nonActivePlayers.remove(game.getCurrentPlayer());
        playerIcons = new ArrayList<>();
        playerIconLabels = new ArrayList<>();

        winMessage = new Rectangle(0,0,1440,900);
        winMessage.setVisible(false);
        GUI.getChildren().add(winMessage);

        endTurnPopUp = new Rectangle(0,0,1440,900);
        endTurnPopUp.setVisible(false);
        clickToContinueButton = new Rectangle(521.5,485,397,50.5);
        clickToContinueButton.setFill(new ImagePattern(new Image(this.getClass().getResource("clicktocontinue.png").toExternalForm())));
        clickToContinueButton.setVisible(false);
        GUI.getChildren().addAll(endTurnPopUp, clickToContinueButton);

        notEnoughResourcesError = new Rectangle(644,712.5,270,42.5);
        notEnoughResourcesError.setFill(new ImagePattern(new Image(this.getClass().getResource("notenoughresources.png").toExternalForm())));
        notEnoughResourcesError.setVisible(false);
        notEnoughResourcesError.toFront();
        GUI.getChildren().add(notEnoughResourcesError);

        cantPlaceRoadError = new Rectangle(644,712.5,270,42.5);
        cantPlaceRoadError.setFill(new ImagePattern(new Image(this.getClass().getResource("cantplaceroad.png").toExternalForm())));
        cantPlaceRoadError.setVisible(false);
        cantPlaceRoadError.toFront();
        GUI.getChildren().add(cantPlaceRoadError);

        cantPlaceSettlementError = new Rectangle(581.5,712.5,332.5,42.5);
        cantPlaceSettlementError.setFill(new ImagePattern(new Image(this.getClass().getResource("cantplacesettlement.png").toExternalForm())));
        cantPlaceSettlementError.setVisible(false);
        cantPlaceSettlementError.toFront();
        GUI.getChildren().add(cantPlaceSettlementError);

        rollDiceFirstError = new Rectangle(729,712.5,185,42.5);
        rollDiceFirstError.setFill(new ImagePattern(new Image(this.getClass().getResource("rolldicefirst.png").toExternalForm())));
        rollDiceFirstError.setVisible(false);
        rollDiceFirstError.toFront();
        GUI.getChildren().add(rollDiceFirstError);

        Rectangle CPResourceUI = new Rectangle(45, 765, 715, 140);
        CPResourceUI.setFill(new ImagePattern(new Image(this.getClass().getResource("box.png").toExternalForm())));
        GUI.getChildren().add(CPResourceUI);

        Rectangle diceUI = new Rectangle(774, 765, 140, 140);
        diceUI.setFill(new ImagePattern(new Image(this.getClass().getResource("box2.png").toExternalForm())));
        GUI.getChildren().add(diceUI);

        Rectangle CPUI = new Rectangle(960, 765, 450, 140);
        CPUI.setFill(new ImagePattern(new Image(this.getClass().getResource("pbox.png").toExternalForm())));
        CPUI.toBack();
        GUI.getChildren().add(CPUI);

        CPIcon = new Rectangle(1000, 810,65, 65);
        CPIcon.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"player.png").toExternalForm())));
        CPIconLabel = new Rectangle(1020, 860, 25, 25);
        CPIconLabel.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"playerlabel.png").toExternalForm())));
        CPVPCount = new Text(1020 + 6.25, 860 + 20, String.valueOf(game.getCurrentPlayer().getVictoryPoints()));
        CPVPCount.setFont(new Font(20));

        Rectangle CPResCards = new Rectangle(1105, 790, 60, 84);
        Rectangle CPResCardsLabel = new Rectangle(1105 + 17.5, 860, 25, 25);
        CPResCards.setFill(new ImagePattern(new Image(this.getClass().getResource("rescards.png").toExternalForm())));
        CPResCardsLabel.setFill(new ImagePattern(new Image(this.getClass().getResource("rescardlabel.png").toExternalForm())));
        int CPResCount = 0;
        for (int z=0; z < 5;z++){
            CPResCount += game.getCurrentPlayer().resourceCards.get(ResourceType.values()[z]);
        }
        CPResCardsCount = new Text(1105 + 17.5 + 6.25, 860 + 20, String.valueOf(CPResCount));
        CPResCardsCount.setFont(new Font(20));

        Rectangle CPDevCards = new Rectangle(1180, 790, 60, 84);
        Rectangle CPDevCardsLabel = new Rectangle(1180 + 17.5, 860, 25, 25);
        CPDevCards.setFill(new ImagePattern(new Image(this.getClass().getResource("devcard.png").toExternalForm())));
        CPDevCardsLabel.setFill(new ImagePattern(new Image(this.getClass().getResource("devcardlabel.png").toExternalForm())));
        CPDevCardsCount = new Text(1180 + 17.5 + 6.25, 860 + 20, String.valueOf(game.getCurrentPlayer().getDevelopmentCards().size()));
        CPDevCardsCount.setFont(new Font(20));

        Rectangle CPLargestArmy = new Rectangle(1255, 790, 60, 84);
        CPLargestArmy.setFill(new ImagePattern(new Image(this.getClass().getResource("largestarmy.png").toExternalForm())));
        CPLargestArmyValue = new Text(1255+30- 6.25, 860 + 20, String.valueOf(game.getCurrentPlayer().getArmySize()));
        CPLargestArmyValue.setFont(new Font(20));;

        Rectangle CPLongestRoad = new Rectangle(1330, 790, 60, 84);
        CPLongestRoad.setFill(new ImagePattern(new Image(this.getClass().getResource("longestroad.png").toExternalForm())));
        CPLongestRoadValue = new Text(1330+30- 6.25, 860 + 20, String.valueOf(game.getCurrentPlayer().getLongestRoadLength()));
        CPLongestRoadValue.setFont(new Font(20));;

        GUI.getChildren().addAll(CPIcon, CPIconLabel,CPResCards,CPResCardsLabel, CPDevCards, CPDevCardsLabel, CPResCardsCount, CPLongestRoad, CPLargestArmy, CPDevCardsCount,CPLongestRoadValue,CPLargestArmyValue,CPVPCount);

        resCardsCount = new Text[players.size() - 1];
        devCardsCount = new Text[players.size() - 1];
        VPCount = new Text[players.size() - 1];
        playerLargestArmyValue = new Text[players.size() - 1];
        playerLongestRoadValue = new Text[players.size() - 1];
        for (int y = 0; y < players.size() - 1; y++) {
            Rectangle playerUI = new Rectangle(960, 270 + (165 * y) , 450, 140);
            playerUI.setFill(new ImagePattern(new Image(this.getClass().getResource("p2box.png").toExternalForm())));
            Rectangle playerIcon = new Rectangle(1000, 315 + (165 * y) ,65, 65);
            playerIcon.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[(nonActivePlayers.get(y)).getPlayerID()-1]+"player.png").toExternalForm())));
            playerIcons.add(playerIcon);

            Rectangle playerIconLabel = new Rectangle(1020, 365 + (165 * y) , 25, 25);
            playerIconLabel.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[(nonActivePlayers.get(y)).getPlayerID()-1]+"playerlabel.png").toExternalForm())));
            playerIconLabels.add(playerIconLabel);
            Text VPCount = new Text(1020 + 6.25, 365 + (165 * y) + 20, String.valueOf(nonActivePlayers.get(y).getVictoryPoints()));
            VPCount.setFont(new Font(20));
            this.VPCount[y] = VPCount;

            Rectangle playerResCards = new Rectangle(1105, 295 + (165 * y) , 60, 84);
            Rectangle playerResCardsLabel = new Rectangle(1105 + 17.5, 365 + (165 * y), 25, 25);
            playerResCards.setFill(new ImagePattern(new Image(this.getClass().getResource("rescards.png").toExternalForm())));
            playerResCardsLabel.setFill(new ImagePattern(new Image(this.getClass().getResource("rescardlabel.png").toExternalForm())));
            int resCount = 0;
            for (int z=0; z < 5;z++){
                 resCount += nonActivePlayers.get(y).resourceCards.get(ResourceType.values()[z]);
            }
            Text resCardsCount = new Text(1105 + 17.5 + 6.25, 365 + (165 * y) + 20, String.valueOf(resCount));
            resCardsCount.setFont(new Font(20));
            this.resCardsCount[y] = resCardsCount;

            Rectangle playerDevCards = new Rectangle(1180, 295 + (165 * y) , 60, 84);
            Rectangle layerDevCardsLabel = new Rectangle(1180 + 17.5, 365 + (165 * y), 25, 25);
            playerDevCards.setFill(new ImagePattern(new Image(this.getClass().getResource("devcard.png").toExternalForm())));
            layerDevCardsLabel.setFill(new ImagePattern(new Image(this.getClass().getResource("devcardlabel.png").toExternalForm())));
            Text devCardsCount = new Text(1180 + 17.5 + 6.25, 365 + (165 * y) + 20, String.valueOf(nonActivePlayers.get(y).getDevelopmentCards().size()));
            devCardsCount.setFont(new Font(20));
            this.devCardsCount[y] = devCardsCount;

            Rectangle playerLargestArmy = new Rectangle(1255, 295 + (165 * y) , 60, 84);
            playerLargestArmy.setFill(new ImagePattern(new Image(this.getClass().getResource("largestarmy.png").toExternalForm())));
            Text playerLargestArmyValue = new Text(1255+30- 6.25, 365 + (165 * y) + 20, String.valueOf(nonActivePlayers.get(y).getArmySize()));
            playerLargestArmyValue.setFont(new Font(20));;
            this.playerLargestArmyValue[y] = playerLargestArmyValue;

            Rectangle playerLongestRoad = new Rectangle(1330, 295 + (165 * y) , 60, 84);
            playerLongestRoad.setFill(new ImagePattern(new Image(this.getClass().getResource("longestroad.png").toExternalForm())));
            Text playerLongestRoadValue = new Text(1330+30- 6.25, 365 + (165 * y) + 20, String.valueOf(nonActivePlayers.get(y).getLongestRoadLength()));
            playerLongestRoadValue.setFont(new Font(20));;
            this.playerLongestRoadValue[y] = playerLongestRoadValue;

            GUI.getChildren().addAll(playerUI,playerIcon,playerIconLabel,playerResCards,playerResCardsLabel,playerDevCards,layerDevCardsLabel,playerLargestArmy,playerLongestRoad,resCardsCount,playerLongestRoadValue,playerLargestArmyValue,devCardsCount,VPCount);
            resCardsCount.toFront();
            devCardsCount.toFront();
            VPCount.toFront();
            playerUI.toBack();
        }

        Rectangle tradeUI = new Rectangle(960, 765 - 165 - 165 - 165 - 220 - 25, 450, 220);
        tradeUI.setFill(new ImagePattern(new Image(this.getClass().getResource("tbox.png").toExternalForm())));
        GUI.getChildren().add(tradeUI);

        dice1 = new Rectangle(790, 790, 50, 50);
        dice1.setFill(new ImagePattern(new Image(this.getClass().getResource("d6.png").toExternalForm())));
        GUI.getChildren().add(dice1);

        dice2 = new Rectangle(850, 790, 50, 50);
        dice2.setFill(new ImagePattern(new Image(this.getClass().getResource("d2.png").toExternalForm())));
        GUI.getChildren().add(dice2);

        currentResourceValues = new Text[5]; //stores text boxes for resource values in here
        for (int y = 0; y < 5; y++) {
            Rectangle resCard = new Rectangle(72.5 + (70 * y), 790, 60, 84);
            Rectangle label = new Rectangle(90 + (70 * y), 860, 25, 25);
            resCard.setFill(new ImagePattern(new Image(this.getClass().getResource(ResourceType.values()[y].label + ".png").toExternalForm())));
            label.setFill(new ImagePattern(new Image(this.getClass().getResource(ResourceType.values()[y].label  + "label.png").toExternalForm())));
            Text text = new Text(90 + 6.25 +(70 * y), 860 + 20, String.valueOf(game.getCurrentPlayer().resourceCards.get(ResourceType.values()[y])));
            text.setFont(new Font(20));
            GUI.getChildren().addAll(resCard, label, text);
            currentResourceValues[y] = text; // adding text to array
        }

        Rectangle devCard = new Rectangle(442.5, 790, 60, 84);
        devCard.setFill(new ImagePattern(new Image(this.getClass().getResource("devcard.png").toExternalForm())));
        GUI.getChildren().add(devCard);

        buyRoadButton = new Rectangle(532.5 , 790, 60, 39.5);
        buyRoadButton.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"buyroad.png").toExternalForm())));
        buyRoadButton.setOnMouseClicked(e -> {
            if (diceCanBeRolled == false) {
                if (game.getCurrentPlayer().getResourceCards().get(ResourceType.BRICK) > 0 && game.getCurrentPlayer().getResourceCards().get(ResourceType.LUMBER) > 0) {
                    settlementPane.setVisible(false);
                    //TODO spaces only appear where roads can be placed
                    roadPane.setVisible(true);
                } else {
                    notEnoughResourcesError();
                }
            }
            else{
                rollDiceFirstError();
                System.out.println("dice must be rolled first");
            }
        });

        buySettlementButton = new Rectangle(602.5, 790, 60, 39.5);
        buySettlementButton.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"buysettlement.png").toExternalForm())));
        buySettlementButton.setOnMouseClicked(e -> {
            if (diceCanBeRolled == false) {
                if (game.getCurrentPlayer().getResourceCards().get(ResourceType.BRICK) > 0 && game.getCurrentPlayer().getResourceCards().get(ResourceType.GRAIN) > 0 && game.getCurrentPlayer().getResourceCards().get(ResourceType.LUMBER) > 0 && game.getCurrentPlayer().getResourceCards().get(ResourceType.WOOL) > 0) {
                    roadPane.setVisible(false);
                    settlementPane.setVisible(true);
                } else {
                    notEnoughResourcesError();
                }
            }
            else{
                rollDiceFirstError();
                System.out.println("dice must be rolled first");
            }
        });

        buyCityButton = new Rectangle(672.5, 790, 60, 39.5);
        buyCityButton.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"buycity.png").toExternalForm())));
        buyCityButton.setOnMouseClicked(e -> {
            if (diceCanBeRolled == false) {
                if (game.getCurrentPlayer().getResourceCards().get(ResourceType.ORE) > 2 && game.getCurrentPlayer().getResourceCards().get(ResourceType.GRAIN) > 1) {
                    roadPane.setVisible(false);
                    settlementPane.setVisible(true);
                } else {
                    notEnoughResourcesError();
                }
            }
            else{
                rollDiceFirstError();
                System.out.println("dice must be rolled first");
            }
        });

        Rectangle buyDevCardButton = new Rectangle(532.5, 839.5, 60, 39.5);
        buyDevCardButton.setFill(new ImagePattern(new Image(this.getClass().getResource("buydev.png").toExternalForm())));
        buyDevCardButton.setOnMouseClicked(e -> {
            if (diceCanBeRolled == false) {

                if (game.getCurrentPlayer().getResourceCards().get(ResourceType.ORE) > 0 && game.getCurrentPlayer().getResourceCards().get(ResourceType.WOOL) > 0 && game.getCurrentPlayer().getResourceCards().get(ResourceType.GRAIN) > 0) {
                    //TODO allow player to buy development card
                    //TODO display development card
                } else {
                    notEnoughResourcesError();
                }
            }
            else{
                rollDiceFirstError();
                System.out.println("dice must be rolled first");
            }
        });

        Rectangle endTurnButton = new Rectangle(602.5, 839.5, 130, 39.5);
        endTurnButton.setFill(new ImagePattern(new Image(this.getClass().getResource("endturn.png").toExternalForm())));
        endTurnButton.setOnMouseClicked(e -> {
            if (diceCanBeRolled == false){
                settlementPane.setVisible(false);
                roadPane.setVisible(false);
                game.nextPlayer();
                endTurnMenu();
            }
            else{
                rollDiceFirstError();
                System.out.println("dice must be rolled first");
            }
        });

        Rectangle rollDiceButton = new Rectangle(800, 844.5 + 5, 90, 39.5);
        rollDiceButton.setFill(new ImagePattern(new Image(this.getClass().getResource("roll.png").toExternalForm())));
        rollDiceButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                scene.setCursor(Cursor.HAND);
            }
        });
        rollDiceButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });

        rollDiceButton.setOnMouseClicked(e -> {
            if (diceCanBeRolled == true){
                diceCanBeRolled = false;
                diceRollAnimation();
            }
            else{
                System.out.println("dice can't be rolled");
            }
        });
        GUI.getChildren().addAll(buyRoadButton, buySettlementButton, buyCityButton, buyDevCardButton, endTurnButton, rollDiceButton);
        background.toBack();
    }

    public void refreshUI() {
        int currentPlayerResNumber = 0;
        for (int y = 0; y < 5; y++) {
            currentResourceValues[y].setText(String.valueOf(game.getCurrentPlayer().resourceCards.get(ResourceType.values()[y])));
            currentPlayerResNumber += game.getCurrentPlayer().resourceCards.get(ResourceType.values()[y]);
        }
        CPResCardsCount.setText(String.valueOf(currentPlayerResNumber));
        CPDevCardsCount.setText(String.valueOf(game.getCurrentPlayer().getDevelopmentCards().size()));
        CPIcon.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID() - 1] + "player.png").toExternalForm())));
        CPIconLabel.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID() - 1] + "playerlabel.png").toExternalForm())));
        CPVPCount.setText(String.valueOf(game.getCurrentPlayer().getVictoryPoints()));
        CPLargestArmyValue.setText(String.valueOf(game.getCurrentPlayer().getArmySize()));
        CPLongestRoadValue.setText(String.valueOf(game.getCurrentPlayer().getLongestRoadLength()));
        buyRoadButton.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"buyroad.png").toExternalForm())));
        buySettlementButton.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"buysettlement.png").toExternalForm())));
        buyCityButton.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"buycity.png").toExternalForm())));
        players = game.getPlayers();
        nonActivePlayers = new ArrayList<>(players);
        nonActivePlayers.remove(game.getCurrentPlayer());
        for (int y = 0; y < players.size() - 1; y++) {
            playerIcons.get(y).setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[(nonActivePlayers.get(y)).getPlayerID() - 1] + "player.png").toExternalForm())));
            playerIconLabels.get(y).setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[(nonActivePlayers.get(y)).getPlayerID() - 1] + "playerlabel.png").toExternalForm())));
            int altResNumber = 0;
            for (int z=0; z < 5;z++){
                altResNumber += nonActivePlayers.get(y).resourceCards.get(ResourceType.values()[z]);
            }
            resCardsCount[y].setText(String.valueOf(altResNumber));
            devCardsCount[y].setText(String.valueOf(nonActivePlayers.get(y).getDevelopmentCards().size()));
            VPCount[y].setText(String.valueOf(nonActivePlayers.get(y).getVictoryPoints()));
            playerLongestRoadValue[y].setText(String.valueOf(nonActivePlayers.get(y).getLongestRoadLength()));
            playerLargestArmyValue[y].setText(String.valueOf(nonActivePlayers.get(y).getArmySize()));
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
    public void notEnoughResourcesError(){
        notEnoughResourcesError.setVisible(true);
        FadeTransition fade = new FadeTransition();
        //setting the duration for the Fade transition
        fade.setDuration(Duration.millis(3500));
        //setting the initial and the target opacity value for the transition
        fade.setFromValue(10);
        fade.setToValue(0);
        //setting Circle as the node onto which the transition will be applied
        fade.setNode(notEnoughResourcesError);
        //playing the transition
        fade.play();
    }

    public void cantPlaceSettlementError(){
        cantPlaceSettlementError.setVisible(true);
        FadeTransition fade = new FadeTransition();
        //setting the duration for the Fade transition
        fade.setDuration(Duration.millis(3500));
        //setting the initial and the target opacity value for the transition
        fade.setFromValue(10);
        fade.setToValue(0);
        //setting Circle as the node onto which the transition will be applied
        fade.setNode(cantPlaceSettlementError);
        //playing the transition
        fade.play();
    }

    public void cantPlaceRoadError(){
        cantPlaceRoadError.setVisible(true);
        FadeTransition fade = new FadeTransition();
        //setting the duration for the Fade transition
        fade.setDuration(Duration.millis(3500));
        //setting the initial and the target opacity value for the transition
        fade.setFromValue(10);
        fade.setToValue(0);
        //setting Circle as the node onto which the transition will be applied
        fade.setNode(cantPlaceRoadError);
        //playing the transition
        fade.play();
    }

    public void rollDiceFirstError(){
        rollDiceFirstError.setVisible(true);
        FadeTransition fade = new FadeTransition();
        //setting the duration for the Fade transition
        fade.setDuration(Duration.millis(3500));
        //setting the initial and the target opacity value for the transition
        fade.setFromValue(10);
        fade.setToValue(0);
        //setting Circle as the node onto which the transition will be applied
        fade.setNode(rollDiceFirstError);
        //playing the transition
        fade.play();
    }

    public void endTurnMenu(){
        endTurnPopUp.setFill(new ImagePattern(new Image(this.getClass().getResource("p"+(game.getCurrentPlayer().getPlayerID())+"endturn.png").toExternalForm())));
        endTurnPopUp.setVisible(true);
        endTurnPopUp.toFront();
        clickToContinueButton.setVisible(true);
        clickToContinueButton.toFront();
        clickToContinueButton.setOnMouseClicked(ee -> {
            endTurnPopUp.setVisible(false);
            clickToContinueButton.setVisible(false);
        });
    }

    public void winMessage(){
        //TODO add win graphic
        winMessage.setVisible(true);
        winMessage.toFront();
    }

    public void setDiceCanBeRolledTrue(){
        diceCanBeRolled = true;
    }
}