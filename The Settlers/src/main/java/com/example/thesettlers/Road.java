package com.example.thesettlers;

import com.example.thesettlers.enums.GameState;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Represents a road in the game of The Settlers.
 */
public class Road {
    private Player owner;
    private Settlement settlementA;
    private Settlement settlementB;
    public Rectangle rectangle;
    private Image image;
    private Game game;
    private String[] playerColours;

    /**
     * Constructs a new Road object with the specified x, y coordinates, version, and associated Game.
     * Initializes the Road with null settlements and owner, and sets up a Rectangle object to represent
     * the Road. Also sets up the OnMouseClicked event for the Road's rectangle.
     *
     * @param x The x-coordinate of the Road's location.
     * @param y The y-coordinate of the Road's location.
     * @param version The version of the Road's image.
     * @param game The associated Game object.
     */
    public Road(double x, double y, int version, Game game){
        this.game = game;
        settlementA = null;
        settlementB = null;
        owner = null;
        playerColours = new String[]{"red", "blue", "gold", "white"};
        rectangle = new Rectangle(x,y,35,35);
        rectangle.setFill(new ImagePattern(new Image(this.getClass().getResource("placementcircle.png").toExternalForm())));
        rectangle.setOnMouseClicked(e -> {
            if(game.buyRoad(this)){
                rectangle.setX(x - 13);
                rectangle.setY(y - 13);
                rectangle.setHeight(61);
                rectangle.setWidth(61);
                rectangle.setFill(new ImagePattern(getImage(version)));
                game.getGameBoard().getRoadPermPane().getChildren().add(rectangle);
                for(Road road:game.getGameBoard().getRoadList()){
                    road.getIcon().setVisible(true);
                }
                // moves to the next player as part of the starting phase of the game
                if(game.gameState == GameState.START){
                    game.nextPlayer();
                    game.gui.refreshUI();
                }
                if(game.roadBuilding > 0){
                    game.useRoadBuildingCard();
                }
            }
        });
    }

    /**
     * Returns the owner of the road.
     *
     * @return The player who owns the road.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Returns the first settlement connected to the road.
     *
     * @return The first settlement connected to the road.
     */
    public Settlement getSettlementA() {
        return settlementA;
    }

    /**
     * Returns the second settlement connected to the road.
     *
     * @return The second settlement connected to the road.
     */
    public Settlement getSettlementB() {
        return settlementB;
    }

    /**
     * Sets the owner of the road.
     *
     * @param owner The player who owns the road.
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Returns the next settlement connected to the road based on the starting settlement.
     *
     * @param startingSettlement The starting settlement.
     * @return The next settlement connected to the road.
     */
    public Settlement getNextSettlement(Settlement startingSettlement){
        if(startingSettlement == settlementA){
            return settlementB;
        }
        else if(startingSettlement == settlementB){
            return settlementA;
        }
        return null;
    }

    /**
     * Adds the settlements connected to the road.
     *
     * @param settlementA The first settlement connected to the road.
     * @param settlementB The second settlement connected to the road.
     */
    public void addSettlements(Settlement settlementA, Settlement settlementB) {
        this.settlementA = settlementA;
        this.settlementB = settlementB;
    }

    /**
     * Returns the road's icon as a Rectangle object.
     *
     * @return The road's icon.
     */
    public Rectangle getIcon() {
        return rectangle;
    }

    /**
     * Returns the road's image based on the road version.
     *
     * @param version The road version (1, 2, or 3).
     * @return The road's image.
     */
    public Image getImage(int version) {
        if (version == 1) {
            this.image = new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"road1.png").toExternalForm());
        } else if (version == 2) {
            this.image = new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"road2.png").toExternalForm());
        } else if (version == 3) {
            this.image = new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"road3.png").toExternalForm());
        } else {
        }
        return this.image;
    }

    /**
     * Returns the settlement connected to the road that is not the given settlement.
     *
     * @param settlement The given settlement.
     * @return The other settlement connected to the road.
     * @throws IllegalArgumentException If the given settlement is not connected to the road.
     */
    public Settlement getOtherSettlement(Settlement settlement) {
        if (settlementA == settlement) {
            return settlementB;
        } else if (settlementB == settlement) {
            return settlementA;
        } else {
            throw new IllegalArgumentException("The given settlement is not connected to this road.");
        }
    }

}
