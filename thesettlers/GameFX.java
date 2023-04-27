package com.example.thesettlers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
public class GameFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {//The start method launches the application.
        primaryStage.setTitle("The Settlers");// Set the title of the application to "The Settlers"
        primaryStage.setFullScreen(true);//Sets the application to full screen
        Game game = new Game(3, 10);//Creates a new instance of a class called game, and the number of players is passed through the argument.
        GUI gui = new GUI(game);
        game.setGUI(gui);
        Scene content = gui.getGUI();// The "getGUI" method returns a JavaFX scene containing the User Interface elements.
        content.setFill(Color.web("3e91c4"));// Sets the background color to a shade of blue.
        content.getStylesheets().add(String.valueOf(this.getClass().getResource("sample.css")));
        primaryStage.setScene(content);
        primaryStage.show();
    }
    public static void main(String args[]) {
        launch(args);
    }
}