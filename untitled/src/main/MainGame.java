package main;

import models.Game;
import services.GameService;

public class MainGame {

    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();
        GameService gameService = new GameService();
        gameService.init(game);
    }
}
