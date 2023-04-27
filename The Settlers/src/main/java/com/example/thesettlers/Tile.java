package com.example.thesettlers;

import com.example.thesettlers.enums.TerrainType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.util.Objects;

import static java.lang.Math.sqrt;

/**
 * Represents a hexagonal tile in the game of The Settlers.
 */
public class Tile extends Polygon {
    private Image image;
    private final static double r = 70; // the inner radius from hexagon center to outer corner
    private final static double n = sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis
    private TerrainType tileType;
    private int value;
    private boolean robber;
    private ValueLabel valueLabel;
    private Circle robberImage;

    /**
     * Constructs a new Tile object with the specified x, y coordinates, type, value, and associated Game.
     * Initializes the Tile with the given type, value, and an Image representation of the tile type.
     * Sets up a Circle object to represent the robber and a ValueLabel object for the tile's value.
     * Also sets up the OnMouseClicked event for the Tile's Polygon object.
     *
     * @param x The x-coordinate of the Tile's location.
     * @param y The y-coordinate of the Tile's location.
     * @param type The type of the Tile as a String (e.g., "HILLS", "FOREST", etc.).
     * @param value The numerical value associated with the Tile.
     * @param game The associated Game object.
     */
    public Tile(double x, double y, String type, int value,Game game) {
        this.value = value;
        robberImage = new Circle((x + ((sqrt(3) / 2) * 70)),(y+35),35);
        robberImage.setFill(new ImagePattern(new Image(this.getClass().getResource("robber.png").toExternalForm())));
        robber = false;
        if (Objects.equals(type, "HILLS")) {
            tileType = TerrainType.HILLS;
        } else if (Objects.equals(type, "FOREST")) {
            tileType = TerrainType.FOREST;
        } else if (Objects.equals(type, "MOUNTAINS")) {
            tileType = TerrainType.MOUNTAINS;
        } else if (Objects.equals(type, "FIELDS")) {
            tileType = TerrainType.FIELDS;
        } else if (Objects.equals(type, "PASTURE")) {
            tileType = TerrainType.PASTURE;
        } else if (Objects.equals(type, "DESERT")) {
            tileType = TerrainType.DESERT;
        } else { }
        getPoints().addAll(
                x, y,
                x, y + r,
                x + n, y + r * 1.5,
                x + (2*n), y + r,
                x + (2*n), y,
                x + n, y - r * 0.5
        );

        setFill(new ImagePattern(getImage(tileType)));
        setStrokeWidth(6);
        setStroke(Color.web("f9c872"));
        setOnMouseClicked(e -> {
            System.out.println("Clicked: " + this);
            game.getGameBoard().transparency(false);
            game.getRobber().setRobber(false);
            game.setPlaceRobber(false);
            setRobber(true);
            game.setRobber(this);
            game.stealCardOptions();
        });

        valueLabel = new ValueLabel(x, y, value);
    }

    /**
     * Returns the value label (the number) of the tile as a Circle object.
     *
     * @return The Circle object representing the value label of the tile.
     */
    public Circle getValueLabel(){
        return valueLabel.getCircle();
    }

    /**
     * Sets the robber presence on the tile.
     *
     * @param robber A boolean value indicating whether the robber is on the tile or not.
     */
    public void setRobber(boolean robber) {
        this.robber = robber;
        if(robber){
            robberImage.setVisible(true);
        }
        else{
            robberImage.setVisible(false);
        }
    }

    /**
     * Returns true if the robber is on the tile, false otherwise.
     *
     * @return true if the robber is on the tile, false otherwise.
     */
    public boolean isRobber(){
        return robber;
    }

    /**
     * Returns the robber's image as a Circle object.
     *
     * @return The Circle object representing the robber's image.
     */
    public Circle getRobberImage() {
        return robberImage;
    }

    /**
     * Returns the value (number) of the tile.
     *
     * @return The value (number) of the tile.
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the terrain type of the tile.
     *
     * @return The TerrainType enum value of the tile.
     */
    public TerrainType getTileType() {
        return tileType;
    }

    /**
     * Returns the image representing the terrain type of the tile.
     *
     * @param type The TerrainType of the tile.
     * @return The Image object representing the terrain type of the tile.
     */
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