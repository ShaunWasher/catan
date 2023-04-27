package com.example.thesettlers;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Road {// Declare new public class
    private Player owner;//Declare a private instance variable called owner of type Player.
    private Settlement settlementA;//This line declares a private instance variable called settlementA of type Settlement.
    private Settlement settlementB;//This line declares a private instance variable called settlementB of type Settlement.
    private Rectangle rectangle;//This line declares a private instance variable called rectangle of type Rectangle.
    private Image image;//This line declares a private instance variable called image of type Image.
    private Game game;

    public Road(double x, double y, int version, Game game){//This line defines a constructor for the Road class that takes in three parameters: x and y coordinates, and a version integer.
        this.game = game;
        settlementA = null;//Set variable Settlement A to null, indicating it has no value at this point.
        settlementB = null;//Set variable Settlement B to null, indicating it has no value at this point.
        owner = null;//Set variable owner to null, indicating it has no value at this point.
        rectangle = new Rectangle(x,y,35,35);//This line creates a new Rectangle object with the specified x and y coordinates and a width and height of 35.
        rectangle.setFill(new ImagePattern(getImage(version)));//This line fills the rectangle to an ImagePattern created from an image obtained by calling the getImage() method with the integer.
        rectangle.setFill(new ImagePattern(new Image(this.getClass().getResource("placementcircle.png").toExternalForm())));//This line sets the fill of the rectangle to a new ImagePattern object created from an image obtained by loading a file called placementcircle.png.
        rectangle.setOnMouseClicked(e -> {//This line sets an OnMouseClicked event handler for the rectangle, which changes the position and size of the rectangle and updates its fill when the rectangle is clicked.

            if(game.buyRoad(this)){
                rectangle.setX(x - 13);
                rectangle.setY(y - 13);
                rectangle.setHeight(61);
                rectangle.setWidth(61);
                rectangle.setFill(new ImagePattern(getImage(version)));
            }
        });
    }

    public Player getOwner() {// This line defines a method named getOwner() that returns the owner of the city or settlement.
        return owner;
    }

    public Settlement getSettlementA() {//This line defines a public method called getSettlementA() that returns settlementA.
        return settlementA;
    }//return settlement A

    public Settlement getSettlementB() {//This line defines a public method called getSettlementB() that returns settlementB.
        return settlementB;
    }// Return settlement B

    public void setOwner(Player owner) {//This line defines a public method called setOwner() that takes in a Player object as a parameter and sets the owner instance variable to that object.
        this.owner = owner;
    }

    public Settlement getNextSettlement(Settlement startingSettlement){//This line defines a public method called getNextSettlement() that takes in a Settlement object as a parameter and returns the other Settlement object connected to the road.
        // If the input settlement is not connected to the road, the method returns null.
        if(startingSettlement == settlementA){
            return settlementB;
        }
        else if(startingSettlement == settlementB){
            return settlementA;
        }
        return null;
    }

    public void addSettlements(Settlement settlementA, Settlement settlementB) {//This line defines a public method called
        // addSettlements() that takes in two Settlement objects as parameters and sets the settlementA and settlementB instance variables to these objects.
        this.settlementA = settlementA;
        this.settlementB = settlementB;
    }
    public Rectangle getIcon() {//This line defines a public method called getIcon() that returns the rectangle instance variable, which is a Rectangle object.
        return rectangle;
    }
    public Image getImage(int version) {//This line defines a public method called getImage() that takes in an int parameter called version.
        if (version == 1) {//This method loads an image based on the version parameter and returns it as an Image object.
            this.image = new Image(this.getClass().getResource("redroad1.png").toExternalForm());//If version is not 1, the method does not set any value to the this.image instance variable.
        } else if (version == 2) {//If version is not 2, the method does not set any value to the this.image instance variable.
            this.image = new Image(this.getClass().getResource("redroad2.png").toExternalForm());
        } else if (version == 3) {//If version is not 3, the method does not set any value to the this.image instance variable.
            this.image = new Image(this.getClass().getResource("redroad3.png").toExternalForm());
        } else {
        }
        return this.image;
    }

}
