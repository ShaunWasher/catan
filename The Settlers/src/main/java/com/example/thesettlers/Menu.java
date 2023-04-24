package com.example.thesettlers;

import com.example.thesettlers.enums.BoardType;
import com.example.thesettlers.enums.GameVersion;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

public class Menu {
    private SceneChanger sceneChanger;
    private GameFX fx;
    private Stage primaryStage;
    private Game game;
    private Pane menu = new Pane();
    private Scene scene = new Scene(menu);
    private GameVersion gameversion;
    private BoardType boardtype;
    private int players;
    private int agents;
    private String[] playerColours;
    private Rectangle[] playerIcons;
    private Rectangle[] AIIcons;
    private Rectangle[] addPlayerButtons;
    private Rectangle[] addAIButtons;
    private Rectangle[] crosses;
    public Menu(){
        gameversion = GameVersion.VP;
        boardtype = BoardType.STARTING;
        players = 1;
        agents = 0;
        playerColours = new String[]{"blue", "gold", "white"};

        menu.setId("MAINMENU");
        Rectangle startGame = new Rectangle(362.5, 280,715,100);
        startGame.setFill(new ImagePattern(new Image(this.getClass().getResource("startgame.png").toExternalForm())));

        Rectangle howToPlay = new Rectangle(362.5, 280+100+40,715,100);
        howToPlay.setFill(new ImagePattern(new Image(this.getClass().getResource("howtoplay.png").toExternalForm())));

        Rectangle options = new Rectangle(362.5, 280+200+80,715,100);
        options.setFill(new ImagePattern(new Image(this.getClass().getResource("options.png").toExternalForm())));

        Rectangle exit = new Rectangle(362.5, 280+300+120,715,100);

        Group menuOptions = new Group(startGame,howToPlay,options,exit);

        Rectangle gameVersionBox = new Rectangle(105,460-85-45,590,85);
        gameVersionBox.setFill(new ImagePattern(new Image(this.getClass().getResource("sec.png").toExternalForm())));

        Rectangle vpBased = new Rectangle(115,460-85-45+10,276.5,63);
        vpBased.setFill(new ImagePattern(new Image(this.getClass().getResource("vpbased.png").toExternalForm())));

        Rectangle timeBased = new Rectangle(400+10,460-85-45+10,276.5,63);
        timeBased.setFill(new ImagePattern(new Image(this.getClass().getResource("timebased.png").toExternalForm())));
        timeBased.setOpacity(0.35);

        vpBased.setOnMouseClicked(e -> {
            vpBased.setOpacity(1);
            gameversion = GameVersion.VP;
            timeBased.setOpacity(0.35);
        });

        timeBased.setOnMouseClicked(e -> {
            timeBased.setOpacity(1);
            gameversion = GameVersion.TIMED;
            vpBased.setOpacity(0.35);
            //TODO PLAYER TO SELECT LENGTH OF GAME
        });

        Rectangle boardTypeBox = new Rectangle(105+(315*2),460-85-45,590,85);
        boardTypeBox.setFill(new ImagePattern(new Image(this.getClass().getResource("sec.png").toExternalForm())));

        Rectangle starting = new Rectangle(115+(315*2),460-85-45+10,276.5,63);
        starting.setFill(new ImagePattern(new Image(this.getClass().getResource("starting.png").toExternalForm())));

        Rectangle random = new Rectangle(400+10+(315*2),460-85-45+10,276.5,63);
        random.setFill(new ImagePattern(new Image(this.getClass().getResource("random.png").toExternalForm())));
        random.setOpacity(0.35);

        starting.setOnMouseClicked(e -> {
            starting.setOpacity(1);
            boardtype = BoardType.STARTING;
            random.setOpacity(0.35);
        });

        random.setOnMouseClicked(e -> {
            random.setOpacity(1);
            boardtype = BoardType.RANDOM;
            starting.setOpacity(0.35);
        });

        Rectangle back = new Rectangle(222.5,460+270+45,350,65);
        back.setFill(new ImagePattern(new Image(this.getClass().getResource("back.png").toExternalForm())));

        Rectangle play = new Rectangle(105+(315*2)+117.5,460+270+45,350,65);
        play.setFill(new ImagePattern(new Image(this.getClass().getResource("play.png").toExternalForm())));

        Group startGameMenu = new Group(gameVersionBox,boardTypeBox,vpBased,timeBased,starting,random,back,play);

        back.setOnMouseClicked(e -> {
            menu.setId("MAINMENU");
            startGameMenu.setVisible(false);
            menuOptions.setVisible(true);
        });

        play.setOnMouseClicked(e -> {
            if (players+agents > 1 ){
                startGame();
            }
            else {
                //TODO ERROR MESSAGE
            }

        });

        Rectangle player1Box = new Rectangle(105,460,270,270);
        player1Box.setFill(new ImagePattern(new Image(this.getClass().getResource("player.png").toExternalForm())));

        Rectangle player1Icon = new Rectangle(175,530,130,130);
        player1Icon.setFill(new ImagePattern(new Image(this.getClass().getResource("redplayer.png").toExternalForm())));
        startGameMenu.getChildren().addAll(player1Box,player1Icon);

        Rectangle player2Box = new Rectangle(420,460,270,270);
        player2Box.setFill(new ImagePattern(new Image(this.getClass().getResource("player.png").toExternalForm())));
        startGameMenu.getChildren().add(player2Box);

        Rectangle[] addPlayerBoxes = new Rectangle[2];
        for (int y=0; y < 2;y++){
            Rectangle playerBox = new Rectangle(735+(315*y),460,270,270);
            playerBox.setFill(new ImagePattern(new Image(this.getClass().getResource("player.png").toExternalForm())));
            addPlayerBoxes[y] = playerBox;
            playerBox.setVisible(false);
            startGameMenu.getChildren().add(playerBox);
        }
        Rectangle[] playerIcons = new Rectangle[3];
        Rectangle[] AIIcons = new Rectangle[3];
        Rectangle[] addPlayerButtons = new Rectangle[3];
        Rectangle[] addAIButtons = new Rectangle[3];
        Rectangle[] crosses = new Rectangle[3];
        for (int y=0; y < 3;y++){
            int i = y;
            Rectangle playerIcon = new Rectangle(175+315+(315*y),530,130,130);
            playerIcon.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[y]+"player.png").toExternalForm())));
            playerIcon.setVisible(false);
            playerIcons[y] = playerIcon;

            Rectangle AIIcon = new Rectangle(175+315+(315*y),530,130,130);
            AIIcon.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[y]+"ai.png").toExternalForm())));
            AIIcon.setVisible(false);
            AIIcons[y] = AIIcon;

            Rectangle cross = new Rectangle(175+315+(130/2)-(25/2)+(315*y),660+10,25,25);
            cross.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[y]+"x.png").toExternalForm())));
            cross.setVisible(false);
            cross.setOnMouseClicked(event -> {
                if (playerIcons[i].isVisible()){
                    players = players -1;
                    playerIcons[i].setVisible(false);
                }
                if (AIIcons[i].isVisible()){
                    agents = agents -1;
                    AIIcons[i].setVisible(false);
                }
                addPlayerButtons[i].setVisible(true);
                addAIButtons[i].setVisible(true);
                crosses[i].setVisible(false);
                if (i != 0){
                    crosses[i-1].setVisible(true);
                }
                if (i != 2){
                    addPlayerButtons[i+1].setVisible(false);
                    addAIButtons[i+1].setVisible(false);
                    addPlayerBoxes[i].setVisible(false);
                }
            });
            crosses[y] = cross;

            Rectangle addPlayer = new Rectangle(455+(315*y),575.55-19.45-10,200,38.9);
            addPlayer.setFill(new ImagePattern(new Image(this.getClass().getResource("addplayer.png").toExternalForm())));
            addPlayer.setVisible(false);
            addPlayer.setOnMouseClicked(event -> {
                players = players + 1;
                addPlayerButtons[i].setVisible(false);
                addAIButtons[i].setVisible(false);
                playerIcons[i].setVisible(true);
                crosses[i].setVisible(true);
                if (i != 0){
                    crosses[i-1].setVisible(false);
                }
                if (i != 2){
                    addPlayerButtons[i+1].setVisible(true);
                    addAIButtons[i+1].setVisible(true);
                    addPlayerBoxes[i].setVisible(true);
                }
            });
            addPlayerButtons[y] = addPlayer;

            Rectangle addAI = new Rectangle(455+(315*y),575.55+19.45+10,200,38.9);
            addAI.setFill(new ImagePattern(new Image(this.getClass().getResource("addai.png").toExternalForm())));
            addAI.setVisible(false);
            addAI.setOnMouseClicked(event -> {
                agents = agents + 1;
                addPlayerButtons[i].setVisible(false);
                addAIButtons[i].setVisible(false);
                AIIcons[i].setVisible(true);
                crosses[i].setVisible(true);
                if (i != 0){
                    crosses[i-1].setVisible(false);
                }
                if (i != 2){
                    addPlayerButtons[i+1].setVisible(true);
                    addAIButtons[i+1].setVisible(true);
                    addPlayerBoxes[i].setVisible(true);
                }
            });
            addAIButtons[y] = addAI;

            startGameMenu.getChildren().addAll(playerIcon,AIIcon,cross,addPlayer,addAI);
        }

        addPlayerButtons[0].setVisible(true);
        addAIButtons[0].setVisible(true);



        menu.getChildren().add(startGameMenu);
        startGameMenu.setVisible(false);

        startGame.setOnMouseClicked(e -> {
            menu.setId("STARTGAMEMENU");
            menuOptions.setVisible(false);
            startGameMenu.setVisible(true);
        });






        Rectangle exitform = new Rectangle(0,0,1440,900);
        exitform.setFill(new ImagePattern(new Image(this.getClass().getResource("exitform.png").toExternalForm())));

        Rectangle yes = new Rectangle(521.5,485,153.5,50.5);
        yes.setFill(new ImagePattern(new Image(this.getClass().getResource("yes.png").toExternalForm())));

        Rectangle no = new Rectangle(755.5,485,153.5,50.5);
        no.setFill(new ImagePattern(new Image(this.getClass().getResource("no.png").toExternalForm())));

        Group exitMenu = new Group(exitform,yes,no);
        exitMenu.setVisible(false);

        exit.setFill(new ImagePattern(new Image(this.getClass().getResource("exit.png").toExternalForm())));
        exit.setOnMouseClicked(e -> {
            exitMenu.setVisible(true);
            exitMenu.toFront();
            yes.setOnMouseClicked(i -> {
                Platform.exit();
            });
            no.setOnMouseClicked(i -> {
                exitMenu.setVisible(false);
            });
        });

        menu.getChildren().addAll(menuOptions,exitMenu);
    }

    public void setSceneChanger(SceneChanger sceneChanger) {
        this.sceneChanger = sceneChanger;
    }

    public Scene getMenu() {
        return scene;
    }
    public Pane getMenuPane() {
        return menu;
    }

    public void startGame(){
        Game game = null; //TODO get number of players from setup screen
        try {
            game = new Game(gameversion,boardtype, players,agents); //TODO PASS THROUGH LENGTH OF GAME FOR TIMED
        } catch (URISyntaxException | IOException ex) {
            throw new RuntimeException(ex);
        }
        GUI gui = null;
        try {
            gui = new GUI(game);
        } catch (URISyntaxException | IOException ex) {
            throw new RuntimeException(ex);
        }
        game.setGUI(gui);
        Pane newScenePane = gui.getGUI();
        sceneChanger.changeScene(newScenePane);
    }

}


