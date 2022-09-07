/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.decor.bonus;

import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.Takeable;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.go.decor.Decor;

public abstract class Bonus extends Decor implements Takeable {


    public Bonus(Game game, Position position) {
        super(game, position);
    }

    public Bonus(Position position) {
        super(position);
    }

    @Override
    public boolean isWalkable(Player player) {
        return true;
    }

    @Override
    public void explode() {
        remove();
    }
}
