package com.example.thesettlers;

import com.example.thesettlers.enums.PortType;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

/**
 * Represents a settlement in the game of The Settlers.
 */
public class Settlement {
    private ArrayList<Road> roads;
    private ArrayList<Tile> tiles;
    private Player owner;
    boolean isCity;
    public Rectangle rectangle;
    private Game game;
    private PortType port;

    /**
     * Constructs a new Settlement object with the specified x, y coordinates and associated Game.
     * Initializes the Settlement with null owner and empty lists of roads and tiles. Sets up a
     * Rectangle object to represent the Settlement and sets its appearance based on the game state.
     * Also sets up the OnMouseClicked event for the Settlement's rectangle.
     *
     * @param x The x-coordinate of the Settlement's location.
     * @param y The y-coordinate of the Settlement's location.
     * @param game The associated Game object.
     */
    public Settlement(double x, double y, Game game){
        this.game = game;
        this.roads = new ArrayList<>();
        this.tiles = new ArrayList<>();
        String[] playerColours =  {"red","blue","gold","white"};
        owner = null;
        isCity = false;
        rectangle = new Rectangle(x,y,35,35);
        rectangle.setFill(new ImagePattern(new Image(this.getClass().getResource("placementcircle.png").toExternalForm())));
        rectangle.setOnMouseClicked(e -> {
            System.out.println(port);
            if(owner == null) {
                if (game.buySettlement(this)) {
                    rectangle.setX(x - 5);
                    rectangle.setY(y - 5);
                    rectangle.setHeight(45);
                    rectangle.setWidth(45);
                    rectangle.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"settlement.png").toExternalForm())));
                    game.getGameBoard().getSettlementPermPane().getChildren().add(rectangle);
                    for(Settlement settlement:game.getGameBoard().getSettlementList()){
                        for (Road road : settlement.getRoads()) {
                            if (road.getNextSettlement(settlement).getOwner() != null) {
                                settlement.getIcon().setVisible(false);
                                break;
                            } else {
                                settlement.getIcon().setVisible(true);
                            }
                        }
                    }
                }
            } else {
                if(game.upgradeToCity(this)){
                    rectangle.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"city.png").toExternalForm())));
                }
            }
        });
    }

    /**
     * Checks if there is a road connection to the given player.
     *
     * @param player The player to check for a road connection.
     * @return true if there is a road connection to the player, false otherwise.
     */
    public boolean checkRoadConnection(Player player){
        for(Road road: roads){
            if(road.getOwner() == player){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the list of roads connected to the settlement.
     *
     * @return An ArrayList of Road objects connected to the settlement.
     */
    public ArrayList<Road> getRoads() {
        return roads;
    }

    /**
     * Returns the list of tiles connected to the settlement.
     *
     * @return An ArrayList of Tile objects connected to the settlement.
     */
    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    /**
     * Returns the owner of the settlement.
     *
     * @return The player who owns the settlement.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the settlement.
     *
     * @param owner The player who owns the settlement.
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Makes the settlement a city.
     */
    public void makeCity() {
        isCity = true;
    }

    /**
     * Returns true if the settlement is a city, false otherwise.
     *
     * @return true if the settlement is a city, false otherwise.
     */
    public boolean isCity() {
        return isCity;
    }

    /**
     * Adds a road to the settlement.
     *
     * @param road The road to be added.
     */
    public void addRoad(Road road){
        roads.add(road);
    }

    /**
     * Adds a tile to the settlement.
     *
     * @param tile The tile to be added.
     */
    public void addTile(Tile tile){
        tiles.add(tile);
    }

    /**
     * Returns the settlement's icon as a Rectangle object.
     *
     * @return The settlement's icon.
     */
    public Rectangle getIcon() {
        return rectangle;
    }

    /**
     * Sets the port of the settlement.
     *
     * @param port The PortType of the port.
     */
    public void setPort(PortType port) {
        this.port = port;
    }

    /**
     * Returns the port of the settlement.
     *
     * @return The PortType of the settlement's port.
     */
    public PortType getPort() {
        return port;
    }
}
