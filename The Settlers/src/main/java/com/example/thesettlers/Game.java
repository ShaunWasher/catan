package com.example.thesettlers;

import com.example.thesettlers.enums.DevelopmentCardType;
import com.example.thesettlers.enums.GameState;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

public class Game {
    private GameState gameState;
    private ArrayList<Player> players;
    private int longestRoad;
    private int largestArmy;
    private Queue<DevelopmentCard> developmentCards;
    public Game(GameBoard gameBoard){
        gameState = GameState.START;
        longestRoad = 0;
        largestArmy = 0;
        for(int i = 0;i<4;i++) {
            players.add(new Player(i + 1));
        }
        ArrayList<DevelopmentCard> cards = new ArrayList<>();
        for(int i = 0;i<14;i++) {
            cards.add(new DevelopmentCard(DevelopmentCardType.KNIGHT));
        }
        for(int i = 0;i<5;i++) {
            cards.add(new DevelopmentCard(DevelopmentCardType.VP));
        }
        for(int i = 0;i<2;i++) {
            cards.add(new DevelopmentCard(DevelopmentCardType.ROADBUILDING));
        }
        for(int i = 0;i<2;i++) {
            cards.add(new DevelopmentCard(DevelopmentCardType.YEAROFPLENTY));
        }
        for(int i = 0;i<2;i++) {
            cards.add(new DevelopmentCard(DevelopmentCardType.MONOPOLY));
        }
        Random rand = new Random();
        for(int i = 0;i<25;i++) {
            developmentCards.add(cards.get(rand.nextInt(25-i)));
        }

    }

}
