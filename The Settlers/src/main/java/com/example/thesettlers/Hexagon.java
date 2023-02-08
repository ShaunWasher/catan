package com.example.thesettlers;

import com.example.thesettlers.enums.TerrainType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import java.util.Objects;

import static java.lang.Math.sqrt;

public class Hexagon extends Polygon {
    private Image image;
    private final static double r = 60; // the inner radius from hexagon center to outer corner
    private final static double n = sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis
    Hexagon(double x, double y, TerrainType type) {
        // creates the polygon using the corner coordinates
        getPoints().addAll(
                x, y,
                x, y + r,
                x + n, y + r * 1.5,
                x + (2*n), y + r,
                x + (2*n), y,
                x + n, y - r * 0.5
        );
        
        setFill(new ImagePattern(getImage(type)));
        setStrokeWidth(6);
        setStroke(Color.web("f9c872"));
        setOnMouseClicked(e -> System.out.println("Clicked: " + this));
    }

    public Image getImage(TerrainType type) {
        if (Objects.equals(type, TerrainType.HILLS)) {
            this.image = new Image(this.getClass().getResource("hills.png").toExternalForm());
        } else if (Objects.equals(type, TerrainType.FOREST)) {
            this.image = new Image(this.getClass().getResource("forest.png").toExternalForm());
        } else if (Objects.equals(type, TerrainType.MOUNTAINS)) {
            this.image = new Image(this.getClass().getResource("mountains.png").toExternalForm());
        } else if (Objects.equals(type, TerrainType.FIELDS)) {
            this.image = new Image(this.getClass().getResource("fields.png").toExternalForm());
        } else if (Objects.equals(type, TerrainType.PASTURE)) {
            this.image = new Image(this.getClass().getResource("pasture.png").toExternalForm());
        } else if (Objects.equals(type, TerrainType.DESERT)) {
            this.image = new Image(this.getClass().getResource("desert.png").toExternalForm());
        } else {
            this.image = new Image(this.getClass().getResource("image.png").toExternalForm());
        }
        return this.image;
    }

}