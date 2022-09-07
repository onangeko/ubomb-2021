/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb;

import fr.ubx.poo.ubomb.engine.GameEngine;
import fr.ubx.poo.ubomb.game.Game;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        String path = getClass().getResource("/sample").getFile();
        Game game = new Game(path);
        GameEngine engine = new GameEngine("UBomb 2021 Student", game, stage);
        engine.start();
    }

}
