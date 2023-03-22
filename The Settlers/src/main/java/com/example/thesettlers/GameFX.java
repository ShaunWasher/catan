package com.example.thesettlers;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
public class GameFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("The Settlers");
        primaryStage.setFullScreen(true);
        Game game = new Game(2, 10); //TODO get number of players from setup screen
        GUI gui = new GUI(game);
        game.setGUI(gui);
        Scene content = gui.getGUI();
        content.setFill(Color.web("3e91c4"));
        content.getStylesheets().add(String.valueOf(this.getClass().getResource("sample.css")));

        final int initWidth = 1400;      //initial width
        final int initHeight = 900;    //initial height
        final Pane root = new Pane();   //necessary evil

        Pane controller = gui.getGUIPane();   //initial view
        controller.setPrefWidth(initWidth);     //if not initialized
        controller.setPrefHeight(initHeight);   //if not initialized
        root.getChildren().add(controller);     //necessary evil

        Scale scale = new Scale(1, 1, 0, 0);
        scale.xProperty().bind(root.widthProperty().divide(initWidth));     //must match with the one in the controller
        scale.yProperty().bind(root.heightProperty().divide(initHeight));   //must match with the one in the controller
        root.getTransforms().add(scale);

        final Scene scene = new Scene(root, initWidth, initHeight);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();

        //add listener for the use of scene.setRoot()
        scene.rootProperty().addListener(new ChangeListener<Parent>(){
            @Override public void changed(ObservableValue<? extends Parent> arg0, Parent oldValue, Parent newValue){
                scene.rootProperty().removeListener(this);
                scene.setRoot(root);
                ((Region)newValue).setPrefWidth(initWidth);     //make sure is a Region!
                ((Region)newValue).setPrefHeight(initHeight);   //make sure is a Region!
                root.getChildren().clear();
                root.getChildren().add(newValue);
                scene.rootProperty().addListener(this);
            }
        });}
    public static void main(String args[]) {
        launch(args);
    }
}