package com.example.thesettlers;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class RoadIcon {
    private Rectangle rectangle;

    RoadIcon(double x, double y, Player owner) {
        rectangle = new Rectangle(x,y,35,35);
        rectangle.setFill(new ImagePattern(new Image(this.getClass().getResource("placementcircle.png").toExternalForm())));
        rectangle.setOnMouseClicked(e -> rectangle.setFill(new ImagePattern(new Image(this.getClass().getResource("redsettlement.png").toExternalForm()))));

    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}