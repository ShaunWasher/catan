package com.example.numbertwo;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import java.util.Objects;

public class Tile extends Polygon {
    private Image image;

    Tile(double r, double n, double TILE_WIDTH, double x, double y, String type) {
        // creates the polygon using the corner coordinates
        getPoints().addAll(
                x, y,
                x, y + r,
                x + n, y + r * 1.5,
                x + TILE_WIDTH, y + r,
                x + TILE_WIDTH, y,
                x + n, y - r * 0.5
        );
        
        setFill(new ImagePattern(getImage(type)));
        setStrokeWidth(6);
        setStroke(Color.web("f9c872"));
        setOnMouseClicked(e -> System.out.println("Clicked: " + this));
    }

    public Image getImage(String type) {
        if (Objects.equals(type, "HILLS")) {
            this.image = new Image(this.getClass().getResource("hills.png").toExternalForm());
        } else if (Objects.equals(type, "FOREST")) {
            this.image = new Image(this.getClass().getResource("forest.png").toExternalForm());
        } else if (Objects.equals(type, "MOUNTAIN")) {
            this.image = new Image(this.getClass().getResource("mountains.png").toExternalForm());
        } else if (Objects.equals(type, "FIELDS")) {
            this.image = new Image(this.getClass().getResource("fields.png").toExternalForm());
        } else if (Objects.equals(type, "PASTURE")) {
            this.image = new Image(this.getClass().getResource("pasture.png").toExternalForm());
        } else if (Objects.equals(type, "DESERT")) {
            this.image = new Image(this.getClass().getResource("desert.png").toExternalForm());
        } else {
            this.image = new Image(this.getClass().getResource("image.png").toExternalForm());
        }
        return this.image;
    }



}