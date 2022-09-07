/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.game;


import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.character.Player;


import java.io.*;
import java.util.*;

public class Game {

    public int bombBagCapacity;
    public int actualLevel =0;

    public int getMonsterVelocity() {
        return monsterVelocity;
    }

    public final int monsterVelocity;
    public final int playerLives;
    public final int levels;
    public final long playerInvisibilityTime;
    public final long monsterInvisibilityTime;
    public List <Grid> GridList;
    private Grid grid;
    private final Player player;


    public Game(String worldPath) {
        try (InputStream input = new FileInputStream(new File(worldPath, "config.properties"))) {
            Properties prop = new Properties();
            // load the configuration file
            prop.load(input);
            bombBagCapacity = Integer.parseInt(prop.getProperty("bombBagCapacity", "3"));
            monsterVelocity = Integer.parseInt(prop.getProperty("monsterVelocity", "10"));
            levels = Integer.parseInt(prop.getProperty("levels", "1"));
            playerLives = Integer.parseInt(prop.getProperty("playerLives", "3"));
            playerInvisibilityTime = Long.parseLong(prop.getProperty("playerInvisibilityTime", "4000"));
            monsterInvisibilityTime = Long.parseLong(prop.getProperty("monsterInvisibilityTime", "1000"));


            // Load the world
            Levels niveaux = new Levels();
            GridList = niveaux.Level(this);
            this.grid = GridList.get(actualLevel);
            // Create the player
            String[] tokens = prop.getProperty("player").split("[ :x]+");
            if (tokens.length != 2)
                throw new RuntimeException("Invalid configuration format");
            Position playerPosition = new Position(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
            player = new Player(this, playerPosition, playerLives);

        } catch (IOException ex) {
            System.err.println("Error loading configuration");
            throw new RuntimeException("Invalid configuration format");
        }
    }

    public Grid setGrid(Grid grid) {
        this.grid = grid;
        return grid;
    }

    public Grid getGrid() {
        return grid;
    }
    public Grid getGridList(int i) {
        return this.GridList.get(i);
    }


    // Returns the player, monsters and bombs at a given position
    public List<GameObject> getGameObjects(Position position) {
        List<GameObject> gos = new LinkedList<>();
        if (getPlayer().getPosition().equals(position))
            gos.add(player);
        return gos;
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean inside(Position position) {
        return true;
    }

}
