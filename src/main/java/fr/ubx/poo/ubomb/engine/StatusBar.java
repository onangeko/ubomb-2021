/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.engine;

import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.view.ImageResource;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class StatusBar {
    public static final int height = 55;
    private final Game game;
    private final DropShadow ds = new DropShadow();
    private final HBox hBox = new HBox();
    private final Text lives = new Text();
    private final Text availableBombs = new Text();
    private final Text bombRange = new Text();
    private final Text keys = new Text();
    private final HBox level = new HBox();
    private final int gameLevel = 1;


    public StatusBar(Group root, int sceneWidth, int sceneHeight, Game game) {
        // Status bar
        this.game = game;

        level.getStyleClass().add("level");
        level.getChildren().add(new ImageView(ImageResource.getDigit(gameLevel)));

        ds.setRadius(5.0);
        ds.setOffsetX(3.0);
        ds.setOffsetY(3.0);
        ds.setColor(Color.color(0.5f, 0.5f, 0.5f));


        HBox status = new HBox();
        status.getStyleClass().add("status");
        HBox live = statusGroup(ImageResource.HEART.getImage(), this.lives);
        HBox bombs = statusGroup(ImageResource.BANNER_BOMB.getImage(), availableBombs);
        HBox range = statusGroup(ImageResource.BANNER_RANGE.getImage(), bombRange);
        HBox key = statusGroup(ImageResource.KEY.getImage(), keys);
        status.setSpacing(40.0);
        status.getChildren().addAll(live, bombs, range, key);

        hBox.getChildren().addAll(level, status);
        hBox.getStyleClass().add("statusBar");
        hBox.relocate(0, sceneHeight);
        hBox.setPrefSize(sceneWidth, height);
        root.getChildren().add(hBox);
    }

    private void updateLevel(int n) {
        if (n != gameLevel) {
            level.getChildren().clear();
            level.getChildren().add(new ImageView(ImageResource.getDigit(n)));
        }
    }

    private HBox statusGroup(Image kind, Text number) {
        HBox group = new HBox();
        ImageView img = new ImageView(kind);
        group.setSpacing(4);
        number.setEffect(ds);
        number.setCache(true);
        number.setFill(Color.BLACK);
        number.getStyleClass().add("number");
        group.getChildren().addAll(img, number);
        return group;
    }

    public void update(Game game) {
        updateLevel(gameLevel);
        lives.setText(Integer.toString(game.getPlayer().getLives()));
        bombRange.setText(Integer.toString(game.getPlayer().getBombRange()));
        availableBombs.setText(Integer.toString(game.getPlayer().getBomb()));
        keys.setText(Integer.toString(game.getPlayer().getKey()));
    }

}
