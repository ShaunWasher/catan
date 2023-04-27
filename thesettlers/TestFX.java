package com.example.thesettlers;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
public class TestFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        double x = primScreenBounds.getWidth();
        double y = primScreenBounds.getHeight();
        System.out.print("x"+x+"y"+y);

        VBox box = new VBox();
        final Scene scene = new Scene(box,x, y);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
    public static void main(String args[]) {
        launch(args);
    }
}