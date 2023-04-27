package com.example.thesettlers;

import javafx.scene.layout.Pane;

/**
 * The SceneChanger interface defines a method for changing scenes in a JavaFX application.
 * Classes that implement this interface can provide their own implementation of the
 * changeScene method to switch between different JavaFX Panes.
 */
public interface SceneChanger {

    /**
     * Changes the current scene to the specified JavaFX Pane.
     *
     * @param newScenePane The JavaFX Pane object that represents the new scene to be displayed.
     */
    void changeScene(Pane newScenePane);
}