package com.example.thesettlers;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.List;

public class GUI {

    private Pane GUI = new Pane();
    private Rectangle box;
    private Rectangle dice2;
    private Rectangle brick;
    private Rectangle lumber;
    private Rectangle ore;
    private Rectangle grain;
    private Rectangle wool;
    private Rectangle devCard;
    private Rectangle buyRoad;
    private Rectangle buySettlement;
    private Rectangle buyCity;
    private Rectangle buyDevelopmentCard;
    private Rectangle trade;
    private Rectangle endTurn;

    public Pane getGUI() {

        box = new Rectangle(360-75,765,715,140);
        box.setFill(new ImagePattern(new Image(this.getClass().getResource("box.png").toExternalForm())));
        GUI.getChildren().add(box);

        Rectangle box2 = new Rectangle(1090-75,765,140,140);
        box2.setFill(new ImagePattern(new Image(this.getClass().getResource("box2.png").toExternalForm())));
        GUI.getChildren().add(box2);

        Rectangle dice1 = new Rectangle(1090-60,790,50,50);
        dice1.setFill(new ImagePattern(new Image(this.getClass().getResource("d6.png").toExternalForm())));
        GUI.getChildren().add(dice1);

        dice2 = new Rectangle(1090,790,50,50);
        dice2.setFill(new ImagePattern(new Image(this.getClass().getResource("d2.png").toExternalForm())));
        GUI.getChildren().add(dice2);

        String[] resourceTypes = {"brick","lumber","ore","grain","wool"};
        for (int y = 0; y < 5; y++) {
            Rectangle resCard = new Rectangle(380-67.5 + (70 * y), 790, 60, 84);
            Rectangle label = new Rectangle(397.5-67.5 + (70 * y), 860, 25, 25);
            resCard.setFill(new ImagePattern(new Image(this.getClass().getResource(resourceTypes[y] + ".png").toExternalForm())));
            label.setFill(new ImagePattern(new Image(this.getClass().getResource(resourceTypes[y] + "label.png").toExternalForm())));
            GUI.getChildren().addAll(resCard,label);

        }
        Rectangle devCard = new Rectangle(750-67.5, 790, 60, 84);
        devCard.setFill(new ImagePattern(new Image(this.getClass().getResource("devcard.png").toExternalForm())));
        GUI.getChildren().add(devCard);

        Rectangle button1 = new Rectangle(840-67.5, 790,60,39.5);
        button1.setFill(new ImagePattern(new Image(this.getClass().getResource("buyroad.png").toExternalForm())));
        Rectangle button2 = new Rectangle(910-67.5, 790,60,39.5);
        button2.setFill(new ImagePattern(new Image(this.getClass().getResource("buysettlement.png").toExternalForm())));
        Rectangle button3 = new Rectangle(980-67.5, 790,60,39.5);
        button3.setFill(new ImagePattern(new Image(this.getClass().getResource("buycity.png").toExternalForm())));

        Rectangle button4 = new Rectangle(840-67.5, 839.5,60,39.5);
        button4.setFill(new ImagePattern(new Image(this.getClass().getResource("buydev.png").toExternalForm())));
        Rectangle button5 = new Rectangle(910-67.5, 839.5,60,39.5);
        button5.setFill(new ImagePattern(new Image(this.getClass().getResource("trade.png").toExternalForm())));
        Rectangle button6 = new Rectangle(980-67.5, 839.5,60,39.5);
        button6.setFill(new ImagePattern(new Image(this.getClass().getResource("endturn.png").toExternalForm())));

        Rectangle button7 = new Rectangle(1040, 844.5+5,90,39.5);
        button7.setFill(new ImagePattern(new Image(this.getClass().getResource("roll.png").toExternalForm())));
        button7.setOnMouseClicked(e -> {
            //rollDice();
        });

        GUI.getChildren().addAll(button1,button2,button3,button4,button5,button6,button7);

        return GUI;
    }









}
