package com.example.numbertwo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
public class GameFX extends Application {

    public GameBoard gameBoard;


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("The Settlers");
        primaryStage.setFullScreen(true);
        gameBoard = new GameBoard("Starting Map");
        AnchorPane tileMap = gameBoard.getBoard();
        tileMap.setId("tile_map");
        Scene content = new Scene(tileMap);
        content.setFill(Color.web("3e91c4"));
        content.getStylesheets().add(String.valueOf(this.getClass().getResource("sample.css")));
        primaryStage.setScene(content);
        primaryStage.show();
    }
    public static void main(String args[]) {
        launch(args);
    }
}