/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.view;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Sprite {

    public static final int size = 40;
    private final Pane layer;
    private final GameObject gameObject;
    private ImageView imageView;
    private Image image;
    private Effect effect;

    public Sprite(Pane layer, Image image, GameObject gameObject) {
        this.layer = layer;
        this.image = image;
        this.gameObject = gameObject;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public final void setImage(Image image, Effect effect) {
        if (this.image == null || this.image != image || this.effect != effect) {
            this.image = image;
            this.effect = effect;
        }
    }

    public final void setImage(Image image) {
        setImage(image, null);
    }

    public void updateImage() {

    }

    public void updateImage(Sprite sprite) {
        this.image= sprite.image;
    }

    public Position getPosition() {
        return getGameObject().getPosition();
    }

    public final void render() {
        if (gameObject.isModified()) {
            if (imageView != null) {
                remove();
            }
            updateImage();
            imageView = new ImageView(this.image);
            imageView.setEffect(effect);
            imageView.setX(getPosition().getX() * size);
            imageView.setY(getPosition().getY() * size);
            layer.getChildren().add(imageView);
            gameObject.setModified(false);
        }
    }

    public final void remove() {
        layer.getChildren().remove(imageView);
        imageView = null;
    }
}
