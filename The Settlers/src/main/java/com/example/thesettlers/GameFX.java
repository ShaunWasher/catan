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
        Pane gameBoard = new GameBoard("Starting Map").getBoard();
        gameBoard.setId("tile_map");
        Pane GUI = new GUI().getGUI();
        gameBoard.getChildren().add(GUI);
        Scene content = new Scene(gameBoard);
        content.setFill(Color.web("3e91c4"));
        content.getStylesheets().add(String.valueOf(this.getClass().getResource("sample.css")));
        primaryStage.setScene(content);
        primaryStage.show();
    }
    public static void main(String args[]) {
        launch(args);
    }
}