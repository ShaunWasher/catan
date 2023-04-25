package com.example.thesettlers;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import static java.lang.Math.sqrt;

public class Robber extends Circle {

    Boolean active;
    Tile tile;

    public Robber(double x, double y , Tile tile,Game game){
        super((x + ((sqrt(3) / 2) * 70)),(y+35),22.5);
        setFill(new ImagePattern(new Image(this.getClass().getResource("select.png").toExternalForm())));
        this.tile = tile;
        active = false;
        setOnMouseClicked(e ->{
            System.out.println("CLICKED");
            game.setAllRobbersInactive();
            setActive(true);
            game.getGameBoard().getActiveRobber().getChildren().clear();
            game.getGameBoard().getActiveRobber().getChildren().add(this);
            game.getGameBoard().getRobberPane().setVisible(false);
        });

    }

    public void setActive(Boolean active) {
        this.active = active;
        if (active == true){
            setFill(new ImagePattern(new Image(this.getClass().getResource("robber.png").toExternalForm())));
        }
        if (active == false){
            setFill(new ImagePattern(new Image(this.getClass().getResource("select.png").toExternalForm())));
        }
    }
}
