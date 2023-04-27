package com.example.thesettlers;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.util.Objects;
import static java.lang.Math.sqrt;

/**
 * Represents a value label (number) displayed on a hexagonal tile in the game The Settlers.
 */
public class ValueLabel {
    private Image image;
    private Circle circle;

    /**
     * Constructs a new ValueLabel object with the specified x, y coordinates, and value.
     * Initializes the ValueLabel with a Circle object that represents the label, and sets its
     * fill with the Image corresponding to the provided value.
     *
     * @param x The x-coordinate of the ValueLabel's location.
     * @param y The y-coordinate of the ValueLabel's location.
     * @param value The numerical value associated with the ValueLabel.
     */
    ValueLabel(double x, double y, int value) {
        circle = new Circle((x + ((sqrt(3) / 2) * 70)),(y+35),20);
        circle.setFill(new ImagePattern(getImage(value)));
    }

    /**
     * Returns the Circle object representing the value label.
     *
     * @return The Circle object representing the value label.
     */
    public Circle getCircle() {
        return circle;
    }

    /**
     * Returns the image representing the value label's number.
     *
     * @param value The integer value of the value label.
     * @return The Image object representing the value label's number.
     */
    public Image getImage(int value) {
        if (Objects.equals(value, 2)) {
            this.image = new Image(this.getClass().getResource("2.png").toExternalForm());
        } else if (Objects.equals(value, 3)) {
            this.image = new Image(this.getClass().getResource("3.png").toExternalForm());
        } else if (Objects.equals(value, 4)) {
            this.image = new Image(this.getClass().getResource("4.png").toExternalForm());
        } else if (Objects.equals(value, 5)) {
            this.image = new Image(this.getClass().getResource("5.png").toExternalForm());
        } else if (Objects.equals(value, 6)) {
            this.image = new Image(this.getClass().getResource("6.png").toExternalForm());
        } else if (Objects.equals(value, 8)) {
            this.image = new Image(this.getClass().getResource("8.png").toExternalForm());
        } else if (Objects.equals(value, 9)) {
            this.image = new Image(this.getClass().getResource("9.png").toExternalForm());
        } else if (Objects.equals(value, 10)) {
            this.image = new Image(this.getClass().getResource("10.png").toExternalForm());
        } else if (Objects.equals(value, 11)) {
            this.image = new Image(this.getClass().getResource("11.png").toExternalForm());
        } else if (Objects.equals(value, 12)) {
            this.image = new Image(this.getClass().getResource("12.png").toExternalForm());
        } else {
            this.image = new Image(this.getClass().getResource("0.png").toExternalForm());
        }
        return this.image;
    }

}
