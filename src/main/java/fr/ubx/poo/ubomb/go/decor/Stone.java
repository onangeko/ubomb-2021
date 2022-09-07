/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.decor;

import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;

public class Stone extends Decor {


    public Stone(Game game, Position position) {
        super(game, position);
    }

    public Stone(Position position) {
        super(position);
    }
}
