package com.example.thesettlers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
public class GameFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("The Settlers");
        primaryStage.setFullScreen(true);
        Game game = new Game(3); //TODO get number of players from setup screen
        Scene content = new GUI(game).getGUI();
        content.setFill(Color.web("3e91c4"));
        content.getStylesheets().add(String.valueOf(this.getClass().getResource("sample.css")));
        primaryStage.setScene(content);
        primaryStage.show();
    }
    public static void main(String args[]) {
        launch(args);
    }
}