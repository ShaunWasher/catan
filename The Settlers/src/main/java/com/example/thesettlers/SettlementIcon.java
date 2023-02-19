package com.example.thesettlers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class SettlementIcon {
    private Rectangle rectangle;

    SettlementIcon(double x, double y, Player owner) {
        rectangle = new Rectangle(x,y,35,35);
        rectangle.setFill(new ImagePattern(new Image(this.getClass().getResource("placementcircle.png").toExternalForm())));
        rectangle.setOnMouseClicked(e -> {
            rectangle.setX(x - 5);
            rectangle.setY(y - 5);
            rectangle.setHeight(45);
            rectangle.setWidth(45);
            rectangle.setFill(new ImagePattern(new Image(this.getClass().getResource("redsettlement.png").toExternalForm())));
        });
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}