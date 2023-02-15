package com.example.thesettlers;

import com.example.thesettlers.enums.TerrainType;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class RoadIcon {
    private Rectangle rectangle;
    private Image image;

    RoadIcon(double x, double y, int version) {
        rectangle = new Rectangle(x,y,35,35);
        rectangle.setFill(new ImagePattern(getImage(version)));
        rectangle.setFill(new ImagePattern(new Image(this.getClass().getResource("placementcircle.png").toExternalForm())));
        rectangle.setOnMouseClicked(e -> {
            rectangle.setX(x - 13);
            rectangle.setY(y - 13);
            rectangle.setHeight(61);
            rectangle.setWidth(61);
            rectangle.setFill(new ImagePattern(getImage(version)));
        });
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
    public Image getImage(int version) {
        if (version == 1) {
            this.image = new Image(this.getClass().getResource("redroad1.png").toExternalForm());
        } else if (version == 2) {
            this.image = new Image(this.getClass().getResource("redroad2.png").toExternalForm());
        } else if (version == 3) {
            this.image = new Image(this.getClass().getResource("redroad3.png").toExternalForm());
        } else {
        }
        return this.image;
    }



}