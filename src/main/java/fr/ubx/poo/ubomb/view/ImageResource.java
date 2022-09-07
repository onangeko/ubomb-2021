package fr.ubx.poo.ubomb.view;

import fr.ubx.poo.ubomb.game.Direction;
import javafx.scene.image.Image;

import java.util.Objects;

public enum ImageResource {
    // Decor elements
    STONE("stone.png"),
    TREE("tree.png"),
    BOX("box.png"),
    DOOR_OPENED("door_opened.png"),
    DOOR_CLOSED("door_closed.png"),




    // Bonus
    HEART("heart.png"),
    KEY("key.png"),

    BONUS_BOMB_NB_INC("bonus_bomb_nb_inc.png"),
    BONUS_BOMB_NB_DEC("bonus_bomb_nb_dec.png"),
    BONUS_BOMB_RANGE_INC("bonus_bomb_range_inc.png"),
    BONUS_BOMB_RANGE_DEC("bonus_bomb_range_dec.png"),

    // Player, princess and monsters
    PLAYER_UP("player_up.png"),
    PLAYER_RIGHT("player_right.png"),
    PLAYER_DOWN("player_down.png"),
    PLAYER_LEFT("player_left.png"),

    MONSTER_UP("monster_up.png"),
    MONSTER_RIGHT("monster_right.png"),
    MONSTER_DOWN("monster_down.png"),
    MONSTER_LEFT("monster_left.png"),

    PRINCESS("princess.png"),

    // Bombs
    BOMB_0("bomb_0.png"),
    BOMB_1("bomb_1.png"),
    BOMB_2("bomb_2.png"),
    BOMB_3("bomb_3.png"),
    EXPLOSION("explosion.png"),

    // Status bar
    BANNER_BOMB("banner_bomb.png"),
    BANNER_RANGE("banner_range.png"),

    DIGIT_0("banner_0.jpg"),
    DIGIT_1("banner_1.jpg"),
    DIGIT_2("banner_2.jpg"),
    DIGIT_3("banner_3.jpg"),
    DIGIT_4("banner_4.jpg"),
    DIGIT_5("banner_5.jpg"),
    DIGIT_6("banner_6.jpg"),
    DIGIT_7("banner_7.jpg"),
    DIGIT_8("banner_8.jpg"),
    DIGIT_9("banner_9.jpg"),
    ;

    private final Image image;

    ImageResource(String file) {
        this.image = new Image(Objects.requireNonNull(ImageResource.class.getResourceAsStream("/images/" + file)));
    }

    public Image getImage() {
        return image;
    }

    public static Image getDigit(int i) {
        if (i < 0 || i > 9)
            throw new IllegalArgumentException();
        return valueOf("DIGIT_"+i).image;
    }

    public static Image getPlayer(Direction direction) {
        return valueOf("PLAYER_" + direction).image;
    }

    public static Image getMonster(Direction direction) {
        return valueOf("MONSTER_" + direction).image;
    }

    public static Image getBomb(int i) {
        if (i < 0 || i > 3)
            throw new IllegalArgumentException();
        return valueOf("BOMB_"+i).image;
    }


}
