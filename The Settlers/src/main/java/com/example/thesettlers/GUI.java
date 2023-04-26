package com.example.thesettlers;

import com.example.thesettlers.enums.DevelopmentCardType;
import com.example.thesettlers.enums.GameState;
import com.example.thesettlers.enums.ResourceType;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GUI {
    //region Variables
    Random random = new Random();
    private Pane GUI = new Pane();
    private Pane boardPane;
    private Rectangle takeCardsButton;
    private Pane settlementPane;
    private Pane roadPane;
    private Pane permanentPane;
    private Pane developmentCards;
    private Rectangle dice2;
    private Rectangle dice1;
    private Game game;
    private int tradeCount;
    private GameBoard gameBoard;
    private ArrayList<Player> players;
    private ArrayList<Player> nonActivePlayers;
    private Integer[] CPtradeSelectionValues;
    private Text[] CPtradeSelectionTexts;
    private Integer[] tradeSelectionValues;
    private Text[] tradeSelectionTexts;
    private Rectangle[] downArrows;
    private Rectangle[] upArrows;
    private Rectangle[] popUpCPtradeResCards;
    private Rectangle[] popUPtradeResCards;
    private Text[] currentResourceValues;
    private Rectangle discardButton;
    private Text[] resCardsCount;
    private Text[] devCardsCount;
    private Text[] VPCount;
    private Text[] playerLargestArmyValue;
    private Text[] playerLongestRoadValue;
    private Text[] currentDevCardValues;
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
    private Rectangle tooManyRoadsError;
    private Rectangle tooManySettlementsError;
    private Rectangle rollDiceFirstError;
    private Rectangle tooManyCitiesError;
    private Rectangle unfairTradeError;
    private Rectangle winMessage;
    private Text discardCount;
    private Rectangle tradePopUp;
    private Rectangle developmentCardsUI;
    private Rectangle popUpCPtradeIcon;
    private Rectangle popUpPlayerTradeIcon;
    private Rectangle CPTradeIcon;
    private Rectangle acceptTrade;
    private Rectangle declineTrade;
    private Text[] popUpCPtradeSelectionTexts;
    private Text CPResCardsCount;
    private Text CPLargestArmyValue;
    private Text CPLongestRoadValue;
    private Text CPDevCardsCount;
    private Text CPVPCount;
    private int yearOfPlentyCount;
    private Text[] popUpTradeSelectionTexts;
    private Rectangle[] popUpDownArrows;
    private Rectangle selectTradePopUp;
    private Rectangle[] popUpUpArrows;
    private Rectangle[] popUpTradeResCardLabels;
    private Text[]  popUpTradeResCardCounts;
    private ArrayList<Player> acceptedTrades;
    private boolean diceCanBeRolled;
    private Group endTurn;
    private Rectangle[] yearOfPlentyResCards;
    private Text[] yearOfPlentyValueTexts;
    private Integer[] yearOfPlentyValues;
    private Group yearOfPlenty;
    private Group throwAway;
    private Rectangle[] throwAwayResCards;
    private Text[] throwAwayValueTexts;
    private Integer[] throwAwayValues;
    private int throwAwayCount;
    private Rectangle[] monopolyResCards;
    private ResourceType monopolyResType;
    private Rectangle confirmButton;

    private Group monopoly;
    //endregion
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
        developmentCards = new Pane();
        developmentCards.setVisible(false);
        permanentPane.getChildren().addAll(gameBoard.getRoadPermPane(), gameBoard.getSettlementPermPane());
        Rectangle background = new Rectangle(0, 0, 1440, 900);
        background.setFill(new ImagePattern(new Image(this.getClass().getResource("portbg.png").toExternalForm())));

        GUI.getChildren().addAll(background, boardPane, settlementPane, roadPane, permanentPane);

        playerColours = new String[]{"red", "blue", "gold", "white"};
        players = game.getPlayers();
        diceCanBeRolled = false;
        nonActivePlayers = new ArrayList<>(players);
        nonActivePlayers.remove(game.getCurrentPlayer());
        playerIcons = new ArrayList<>();
        playerIconLabels = new ArrayList<>();
        acceptedTrades = new ArrayList<>();


        //FIXME
        Rectangle testBox = new Rectangle(100, 100, 100, 100);
        GUI.getChildren().add(testBox);
        testBox.setOnMouseClicked(e -> {
            game.getCurrentPlayer().resourceCards.merge(ResourceType.BRICK, 5, Integer::sum);
            game.getCurrentPlayer().resourceCards.merge(ResourceType.LUMBER, 5, Integer::sum);
            game.getCurrentPlayer().resourceCards.merge(ResourceType.GRAIN, 5, Integer::sum);
            game.getCurrentPlayer().resourceCards.merge(ResourceType.WOOL, 5, Integer::sum);
            game.getCurrentPlayer().resourceCards.merge(ResourceType.ORE, 5, Integer::sum);
        });
        //FIXME

        //---------------------CURRENT PLAYER UI---------------------

        Rectangle CPUI = new Rectangle(960, 765, 450, 140);
        CPUI.setFill(new ImagePattern(new Image(this.getClass().getResource("pbox.png").toExternalForm())));
        CPUI.toBack();
        GUI.getChildren().add(CPUI);

        Rectangle CPResourceUI = new Rectangle(45, 765, 715, 140);
        CPResourceUI.setFill(new ImagePattern(new Image(this.getClass().getResource("box.png").toExternalForm())));
        GUI.getChildren().add(CPResourceUI);

        developmentCardsUI = new Rectangle(45, 610, 395, 140);
        developmentCardsUI.setFill(new ImagePattern(new Image(this.getClass().getResource("dbox.png").toExternalForm())));
        developmentCards.getChildren().add(developmentCardsUI);
        GUI.getChildren().add(developmentCards);

        CPIcon = new Rectangle(1000, 810, 65, 65);
        CPIcon.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID() - 1] + "player.png").toExternalForm())));
        CPIconLabel = new Rectangle(1020, 860, 25, 25);
        CPIconLabel.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID() - 1] + "playerlabel.png").toExternalForm())));
        CPVPCount = new Text(1020 + 6.25, 860 + 20, String.valueOf(game.getCurrentPlayer().getVictoryPoints()));
        CPVPCount.setFont(new Font(20));

        Rectangle CPResCards = new Rectangle(1105, 790, 60, 84);
        Rectangle CPResCardsLabel = new Rectangle(1105 + 17.5, 860, 25, 25);
        CPResCards.setFill(new ImagePattern(new Image(this.getClass().getResource("rescards.png").toExternalForm())));
        CPResCardsLabel.setFill(new ImagePattern(new Image(this.getClass().getResource("rescardlabel.png").toExternalForm())));
        int CPResCount = 0;
        for (int z = 0; z < 5; z++) {
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
        CPLargestArmyValue = new Text(1255 + 30 - 6.25, 860 + 20, String.valueOf(game.getCurrentPlayer().getArmySize()));
        CPLargestArmyValue.setFont(new Font(20));

        Rectangle CPLongestRoad = new Rectangle(1330, 790, 60, 84);
        CPLongestRoad.setFill(new ImagePattern(new Image(this.getClass().getResource("longestroad.png").toExternalForm())));
        CPLongestRoadValue = new Text(1330 + 30 - 6.25, 860 + 20, String.valueOf(game.getCurrentPlayer().getLongestRoadLength()));
        CPLongestRoadValue.setFont(new Font(20));

        //---------------------DEVELOPMENT CARDS---------------------

        Rectangle devCard = new Rectangle(442.5, 790, 60, 84);
        devCard.setFill(new ImagePattern(new Image(this.getClass().getResource("devcard.png").toExternalForm())));
        GUI.getChildren().add(devCard);
        Tooltip clickToOpen = new Tooltip();
        ImageView clickToOpenImg = new ImageView(new Image(this.getClass().getResource("clickToOpen.png").toExternalForm()));
        clickToOpenImg.setPreserveRatio(true);
        clickToOpenImg.setFitHeight(16);
        clickToOpen.setGraphic(clickToOpenImg);
        Tooltip.install(devCard, clickToOpen);

        devCard.setOnMouseClicked(e -> {
            if (game.gameState == GameState.MAIN) {
                if (!diceCanBeRolled) {
                    developmentCards.setVisible(!developmentCards.isVisible());
                } else {
                    showError(rollDiceFirstError);
                    System.out.println("dice must be rolled first");
                }
            }
        });

        Tooltip clickToUse = new Tooltip();
        ImageView clickToUseImg = new ImageView(new Image(this.getClass().getResource("clickToUse.png").toExternalForm()));
        clickToUseImg.setPreserveRatio(true);
        clickToUseImg.setFitHeight(12.5);
        clickToUse.setGraphic(clickToUseImg);

        //---------------------KNIGHT CARD---------------------

        Rectangle knightCard = new Rectangle(72.5, 610 + 25, 60, 84);
        knightCard.setFill(new ImagePattern(new Image(this.getClass().getResource("knightcard.png").toExternalForm())));
        Tooltip.install(knightCard, clickToUse);
        knightCard.setOnMouseClicked(e -> {
            developmentCards.setVisible(false);
            game.useKnightCard();
        });

        //---------------------VP CARD---------------------

        Rectangle VPCard = new Rectangle(72.5 + 70, 610 + 25, 60, 84);
        VPCard.setFill(new ImagePattern(new Image(this.getClass().getResource("vpcard.png").toExternalForm())));

        //---------------------ROAD BUILDING---------------------

        Rectangle roadBuildingCard = new Rectangle(72.5 + (70 * 2), 610 + 25, 60, 84);
        roadBuildingCard.setFill(new ImagePattern(new Image(this.getClass().getResource("roadbuildingcard.png").toExternalForm())));
        Tooltip.install(roadBuildingCard, clickToUse);
        roadBuildingCard.setOnMouseClicked(e -> {
            developmentCards.setVisible(false);
            game.useRoadBuildingCard();
        });

        //---------------------YEAR OF PLENTY---------------------

        Rectangle yearOfPlentyPopUp = new Rectangle(0,0,1440,900);
        yearOfPlentyPopUp.setFill(new ImagePattern(new Image(this.getClass().getResource("yearofplentypopup.png").toExternalForm())));

        takeCardsButton = new Rectangle(636.475,494+5,167.05,32.5);
        takeCardsButton.setFill(new ImagePattern(new Image(this.getClass().getResource("takecards.png").toExternalForm())));

        yearOfPlenty = new Group(yearOfPlentyPopUp, takeCardsButton);
        yearOfPlentyResCards = new Rectangle[5];
        yearOfPlentyValueTexts = new Text[5];
        yearOfPlentyValues = new Integer[5];
        yearOfPlentyCount = 0;
        int OFF = 25 / 2;
        for (int y = 0; y < 5; y++) {
            int i = y;
            yearOfPlentyValues[y] = 0;
            Rectangle yearOfPlentyResCard = new Rectangle(495 + (225 / 2) + (46.25 * y), 340 + (95 / 2) - OFF +25+10+12.5, 40, 57.5);
            yearOfPlentyResCard.setFill(new ImagePattern(new Image(this.getClass().getResource(ResourceType.values()[y].label + ".png").toExternalForm())));
            yearOfPlentyResCards[y] = yearOfPlentyResCard;
            Text yearOfPlentyValueText = (new Text(495 + (225 / 2) + 20 - 12.5 + 6.5 + (46.25 * y), 340 + (95 / 2) + 28.75 - 12.5 + 20 - OFF +25+10+12.5, "0")); //FIXME
            yearOfPlentyValueText.setFont(new Font(20));
            yearOfPlentyValueText.setFill(Color.WHITE);
            yearOfPlentyValueTexts[y] = yearOfPlentyValueText;
            yearOfPlenty.getChildren().addAll(yearOfPlentyResCard, yearOfPlentyValueText);
            yearOfPlentyResCard.setOnMouseClicked(event ->
            {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (yearOfPlentyCount < 2) { //If value not > 2
                        yearOfPlentyValues[i]++;
                        yearOfPlentyCount++;
                    }

                } else if (event.getButton() == MouseButton.SECONDARY) {
                    if (yearOfPlentyValues[i] > 0) {
                        yearOfPlentyValues[i]--;
                        yearOfPlentyCount--;
                    }
                }
                yearOfPlentyValueTexts[i].setText(String.valueOf(yearOfPlentyValues[i]));
            });
        }
        yearOfPlenty.setVisible(false);
        GUI.getChildren().add(yearOfPlenty);

        Rectangle yearOfPlentyCard = new Rectangle(72.5+(70 * 3), 610+25, 60, 84);
        yearOfPlentyCard.setFill(new ImagePattern(new Image(this.getClass().getResource("yearofplentycard.png").toExternalForm())));
        Tooltip.install(yearOfPlentyCard,clickToUse);
        yearOfPlentyCard.setOnMouseClicked(e -> {
            if(game.getCurrentPlayer().useDevCard(DevelopmentCardType.YEAROFPLENTY)){
                developmentCards.setVisible(false);
                yearOfPlenty.setVisible(true);
                yearOfPlenty.toFront();
                takeCardsButton.setOnMouseClicked(ee ->{
                    game.useYearOfPlentyCard(yearOfPlentyValues);
                    yearOfPlentyCount = 0;
                    Arrays.fill(yearOfPlentyValues, 0);
                    yearOfPlenty.setVisible(false);
                });
            }
        });

        //---------------------MONOPOLY---------------------

        Rectangle monopolyPopUp = new Rectangle(0,0,1440,900);
        monopolyPopUp.setFill(new ImagePattern(new Image(this.getClass().getResource("monopolypopup.png").toExternalForm())));

        confirmButton = new Rectangle(636.475,494+5,167.05,32.5);
        confirmButton.setFill(new ImagePattern(new Image(this.getClass().getResource("confirm.png").toExternalForm())));

        monopoly = new Group(monopolyPopUp, confirmButton);
        monopolyResCards = new Rectangle[5];
        monopolyResType = null;
        for (int y = 0; y < 5; y++) {
            int i = y;
            Rectangle monopolyResCard = new Rectangle(495 + (225 / 2) + (46.25 * y), 340 + (95 / 2) - OFF +25+10+12.5, 40, 57.5);
            monopolyResCard.setFill(new ImagePattern(new Image(this.getClass().getResource(ResourceType.values()[y].label + ".png").toExternalForm())));
            monopolyResCards[y] = monopolyResCard;
            monopoly.getChildren().addAll(monopolyResCard);
            monopolyResCard.setOnMouseClicked(event ->
            {
                for (Rectangle card : monopolyResCards) {
                    card.setStroke(Color.TRANSPARENT);
                }
                monopolyResCard.setStroke(Color.WHITE);
                monopolyResCard.setStrokeWidth(2);
                monopolyResType = ResourceType.getByIndex(i);
            });
        }
        monopoly.setVisible(false);
        GUI.getChildren().add(monopoly);

        Rectangle monopolyCard = new Rectangle(72.5+(70 * 4), 610+25, 60, 84);
        monopolyCard.setFill(new ImagePattern(new Image(this.getClass().getResource("monopolycard.png").toExternalForm())));
        Tooltip.install(monopolyCard,clickToUse);
        monopolyCard.setOnMouseClicked(e -> {
            if(game.getCurrentPlayer().useDevCard(DevelopmentCardType.MONOPOLY)){
                developmentCards.setVisible(false);
                monopoly.setVisible(true);
                monopoly.toFront();
                confirmButton.setOnMouseClicked(ee ->{
                    game.useMonopolyCard(monopolyResType);
                    monopolyResType = null;
                    for (Rectangle card : monopolyResCards) {
                        card.setStroke(Color.TRANSPARENT);
                    }
                    monopoly.setVisible(false);

                });
            }
        });

        developmentCards.getChildren().addAll(knightCard,VPCard,roadBuildingCard,yearOfPlentyCard,monopolyCard);

        //---------------------RESOURCE AND DEVELOPMENT CARDS LABELS---------------------

        currentResourceValues = new Text[5]; //stores text boxes for resource values in here
        currentDevCardValues = new Text[5];
        for (int y = 0; y < 5; y++) {
            Rectangle devlabel = new Rectangle(90 + (70 * y), 705, 25, 25);
            Rectangle resCard = new Rectangle(72.5 + (70 * y), 790, 60, 84);
            Rectangle reslabel = new Rectangle(90 + (70 * y), 860, 25, 25);
            devlabel.setFill(new ImagePattern(new Image(this.getClass().getResource("devcardlabel.png").toExternalForm())));
            resCard.setFill(new ImagePattern(new Image(this.getClass().getResource(ResourceType.values()[y].label + ".png").toExternalForm())));
            reslabel.setFill(new ImagePattern(new Image(this.getClass().getResource(ResourceType.values()[y].label + "label.png").toExternalForm())));
            Text resText = new Text(90 + 6.25 + (70 * y), 860 + 20, String.valueOf(game.getCurrentPlayer().resourceCards.get(ResourceType.values()[y])));
            Text devText = new Text(90 + 6.25 + (70 * y), 705 + 20, "0");
            devText.setFont(new Font(20));
            resText.setFont(new Font(20));
            developmentCards.getChildren().addAll(devlabel, devText);
            GUI.getChildren().addAll(resCard, reslabel, resText);
            currentResourceValues[y] = resText; // adding text to array
            currentDevCardValues[y] = devText;
        }

        //---------------------BUY BUTTONS---------------------

        buyRoadButton = new Rectangle(532.5 , 790, 60, 39.5);
        buyRoadButton.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"buyroad.png").toExternalForm())));
        Tooltip roadCost = new Tooltip();
        ImageView roadCostImg = new ImageView(new Image(this.getClass().getResource("roadcost.png").toExternalForm()));
        roadCostImg.setPreserveRatio(true);
        roadCostImg.setFitHeight(50);
        roadCost.setGraphic(roadCostImg);
        Tooltip.install(buyRoadButton,roadCost);
        buyRoadButton.setOnMouseClicked(e -> {
            if (game.gameState == GameState.MAIN) {
                if(roadPane.isVisible()){
                    roadPane.setVisible(false);
                }
                else if (!diceCanBeRolled) {
                    if (!game.getCurrentPlayer().checkTooManyRoads()) {
                        if (game.getCurrentPlayer().getResourceCards().get(ResourceType.BRICK) > 0 && game.getCurrentPlayer().getResourceCards().get(ResourceType.LUMBER) > 0) {
                            //make only places where you can place roads available
                            showRoads();
                        } else {
                            showError(notEnoughResourcesError);
                        }
                    } else {
                        showError(tooManyRoadsError);
                    }
                } else {
                    showError(rollDiceFirstError);
                    System.out.println("dice must be rolled first");
                }
            }
        });

        buySettlementButton = new Rectangle(602.5, 790, 60, 39.5);
        buySettlementButton.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"buysettlement.png").toExternalForm())));
        Tooltip settlementCost = new Tooltip();
        ImageView settlementCostImg = new ImageView(new Image(this.getClass().getResource("settlementcost.png").toExternalForm()));
        settlementCostImg.setPreserveRatio(true);
        settlementCostImg.setFitHeight(50);
        settlementCost.setGraphic(settlementCostImg);
        Tooltip.install(buySettlementButton,settlementCost);
        buySettlementButton.setOnMouseClicked(e -> {
            if (game.gameState == GameState.MAIN) {
                if (!diceCanBeRolled) {
                    if (!game.getCurrentPlayer().checkTooManySettlements()) {
                        if (game.getCurrentPlayer().getResourceCards().get(ResourceType.BRICK) > 0 && game.getCurrentPlayer().getResourceCards().get(ResourceType.GRAIN) > 0 && game.getCurrentPlayer().getResourceCards().get(ResourceType.LUMBER) > 0 && game.getCurrentPlayer().getResourceCards().get(ResourceType.WOOL) > 0) {
                            roadPane.setVisible(false);
                            for (Settlement settlement : gameBoard.getSettlementList()) {
                                if (settlement.checkRoadConnection(game.getCurrentPlayer())) {
                                    for (Road road : settlement.getRoads()) {
                                        if (road.getNextSettlement(settlement).getOwner() != null) {
                                            settlement.getIcon().setVisible(false);
                                            break;
                                        } else {
                                            settlement.getIcon().setVisible(true);
                                        }
                                    }
                                } else {
                                    settlement.getIcon().setVisible(false);
                                }
                                if (settlement.getOwner() != null) {
                                    settlement.getIcon().setVisible(true);
                                }
                            }
                            settlementPane.setVisible(!settlementPane.isVisible());
                        } else {
                            showError(notEnoughResourcesError);
                        }
                    } else {
                        showError(tooManySettlementsError);
                    }
                } else {
                    showError(rollDiceFirstError);
                    System.out.println("dice must be rolled first");
                }
            }
        });

        buyCityButton = new Rectangle(672.5, 790, 60, 39.5);
        buyCityButton.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"buycity.png").toExternalForm())));
        Tooltip cityCost = new Tooltip();
        ImageView cityCostImg = new ImageView(new Image(this.getClass().getResource("citycost.png").toExternalForm()));
        cityCostImg.setPreserveRatio(true);
        cityCostImg.setFitHeight(66);
        cityCost.setGraphic(cityCostImg);
        Tooltip.install(buyCityButton,cityCost);
        buyCityButton.setOnMouseClicked(e -> {
            if (game.gameState == GameState.MAIN) {
                if (!diceCanBeRolled) {
                    if (!game.getCurrentPlayer().checkTooManyCities()) {
                        if (game.getCurrentPlayer().getResourceCards().get(ResourceType.ORE) > 2 && game.getCurrentPlayer().getResourceCards().get(ResourceType.GRAIN) > 1) {
                            roadPane.setVisible(false);
                        } else {
                            showError(notEnoughResourcesError);
                        }
                    } else {
                        showError(tooManyCitiesError);
                    }
                } else {
                    showError(rollDiceFirstError);
                    System.out.println("dice must be rolled first");
                }
            }
        });

        Rectangle buyDevCardButton = new Rectangle(532.5, 839.5, 60, 39.5);
        buyDevCardButton.setFill(new ImagePattern(new Image(this.getClass().getResource("buydev.png").toExternalForm())));
        Tooltip devCardCost = new Tooltip();
        ImageView devCardCostImg = new ImageView(new Image(this.getClass().getResource("devcardcost.png").toExternalForm()));
        devCardCostImg.setPreserveRatio(true);
        devCardCostImg.setFitHeight(50);
        devCardCost.setGraphic(devCardCostImg);
        Tooltip.install(buyDevCardButton,devCardCost);
        buyDevCardButton.setOnMouseClicked(e -> {
            if (game.gameState == GameState.MAIN) {
                if (!diceCanBeRolled) {
                    if (game.getCurrentPlayer().getResourceCards().get(ResourceType.ORE) > 0 && game.getCurrentPlayer().getResourceCards().get(ResourceType.WOOL) > 0 && game.getCurrentPlayer().getResourceCards().get(ResourceType.GRAIN) > 0) {
                        try {
                            game.getCurrentPlayer().buyDevCard();
                            refreshUI();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        developmentCards.setVisible(true);
                    } else {
                        showError(notEnoughResourcesError);
                    }
                } else {
                    showError(rollDiceFirstError);
                    System.out.println("dice must be rolled first");
                }
            }
        });

        Rectangle endTurnButton = new Rectangle(602.5, 839.5, 130, 39.5);
        endTurnButton.setFill(new ImagePattern(new Image(this.getClass().getResource("endturn.png").toExternalForm())));
        endTurnButton.setOnMouseClicked(e -> {
            if (game.gameState == GameState.MAIN) {
                if (!diceCanBeRolled) {
                    settlementPane.setVisible(false);
                    roadPane.setVisible(false);
                    developmentCards.setVisible(false);
                    game.nextPlayer();
                    endTurnMenu();
                } else {
                    showError(rollDiceFirstError);
                    System.out.println("dice must be rolled first");
                }
            }
        });

        //---------------------DICE---------------------

        Rectangle diceUI = new Rectangle(774, 765, 140, 140);
        diceUI.setFill(new ImagePattern(new Image(this.getClass().getResource("box2.png").toExternalForm())));
        GUI.getChildren().add(diceUI);

        dice1 = new Rectangle(790, 790, 50, 50);
        dice1.setFill(new ImagePattern(new Image(this.getClass().getResource("d6.png").toExternalForm())));
        GUI.getChildren().add(dice1);

        dice2 = new Rectangle(850, 790, 50, 50);
        dice2.setFill(new ImagePattern(new Image(this.getClass().getResource("d2.png").toExternalForm())));
        GUI.getChildren().add(dice2);

        Rectangle rollDiceButton = new Rectangle(800, 844.5 + 5, 90, 39.5);
        rollDiceButton.setFill(new ImagePattern(new Image(this.getClass().getResource("roll.png").toExternalForm())));
        rollDiceButton.setOnMouseClicked(e -> {
            if (game.gameState == GameState.MAIN) {
                if (diceCanBeRolled) {
                    diceCanBeRolled = false;
                    diceRollAnimation();
                } else {
                    System.out.println("dice can't be rolled");
                }
            }
        });

        //---------------------OTHER PLAYER UI---------------------

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

        //---------------------TRADING---------------------

        Rectangle tradeUI = new Rectangle(960, 25, 450, 220);
        tradeUI.setFill(new ImagePattern(new Image(this.getClass().getResource("tbox.png").toExternalForm())));
        GUI.getChildren().add(tradeUI);

        CPTradeIcon = new Rectangle(960+(225/2)-85,76.25,50,50);
        CPTradeIcon.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"player.png").toExternalForm())));
        CPTradeIcon.toFront();
        GUI.getChildren().add(CPTradeIcon);

        CPtradeSelectionValues = new Integer[5];
        CPtradeSelectionTexts = new Text[5];
        popUpCPtradeSelectionTexts = new Text[5];
        tradeSelectionValues = new Integer[5];
        tradeSelectionTexts = new Text[5];
        popUpTradeSelectionTexts = new Text[5];
        downArrows = new Rectangle[5];
        popUpDownArrows = new Rectangle[5];
        upArrows = new Rectangle[5];
        popUpUpArrows = new Rectangle[5];
        popUpCPtradeResCards = new Rectangle[5];
        popUPtradeResCards = new Rectangle[5];
        popUpTradeResCardLabels = new Rectangle[5];
        popUpTradeResCardCounts = new Text[5];
        for (int y = 0; y < 5; y++) {
            int i = y;
            CPtradeSelectionValues[y] = 0;
            Rectangle CPtradeResCard = new Rectangle(960+(225/2)+(46.25*y),72.5,40,57.5);
            CPtradeResCard.setFill(new ImagePattern(new Image(this.getClass().getResource(ResourceType.values()[y].label + ".png").toExternalForm())));
            Rectangle downArrow = new Rectangle(960+(225/2)+(46.25*y)+20-9.5 ,25+(95/2)+60,19,9);
            downArrow.setFill(new ImagePattern(new Image(this.getClass().getResource("arrowdown.png").toExternalForm())));
            downArrows[y] = downArrow;
            Text CPtradeResText = (new Text(960+(225/2)+20-12.5+6.5+(46.25*y), 25+(95/2)+28.75-12.5+20,"0"));
            CPtradeResText.setFont(new Font(20));
            CPtradeResText.setFill(Color.WHITE);
            CPtradeSelectionTexts[y] = CPtradeResText;
            CPtradeResCard.setOnMouseClicked(event ->
            {
                if (game.gameState == GameState.MAIN) {
                    if (diceCanBeRolled == false) {
                        if (event.getButton() == MouseButton.PRIMARY)
                        {
                            if ((CPtradeSelectionValues[i] < game.getCurrentPlayer().resourceCards.get(ResourceType.values()[i])) && tradeSelectionValues[i] == 0) {
                                CPtradeSelectionValues[i]++;
                                downArrows[i].setVisible(true);
                            }
                        } else if (event.getButton() == MouseButton.SECONDARY)
                        {
                            if (CPtradeSelectionValues[i] > 0) {
                                CPtradeSelectionValues[i]--;
                                if (CPtradeSelectionValues[i] == 0){
                                    downArrows[i].setVisible(false);
                                }
                            }
                        }
                        CPtradeSelectionTexts[i].setText(String.valueOf(CPtradeSelectionValues[i]));
                    } else {
                        showError(rollDiceFirstError);
                        System.out.println("dice must be rolled first");
                    }
                }
            });
            Rectangle popUpCPtradeResCard = new Rectangle(495+(225/2)+(46.25*y),340+(95/2)-OFF,40,57.5);
            popUpCPtradeResCard.setFill(new ImagePattern(new Image(this.getClass().getResource(ResourceType.values()[y].label + ".png").toExternalForm())));
            popUpCPtradeResCards[y] = popUpCPtradeResCard;

            Rectangle popUpDownArrow = new Rectangle(495+(225/2)+(46.25*y)+20-9.5 ,340+(95/2)+60-OFF,19,9); //FIXME
            popUpDownArrow.setFill(new ImagePattern(new Image(this.getClass().getResource("arrowdown.png").toExternalForm())));
            popUpDownArrows[y] = popUpDownArrow;

            Text popUpCPtradeResText = (new Text(495+(225/2)+20-12.5+6.5+(46.25*y), 340+(95/2)+28.75-12.5+20-OFF,"0")); //FIXME
            popUpCPtradeResText.setFont(new Font(20));
            popUpCPtradeResText.setFill(Color.WHITE);
            popUpCPtradeSelectionTexts[y] = popUpCPtradeResText;

            tradeSelectionValues[y] = 0;
            Rectangle tradeResCard = new Rectangle(960+(225/2)+(46.25*y),167.5,40,57.5);
            tradeResCard.setFill(new ImagePattern(new Image(this.getClass().getResource(ResourceType.values()[y].label + ".png").toExternalForm())));
            Rectangle upArrow = new Rectangle(960+(225/2)+(46.25*y)+20-9.5,156,19,9);
            upArrow.setFill(new ImagePattern(new Image(this.getClass().getResource("arrowup.png").toExternalForm())));
            upArrows[y] = upArrow;
            Text tradeResText = (new Text(960+(225/2)+20-12.5+6.5+(46.25*y), 197.5-40+28.75-12.5+20+10,"0"));
            tradeResText.setFont(new Font(20));
            tradeResText.setFill(Color.WHITE);
            tradeSelectionTexts[y] = tradeResText;
            tradeResCard.setOnMouseClicked(event ->
            {
                if (game.gameState == GameState.MAIN) {
                    if (diceCanBeRolled == false) {
                        if (event.getButton() == MouseButton.PRIMARY)
                        {
                            if (CPtradeSelectionValues[i] == 0) {
                                tradeSelectionValues[i]++;
                                upArrows[i].setVisible(true);
                            }
                        } else if (event.getButton() == MouseButton.SECONDARY)
                        {
                            if (tradeSelectionValues[i] > 0) {
                                tradeSelectionValues[i]--;
                                if (tradeSelectionValues[i] == 0){
                                    upArrows[i].setVisible(false);
                                }
                            }
                        }
                        tradeSelectionTexts[i].setText(String.valueOf(tradeSelectionValues[i]));
                    } else {
                        showError(rollDiceFirstError);
                        System.out.println("dice must be rolled first");
                    }
                }
            });
            Rectangle popUpTradeResCard = new Rectangle(607.5+(46.25*y),482.5-OFF,40,57.5);
            popUpTradeResCard.setFill(new ImagePattern(new Image(this.getClass().getResource(ResourceType.values()[y].label + ".png").toExternalForm())));
            popUPtradeResCards[y] = popUpTradeResCard;

            Rectangle popUpTradeResCardLabel = new Rectangle(607.5+(11.67)+(46.25*y),482.5+(46.67)-OFF,16.67,16.67);
            popUpTradeResCardLabel.setFill(new ImagePattern(new Image(this.getClass().getResource(ResourceType.values()[y].label + "label.png").toExternalForm())));
            popUpTradeResCardLabels[y] = popUpTradeResCardLabel;

            Text popUpTradeResCardCount = (new Text(607.5+(11.67)+(46.25*y)+2.08, 482.5+(46.67)-OFF+13.33,"0"));
            popUpTradeResCardCount.setFont(new Font(13.33));
            popUpTradeResCardCounts[y] = popUpTradeResCardCount;

            Rectangle popUpUpArrow = new Rectangle(495+(225/2)+(46.25*y)+20-9.5,340+(95/2)+95-11.5-OFF,19,9);
            popUpUpArrow.setFill(new ImagePattern(new Image(this.getClass().getResource("arrowup.png").toExternalForm())));
            popUpUpArrows[y] = popUpUpArrow;

            Text popUpTradeResText = (new Text(495+(225/2)+20-12.5+6.5+(46.25*y), 340+(95/2)+28.75-12.5+20+95-OFF,"0"));
            popUpTradeResText.setFont(new Font(20));
            popUpTradeResText.setFill(Color.WHITE);
            popUpTradeSelectionTexts[y] = popUpTradeResText;

            upArrow.setVisible(false);
            downArrow.setVisible(false);

            popUpCPtradeResCard.setVisible(false);
            popUpTradeResCardCount.setVisible(false);
            popUpCPtradeResText.setVisible(false);
            popUpTradeResCard.setVisible(false);
            popUpTradeResCardLabel.setVisible(false);
            popUpTradeResText.setVisible(false);
            popUpDownArrow.setVisible(false);
            popUpUpArrow.setVisible(false);

            GUI.getChildren().addAll(CPtradeResCard,CPtradeResText,tradeResCard,tradeResText,downArrow,upArrow);
            GUI.getChildren().addAll(popUpCPtradeResCard,popUpCPtradeResText,popUpTradeResCard,popUpTradeResText,popUpDownArrow,popUpUpArrow,popUpTradeResCardLabel,popUpTradeResCardCount);
        }

        Rectangle playerTradeButton = new Rectangle(960+(225/2)+(46.25*4)+40+16.75+12.5,72.5+57.5-17.5,51.2,17.5);
        playerTradeButton.setFill(new ImagePattern(new Image(this.getClass().getResource("trade.png").toExternalForm())));
        playerTradeButton.setOnMouseClicked(event ->
        {
            if (game.gameState == GameState.MAIN) {
                if (!diceCanBeRolled) {
                    if (containsAllZeros(CPtradeSelectionValues) && containsAllZeros(tradeSelectionValues))
                    {
                        tradeCount = 0;
                        playerTrade();
                    } else {
                        showError(unfairTradeError);
                    }
                } else {
                    showError(rollDiceFirstError);
                    System.out.println("dice must be rolled first");
                }
            }
        });

        GUI.getChildren().addAll(playerTradeButton);

        Rectangle bankTradeButton = new Rectangle(960+(225/2)+(46.25*4)+40+16.75+12.5,167.5+57.5-17.5,51.2,17.5);
        bankTradeButton.setFill(new ImagePattern(new Image(this.getClass().getResource("trade.png").toExternalForm())));
        bankTradeButton.setOnMouseClicked(event ->
        {
            if (game.gameState == GameState.MAIN) {
                if (!diceCanBeRolled) {
                    if(game.bankTrade(CPtradeSelectionValues, tradeSelectionValues)){
                        for (int y = 0; y < 5; y++) {
                            CPtradeSelectionValues[y] = 0;
                            CPtradeSelectionTexts[y].setText("0");
                            tradeSelectionValues[y] = 0;
                            tradeSelectionTexts[y].setText("0");
                            downArrows[y].setVisible(false);
                            upArrows[y].setVisible(false);
                        }
                        refreshUI();
                    }
                } else {
                    showError(rollDiceFirstError);
                    System.out.println("dice must be rolled first");
                }
            }
        });
        GUI.getChildren().add(bankTradeButton);

        //---------------------TRADE POP-UP---------------------

        selectTradePopUp = new Rectangle(0,0,1440,900);
        selectTradePopUp.setFill(new ImagePattern(new Image(this.getClass().getResource("selecttradepopup.png").toExternalForm())));
        selectTradePopUp.setVisible(false);
        tradePopUp = new Rectangle(0,0,1440,900);
        tradePopUp.setFill(new ImagePattern(new Image(this.getClass().getResource("tradepopup.png").toExternalForm())));
        tradePopUp.setVisible(false);
        popUpCPtradeIcon = new Rectangle(1045/2,391.25-(25/2),50,50);
        popUpCPtradeIcon.setVisible(false);
        popUpPlayerTradeIcon = new Rectangle(1045/2,486.25-(25/2),50,50);
        popUpPlayerTradeIcon.setVisible(false);
        GUI.getChildren().addAll(tradePopUp,popUpCPtradeIcon, popUpPlayerTradeIcon,selectTradePopUp);

        acceptTrade = new Rectangle(792.5+42.5+(25/2),450-13.5-25,79,27);
        acceptTrade.setFill(new ImagePattern(new Image(this.getClass().getResource("accept.png").toExternalForm())));
        acceptTrade.setVisible(false);
        declineTrade = new Rectangle(792.5+42.5+(25/2),450-13.5+25,79,27);
        declineTrade.setFill(new ImagePattern(new Image(this.getClass().getResource("decline.png").toExternalForm())));
        declineTrade.setVisible(false);
        GUI.getChildren().addAll(acceptTrade,declineTrade);

        //---------------------THROW AWAY CARDS---------------------

        Rectangle throwAwayPopUp = new Rectangle(0,0,1440,900);
        throwAwayPopUp.setFill(new ImagePattern(new Image(this.getClass().getResource("throwawaypopup.png").toExternalForm())));

        discardButton = new Rectangle(636.475,494+5,167.05,32.5);
        discardButton.setFill(new ImagePattern(new Image(this.getClass().getResource("confirm.png").toExternalForm())));

        discardCount = (new Text(697.5, 370, "0"));
        discardCount.setFont(new Font(25));
        discardCount.setFill(Color.WHITE);

        throwAway = new Group(throwAwayPopUp,discardButton,discardCount);
        throwAwayResCards = new Rectangle[5];
        throwAwayValueTexts = new Text[5];
        throwAwayValues = new Integer[5];
        throwAwayCount = 0;

        for (int y = 0; y < 5; y++) {
            int i = y;
            throwAwayValues[y] = 0;
            Rectangle throwAwayResCard = new Rectangle(495 + (225 / 2) + (46.25 * y), 340 + (95 / 2) - OFF +25+10+12.5, 40, 57.5);
            throwAwayResCard.setFill(new ImagePattern(new Image(this.getClass().getResource(ResourceType.values()[y].label + ".png").toExternalForm())));
            throwAwayResCards[y] = throwAwayResCard;
            Text throwAwayValueText = (new Text(495 + (225 / 2) + 20 - 12.5 + 6.5 + (46.25 * y), 340 + (95 / 2) + 28.75 - 12.5 + 20 - OFF +25+10+12.5, "0"));
            throwAwayValueText.setFont(new Font(20));
            throwAwayValueText.setFill(Color.WHITE);
            throwAwayValueTexts[y] = throwAwayValueText;
            throwAway.getChildren().addAll(throwAwayResCard, throwAwayValueText);
            throwAwayResCard.setOnMouseClicked(event ->
            {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if ((throwAwayValues[i] < game.getCurrentPlayer().resourceCards.get(ResourceType.values()[i])) && throwAwayCount < (int) Math.floor(game.getCurrentPlayer().getResourceCount() / 2.0)) {
                        throwAwayValues[i]++;
                        throwAwayCount++;
                    }

                } else if (event.getButton() == MouseButton.SECONDARY) {
                    if (throwAwayValues[i] > 0) {
                        throwAwayValues[i]--;
                        throwAwayCount--;
                    }
                }
                throwAwayValueTexts[i].setText(String.valueOf(throwAwayValues[i]));
            });
        }
        throwAway.setVisible(false);
        throwAway.toFront();
        GUI.getChildren().add(throwAway);

        //---------------------END TURN POP-UP---------------------

        endTurnPopUp = new Rectangle(0,0,1440,900);
        clickToContinueButton = new Rectangle(521.5,485,397,50.5);
        clickToContinueButton.setFill(new ImagePattern(new Image(this.getClass().getResource("clicktocontinue.png").toExternalForm())));
        endTurn = new Group(endTurnPopUp,clickToContinueButton);
        endTurn.setVisible(false);

        //---------------------WIN POP-UP---------------------

        winMessage = new Rectangle(0,0,1440,900);
        winMessage.setVisible(false);
        GUI.getChildren().add(winMessage);

        //---------------------ERRORS---------------------

        notEnoughResourcesError = new Rectangle(644,712.5,270,42.5);
        notEnoughResourcesError.setFill(new ImagePattern(new Image(this.getClass().getResource("notenoughresources.png").toExternalForm())));

        cantPlaceRoadError = new Rectangle(644,712.5,270,42.5);
        cantPlaceRoadError.setFill(new ImagePattern(new Image(this.getClass().getResource("cantplaceroad.png").toExternalForm())));

        cantPlaceSettlementError = new Rectangle(581.5,712.5,332.5,42.5);
        cantPlaceSettlementError.setFill(new ImagePattern(new Image(this.getClass().getResource("cantplacesettlement.png").toExternalForm())));

        rollDiceFirstError = new Rectangle(729,712.5,185,42.5);
        rollDiceFirstError.setFill(new ImagePattern(new Image(this.getClass().getResource("rolldicefirst.png").toExternalForm())));

        tooManyRoadsError = new Rectangle(712.5,712.5,201.5,42.5);
        tooManyRoadsError.setFill(new ImagePattern(new Image(this.getClass().getResource("toomanyroads.png").toExternalForm())));

        tooManySettlementsError = new Rectangle(652,712.5,262,42.5);
        tooManySettlementsError.setFill(new ImagePattern(new Image(this.getClass().getResource("toomanysettlements.png").toExternalForm())));

        unfairTradeError = new Rectangle(743.5,712.5,170.5,42.5);
        unfairTradeError.setFill(new ImagePattern(new Image(this.getClass().getResource("unfairtrade.png").toExternalForm())));

        tooManyCitiesError = new Rectangle(712.5,712.5,201.5,42.5);
        tooManyCitiesError.setFill(new ImagePattern(new Image(this.getClass().getResource("toomanycities.png").toExternalForm())));

        Group errors = new Group(notEnoughResourcesError,cantPlaceRoadError,cantPlaceSettlementError,rollDiceFirstError,tooManyRoadsError,tooManySettlementsError,unfairTradeError,tooManyCitiesError);
        for (Node node : errors.getChildren()) {
            if (node instanceof Rectangle) {
                node.setVisible(false);
            }
        }

        GUI.getChildren().addAll(CPIcon, CPIconLabel,CPResCards,CPResCardsLabel, CPDevCards, CPDevCardsLabel, CPResCardsCount, CPLongestRoad, CPLargestArmy, CPDevCardsCount,CPLongestRoadValue,CPLargestArmyValue,CPVPCount);
        GUI.getChildren().addAll(buyRoadButton, buySettlementButton, buyCityButton, buyDevCardButton, endTurnButton, rollDiceButton);
        background.toBack();



        GUI.getChildren().addAll(errors,endTurn);
        throwAway.toFront();
    }

    public void showRoads(){
        settlementPane.setVisible(false);
        for (Road road : gameBoard.getRoadList()) {
            road.getIcon().setVisible(road.getSettlementA().getOwner() == game.getCurrentPlayer() || road.getSettlementB().getOwner() == game.getCurrentPlayer() || road.getSettlementA().checkRoadConnection(game.getCurrentPlayer()) || road.getSettlementB().checkRoadConnection(game.getCurrentPlayer()));
            if (road.getOwner() != null) {
                road.getIcon().setVisible(true);
            }
        }
        roadPane.setVisible(true);
    }

    public void refreshUI() {
        int currentPlayerResNumber = 0;
        for (int y = 0; y < 5; y++) {
            currentResourceValues[y].setText(String.valueOf(game.getCurrentPlayer().resourceCards.get(ResourceType.values()[y])));
            currentPlayerResNumber += game.getCurrentPlayer().resourceCards.get(ResourceType.values()[y]);
            currentDevCardValues[y].setText(String.valueOf(game.getCurrentPlayer().getDevelopmentCardCount()[y]));
        }
        CPResCardsCount.setText(String.valueOf(currentPlayerResNumber));
        CPDevCardsCount.setText(String.valueOf(game.getCurrentPlayer().getDevelopmentCards().size()));
        CPIcon.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID() - 1] + "player.png").toExternalForm())));
        CPIconLabel.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID() - 1] + "playerlabel.png").toExternalForm())));
        CPVPCount.setText(String.valueOf(game.getCurrentPlayer().getVictoryPoints()));
        CPLargestArmyValue.setText(String.valueOf(game.getCurrentPlayer().getArmySize()));
        CPLongestRoadValue.setText(String.valueOf(game.getCurrentPlayer().getLongestRoadLength()));
        if (game.getCurrentPlayer().getHasLongestRoad()){
            CPLongestRoadValue.setFill(Color.web("ab0000"));
        }
        else{
            CPLongestRoadValue.setFill(Color.BLACK);
        }
        if (game.getCurrentPlayer().getHasLargestArmy()){
            CPLargestArmyValue.setFill(Color.web("ab0000"));
        }
        else{
            CPLargestArmyValue.setFill(Color.BLACK);
        }

        buyRoadButton.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"buyroad.png").toExternalForm())));
        buySettlementButton.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"buysettlement.png").toExternalForm())));
        buyCityButton.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"buycity.png").toExternalForm())));
        players = game.getPlayers();
        nonActivePlayers = new ArrayList<>(players);
        nonActivePlayers.remove(game.getCurrentPlayer());
        CPTradeIcon.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID() - 1] + "player.png").toExternalForm())));
        for (int y = 0; y < players.size() - 1; y++) {
            playerIcons.get(y).setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[(nonActivePlayers.get(y)).getPlayerID() - 1] + "player.png").toExternalForm())));
            playerIconLabels.get(y).setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[(nonActivePlayers.get(y)).getPlayerID() - 1] + "playerlabel.png").toExternalForm())));
            int altResNumber = 0;
            for (int z=0; z < 5;z++){
                altResNumber += nonActivePlayers.get(y).resourceCards.get(ResourceType.values()[z]);
            }
            resCardsCount[y].setText(String.valueOf(altResNumber));
            devCardsCount[y].setText(String.valueOf(nonActivePlayers.get(y).getDevelopmentCards().size()));
            int VP = nonActivePlayers.get(y).getVictoryPoints();
            for (DevelopmentCard card : nonActivePlayers.get(y).getDevelopmentCards()) {
                if (card.getCardType() == DevelopmentCardType.VP) {
                    VP = VP-1;
                }
            }
            VPCount[y].setText(String.valueOf(VP));
            playerLongestRoadValue[y].setText(String.valueOf(nonActivePlayers.get(y).getLongestRoadLength()));
            if (nonActivePlayers.get(y).getHasLongestRoad()){
                playerLongestRoadValue[y].setFill(Color.web("ab0000"));
            }
            else{
                playerLongestRoadValue[y].setFill(Color.BLACK);
            }
            if (nonActivePlayers.get(y).getHasLargestArmy()){
                playerLargestArmyValue[y].setFill(Color.web("ab0000"));
            }
            else{
                playerLargestArmyValue[y].setFill(Color.BLACK);
            }
            playerLargestArmyValue[y].setText(String.valueOf(nonActivePlayers.get(y).getArmySize()));
        }
    }

    public Pane getGUI() {
        return GUI;
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

    public void showError(Node errorNode) {
        errorNode.setVisible(true);
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(3500));
        fade.setFromValue(10);
        fade.setToValue(0);
        fade.setNode(errorNode);
        fade.play();
    }

    public void cantPlaceSettlementError(){
        showError(cantPlaceSettlementError);
    }

    public void cantPlaceRoadError(){
        showError(cantPlaceRoadError);
    }

    public void endTurnMenu(){
        for (int y = 0; y < 5; y++) {
            CPtradeSelectionValues[y] = 0;
            CPtradeSelectionTexts[y].setText("0");
            tradeSelectionValues[y] = 0;
            tradeSelectionTexts[y].setText("0");
            downArrows[y].setVisible(false);
            upArrows[y].setVisible(false);
        }
        endTurn.setVisible(true);
        endTurn.toFront();
        endTurnPopUp.setFill(new ImagePattern(new Image(this.getClass().getResource("p"+(game.getCurrentPlayer().getPlayerID())+"endturn.png").toExternalForm())));
        clickToContinueButton.setOnMouseClicked(ee -> {
            endTurn.setVisible(false);
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
    public static boolean containsAllZeros(Integer[] array) {
        boolean allZeros = true;
        for (Integer number : array) {
            if (number != 0) {
                allZeros = false;
                break;
            }
        }
        return !allZeros;
    }

    public void playerTrade(){
        endTurn.setVisible(true);
        endTurn.toFront();
        endTurnPopUp.setFill(new ImagePattern(new Image(this.getClass().getResource("p"+(nonActivePlayers.get(tradeCount).getPlayerID())+"endturn.png").toExternalForm())));
        clickToContinueButton.setOnMouseClicked(e -> {
            endTurn.setVisible(false);
            tradePopUp.setVisible(true);
            tradePopUp.toFront();
            popUpCPtradeIcon.setVisible(true);
            popUpCPtradeIcon.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[(game.getCurrentPlayer().getPlayerID() - 1)] + "player.png").toExternalForm())));
            popUpCPtradeIcon.toFront();
            popUpPlayerTradeIcon.setVisible(true);
            popUpPlayerTradeIcon.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[(nonActivePlayers.get(tradeCount)).getPlayerID()-1]+"player.png").toExternalForm())));
            popUpPlayerTradeIcon.toFront();
            acceptTrade.setVisible(true);
            declineTrade.setVisible(true);
            acceptTrade.toFront();
            declineTrade.toFront();
            for (int y = 0; y < 5; y++) {
                popUpCPtradeResCards[y].setVisible(true);
                popUpCPtradeResCards[y].toFront();
                if (downArrows[y].isVisible()){
                    popUpDownArrows[y].setVisible(true);
                    popUpDownArrows[y].toFront();
                }
                popUpCPtradeSelectionTexts[y].setText(String.valueOf(CPtradeSelectionValues[y]));
                popUpCPtradeSelectionTexts[y].setVisible(true);
                popUpCPtradeSelectionTexts[y].toFront();
                popUPtradeResCards[y].setVisible(true);
                popUPtradeResCards[y].toFront();
                popUpTradeResCardLabels[y].setVisible(true);
                popUpTradeResCardLabels[y].toFront();
                popUpTradeResCardCounts[y].setVisible(true);
                popUpTradeResCardCounts[y].toFront();
                popUpTradeResCardCounts[y].setText(String.valueOf(nonActivePlayers.get(tradeCount).resourceCards.get(ResourceType.values()[y])));
                if (upArrows[y].isVisible()){
                    popUpUpArrows[y].setVisible(true);
                    popUpUpArrows[y].toFront();
                }
                popUpTradeSelectionTexts[y].setText(String.valueOf(tradeSelectionValues[y]));
                popUpTradeSelectionTexts[y].setVisible(true);
                popUpTradeSelectionTexts[y].toFront();
            }
            acceptTrade.setOnMouseClicked(ee -> {
                boolean resourceCheck = true;
                for (int i = 0; i < 5; i++) {
                    if (tradeSelectionValues[i] > nonActivePlayers.get(tradeCount).resourceCards.get(ResourceType.values()[i])) {
                        resourceCheck = false;
                        break;
                    }
                }
                if (resourceCheck){
                    acceptedTrades.add(nonActivePlayers.get(tradeCount));
                    popUpCPtradeIcon.setVisible(false);
                    popUpPlayerTradeIcon.setVisible(false);
                    acceptTrade.setVisible(false);
                    declineTrade.setVisible(false);
                    for (int y = 0; y < 5; y++) {
                        popUpCPtradeResCards[y].setVisible(false);
                        popUpCPtradeSelectionTexts[y].setVisible(false);
                        popUPtradeResCards[y].setVisible(false);
                        popUpDownArrows[y].setVisible(false);
                        popUpUpArrows[y].setVisible(false);
                        popUpTradeSelectionTexts[y].setVisible(false);
                        popUpTradeResCardLabels[y].setVisible(false);
                        popUpTradeResCardCounts[y].setVisible(false);
                    }
                    tradePopUp.setVisible(false);
                    tradeCount++;
                    if (tradeCount < nonActivePlayers.size()) {
                        playerTrade();
                    }
                    else {
                        endTurn.setVisible(true);
                        endTurn.toFront();
                        endTurnPopUp.setFill(new ImagePattern(new Image(this.getClass().getResource("p"+(game.getCurrentPlayer().getPlayerID())+"endturn.png").toExternalForm())));
                        clickToContinueButton.setOnMouseClicked(eee -> {
                            endTurn.setVisible(false);
                            Rectangle[] tradeOptions = new Rectangle[acceptedTrades.size()];
                            if (acceptedTrades.size() > 1){
                                selectTradePopUp.setVisible(true);
                                selectTradePopUp.toFront();
                                int offset = 0;
                                for (int y = 0; y < acceptedTrades.size(); y++) {
                                    int i = y;
                                    if (acceptedTrades.size() == 2 ){
                                        offset = 595;} else if (acceptedTrades.size() == 3) {
                                        offset = 545;
                                    }
                                    Rectangle chooseTrade = new Rectangle(offset+(125*y), 425, 100, 100);
                                    chooseTrade.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[(acceptedTrades.get(y)).getPlayerID() - 1] + "player.png").toExternalForm())));
                                    tradeOptions[y] = chooseTrade;
                                    GUI.getChildren().add(chooseTrade);
                                    chooseTrade.setOnMouseClicked(ct -> {
                                        for (int z = 0; z < 5; z++) {
                                            game.getCurrentPlayer().getResourceCards().merge(ResourceType.getByIndex(z),-CPtradeSelectionValues[z],Integer::sum);
                                            game.getCurrentPlayer().getResourceCards().merge(ResourceType.getByIndex(z),tradeSelectionValues[z],Integer::sum);
                                            acceptedTrades.get(i).getResourceCards().merge(ResourceType.getByIndex(z),-tradeSelectionValues[z],Integer::sum);
                                            acceptedTrades.get(i).getResourceCards().merge(ResourceType.getByIndex(z),CPtradeSelectionValues[z],Integer::sum);
                                        }
                                        for (int z = 0; z < acceptedTrades.size(); z++) {
                                            tradeOptions[z].setVisible(false);
                                        }
                                        Arrays.fill(tradeOptions,null);
                                        selectTradePopUp.setVisible(false);
                                        acceptedTrades.clear();
                                        for (int f = 0; f < 5; f++) {
                                            CPtradeSelectionValues[f] = 0;
                                            CPtradeSelectionTexts[f].setText("0");
                                            tradeSelectionValues[f] = 0;
                                            tradeSelectionTexts[f].setText("0");
                                            downArrows[f].setVisible(false);
                                            upArrows[f].setVisible(false);
                                        }
                                        refreshUI();
                                    });
                                }

                            }
                            
                            else{
                                if (acceptedTrades.size() == 1){
                                    for (int z = 0; z < 5; z++) {
                                        game.getCurrentPlayer().getResourceCards().merge(ResourceType.getByIndex(z),-CPtradeSelectionValues[z],Integer::sum);
                                        game.getCurrentPlayer().getResourceCards().merge(ResourceType.getByIndex(z),tradeSelectionValues[z],Integer::sum);
                                        acceptedTrades.get(0).getResourceCards().merge(ResourceType.getByIndex(z),-tradeSelectionValues[z],Integer::sum);
                                        acceptedTrades.get(0).getResourceCards().merge(ResourceType.getByIndex(z),CPtradeSelectionValues[z],Integer::sum);
                                    }
                                    acceptedTrades.clear();
                                    for (int y = 0; y < 5; y++) {
                                        CPtradeSelectionValues[y] = 0;
                                        CPtradeSelectionTexts[y].setText("0");
                                        tradeSelectionValues[y] = 0;
                                        tradeSelectionTexts[y].setText("0");
                                        downArrows[y].setVisible(false);
                                        upArrows[y].setVisible(false);
                                    }
                                    refreshUI();
                                }
                            }
                        });
                    }
                }

            });

            declineTrade.setOnMouseClicked(ee -> {
                popUpCPtradeIcon.setVisible(false);
                popUpPlayerTradeIcon.setVisible(false);
                acceptTrade.setVisible(false);
                declineTrade.setVisible(false);
                for (int y = 0; y < 5; y++) {
                    popUpCPtradeResCards[y].setVisible(false);
                    popUpCPtradeSelectionTexts[y].setVisible(false);
                    popUPtradeResCards[y].setVisible(false);
                    popUpDownArrows[y].setVisible(false);
                    popUpUpArrows[y].setVisible(false);
                    popUpTradeSelectionTexts[y].setVisible(false);
                    popUpTradeResCardLabels[y].setVisible(false);
                    popUpTradeResCardCounts[y].setVisible(false);
                }
                tradePopUp.setVisible(false);
                tradeCount++;
                if (tradeCount < nonActivePlayers.size()) {
                    playerTrade();
                }
                else {
                    endTurn.setVisible(true);
                    endTurn.toFront();
                    endTurnPopUp.setFill(new ImagePattern(new Image(this.getClass().getResource("p"+(game.getCurrentPlayer().getPlayerID())+"endturn.png").toExternalForm())));
                    clickToContinueButton.setOnMouseClicked(eee -> {
                        endTurn.setVisible(false);
                        Rectangle[] tradeOptions = new Rectangle[acceptedTrades.size()];
                        if (acceptedTrades.size() > 1){
                            selectTradePopUp.setVisible(true);
                            selectTradePopUp.toFront();
                            int offset = 0;
                            for (int y = 0; y < acceptedTrades.size(); y++) {
                                int i = y;
                                if (acceptedTrades.size() == 2 ){
                                    offset = 595;} else if (acceptedTrades.size() == 3) {
                                    offset = 545;
                                }
                                Rectangle chooseTrade = new Rectangle(offset+(125*y), 425, 100, 100);
                                chooseTrade.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[(acceptedTrades.get(y)).getPlayerID() - 1] + "player.png").toExternalForm())));
                                tradeOptions[y] = chooseTrade;
                                GUI.getChildren().add(chooseTrade);
                                chooseTrade.setOnMouseClicked(ct -> {
                                    for (int z = 0; z < 5; z++) {
                                        game.getCurrentPlayer().getResourceCards().merge(ResourceType.getByIndex(z),-CPtradeSelectionValues[z],Integer::sum);
                                        game.getCurrentPlayer().getResourceCards().merge(ResourceType.getByIndex(z),tradeSelectionValues[z],Integer::sum);
                                        acceptedTrades.get(i).getResourceCards().merge(ResourceType.getByIndex(z),-tradeSelectionValues[z],Integer::sum);
                                        acceptedTrades.get(i).getResourceCards().merge(ResourceType.getByIndex(z),CPtradeSelectionValues[z],Integer::sum);
                                    }
                                    for (int z = 0; z < acceptedTrades.size(); z++) {
                                        tradeOptions[z].setVisible(false);
                                    }
                                    Arrays.fill(tradeOptions,null);
                                    selectTradePopUp.setVisible(false);
                                    acceptedTrades.clear();
                                    for (int f = 0; f < 5; f++) {
                                        CPtradeSelectionValues[f] = 0;
                                        CPtradeSelectionTexts[f].setText("0");
                                        tradeSelectionValues[f] = 0;
                                        tradeSelectionTexts[f].setText("0");
                                        downArrows[f].setVisible(false);
                                        upArrows[f].setVisible(false);
                                    }
                                    refreshUI();
                                });
                            }

                        }

                        else{
                            if (acceptedTrades.size() == 1){
                                for (int z = 0; z < 5; z++) {
                                    game.getCurrentPlayer().getResourceCards().merge(ResourceType.getByIndex(z),-CPtradeSelectionValues[z],Integer::sum);
                                    game.getCurrentPlayer().getResourceCards().merge(ResourceType.getByIndex(z),tradeSelectionValues[z],Integer::sum);
                                    acceptedTrades.get(0).getResourceCards().merge(ResourceType.getByIndex(z),-tradeSelectionValues[z],Integer::sum);
                                    acceptedTrades.get(0).getResourceCards().merge(ResourceType.getByIndex(z),CPtradeSelectionValues[z],Integer::sum);
                                }
                                acceptedTrades.clear();
                                for (int y = 0; y < 5; y++) {
                                    CPtradeSelectionValues[y] = 0;
                                    CPtradeSelectionTexts[y].setText("0");
                                    tradeSelectionValues[y] = 0;
                                    tradeSelectionTexts[y].setText("0");
                                    downArrows[y].setVisible(false);
                                    upArrows[y].setVisible(false);
                                }
                                refreshUI();
                            }
                        }
                    });
                }
            });
        });



    }

    public void throwAway(){
        discardCount.setText(String.valueOf((int) Math.floor(game.getCurrentPlayer().getResourceCount() / 2.0)));
        throwAway.setVisible(true);
        throwAway.toFront();
        discardButton.setOnMouseClicked(ee ->{
            if (game.throwAwayCards(throwAwayValues)){
                throwAwayCount = 0;
                Arrays.stream(throwAwayValueTexts).forEach(text -> text.setText("0"));
                Arrays.fill(throwAwayValues, 0);
                throwAway.setVisible(false);
            }
        });
    }

    public Pane getPermanentPane() {
        return permanentPane;
    }

}