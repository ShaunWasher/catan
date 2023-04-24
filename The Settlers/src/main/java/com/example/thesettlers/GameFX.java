package com.example.thesettlers;

import com.example.thesettlers.enums.BoardType;
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
public class GameFX extends Application implements SceneChanger {

    private Pane root;
    @Override
    public void changeScene(Pane newScenePane) {
        root.getChildren().clear();
        newScenePane.setPrefWidth(root.getPrefWidth());
        newScenePane.setPrefHeight(root.getPrefHeight());
        root.getChildren().add(newScenePane);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("The Settlers");
        primaryStage.setFullScreen(true);

        Menu menu = new Menu();
        menu.setSceneChanger(this);

        final int initWidth = 1440;      //initial width
        final int initHeight = 900;    //initial height
        root = new Pane();   //necessary evil

        Pane controller = menu.getMenuPane();   //initial view
        controller.setPrefWidth(initWidth);     //if not initialized
        controller.setPrefHeight(initHeight);   //if not initialized
        root.getChildren().add(controller);     //necessary evil

        Scale scale = new Scale(1, 1, 0, 0);
        scale.xProperty().bind(root.widthProperty().divide(initWidth));     //must match with the one in the controller
        scale.yProperty().bind(root.heightProperty().divide(initHeight));   //must match with the one in the controller
        root.getTransforms().add(scale);

        final Scene scene = new Scene(root, initWidth, initHeight);
        scene.setFill(Color.web("3e91c4"));
        scene.getStylesheets().add(String.valueOf(this.getClass().getResource("sample.css")));
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