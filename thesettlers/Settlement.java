package com.example.thesettlers;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

public class Settlement {// Defines a settlement class
    private ArrayList<Road> roads;//Create array list for roads
    private ArrayList<Tile> tiles;// Create array list for tiles
    private Player owner; // Owner represented by a player object
    boolean isCity;// boolean indicates whether the settlement is a city or not
    private Rectangle rectangle;
    private Game game;
    public Settlement(double x, double y, Game game){// This line declares the constructor for the class, has two parameters x and y(represent the settlement location
        this.game = game;
        this.roads = new ArrayList<>();// Array list for road is initialized with an empty list.
        this.tiles = new ArrayList<>();//Array list for tiles is initialized with an empty list.
        owner = null;//owner set to null
        isCity = false;//City set to false
        rectangle = new Rectangle(x,y,35,35);//This line creates a new Rectangle object with the specified x and y coordinates, and a width and height of 35 pixels. This Rectangle will represent the boundaries of the Settlement on the game board.
        rectangle.setFill(new ImagePattern(new Image(this.getClass().getResource("placementcircle.png").toExternalForm())));//This line sets the fill of the Rectangle to an Image that is created using an image file named "placementcircle.png".
        rectangle.setOnMouseClicked(e -> {//This line sets an event handler that will execute when the Rectangle is clicked.
            if(owner == null) {
                if (game.buySettlement(this)) {
                    rectangle.setX(x - 5);//This line sets the x coordinate of the Rectangle to be 5 pixels less than the original coordinates
                    rectangle.setY(y - 5);//This line sets the y coordinate of the Rectangle to be 5 pixels less than the original coordinates
                    rectangle.setHeight(45);//This line sets the height of the Rectangle to be 45 pixels, which is larger than the original size of 35 pixels.
                    rectangle.setWidth(45);//This line sets width of the Rectangle to be 45 pixels, which is larger than the original size of 35 pixels.
                    rectangle.setFill(new ImagePattern(new Image(this.getClass().getResource("redsettlement.png").toExternalForm())));
                }
            } else {
                if(game.upgradeToCity(this)){
                    //TODO make settlement look like city
                }
            }
        });
    }

    //check if there is a road connection to the player
    public boolean checkRoadConnection(Player player){//This line declares a method named checkRoadConnection that takes in a Player object as a parameter.
        for(Road road: roads){//This line starts a for loop that iterates over each Road object in the Settlement's list of roads.
            if(road.getOwner() == player){//This line checks whether the owner of the current Road is the same as the input Player object.
                return true;//If the owner of the current Road is the same as the input Player object, this line returns true
            }
        }
        return false;//If the owner of the current Road object is not the same as the input Player object, this line returns false
    }

    public ArrayList<Road> getRoads() {//This method returns the roads ArrayList that contains all the roads connected to this object.
        return roads;//This statement returns the roads ArrayList object.
    }

    public ArrayList<Tile> getTiles() {//This method returns the tiles ArrayList that contains all the tiles that make up this object.
        return tiles;//This statement returns the tiles ArrayList object.
    }

    public Player getOwner() {//This method returns the owner of this object.
        return owner;
    }

    public void setOwner(Player owner) {//This method sets the owner of this object to the owner parameter.
        this.owner = owner;//This statement sets the owner of this object to the owner parameter.
    }

    public void makeCity() {//This method sets the isCity variable of this object to true, showing that this object has been upgraded to a city.
        isCity = true;//This statement sets the isCity variable to true.
    }

    public boolean isCity() {//This method returns whether this object is a city or not.
        return isCity;// Returns the value of the variable
    }

    public void addRoad(Road road){//This method adds a road object to the roads ArrayList.
        roads.add(road);//This method adds a road object to the roads ArrayList.
    }
    public void addTile(Tile tile){//This method adds a tile object to the tiles ArrayList.
        tiles.add(tile);//This method adds a tile object to the tiles ArrayList.
    }
    public Rectangle getIcon() {//This method returns a Rectangle that represents the icon.
        return rectangle;//This statement returns the rectangle object.
    }
}
