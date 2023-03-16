package com.example.thesettlers;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Menu {
    private Pane menu = new Pane();
    private Scene scene = new Scene(menu);
    public Menu(){
        menu.setId("MENU");
        Rectangle startGame = new Rectangle(362.5, 280,715,100);
        startGame.setFill(new ImagePattern(new Image(this.getClass().getResource("startgame.png").toExternalForm())));

        Rectangle howToPlay = new Rectangle(362.5, 280+100+40,715,100);
        howToPlay.setFill(new ImagePattern(new Image(this.getClass().getResource("howtoplay.png").toExternalForm())));

        Rectangle options = new Rectangle(362.5, 280+200+80,715,100);
        options.setFill(new ImagePattern(new Image(this.getClass().getResource("options.png").toExternalForm())));

        Rectangle exit = new Rectangle(362.5, 280+300+120,715,100);

        Rectangle exitform = new Rectangle(0,0,1440,900);
        exitform.setFill(new ImagePattern(new Image(this.getClass().getResource("exitform.png").toExternalForm())));
        exitform.setVisible(false);

        Rectangle yes = new Rectangle(521.5,485,153.5,50.5);
        yes.setFill(new ImagePattern(new Image(this.getClass().getResource("yes.png").toExternalForm())));
        yes.setVisible(false);

        Rectangle no = new Rectangle(755.5,485,153.5,50.5);
        no.setFill(new ImagePattern(new Image(this.getClass().getResource("no.png").toExternalForm())));
        no.setVisible(false);
        
        exit.setFill(new ImagePattern(new Image(this.getClass().getResource("exit.png").toExternalForm())));
        exit.setOnMouseClicked(e -> {
            exitform.toFront();
            yes.toFront();
            no.toFront();
            exitform.setVisible(true);
            yes.setVisible(true);
            no.setVisible(true);
            yes.setOnMouseClicked(i -> {
                Platform.exit();
            });
            no.setOnMouseClicked(i -> {
                exitform.setVisible(false);
                yes.setVisible(false);
                no.setVisible(false);
            });
        });
        menu.getChildren().addAll(startGame,howToPlay,options,exit,exitform,yes,no);

    }

    public Scene getMenu() {
        return scene;
    }
}
