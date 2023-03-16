package com.example.thesettlers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
public class MenuFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("The Settlers");
        primaryStage.setFullScreen(true);
        Menu gui = new Menu();
        Scene content = gui.getMenu();
        content.setFill(Color.web("3e91c4"));
        content.getStylesheets().add(String.valueOf(this.getClass().getResource("sample.css")));
        primaryStage.setScene(content);
        primaryStage.show();
    }
    public static void main(String args[]) {
        launch(args);
    }
}