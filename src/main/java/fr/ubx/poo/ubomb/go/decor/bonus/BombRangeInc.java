package fr.ubx.poo.ubomb.go.decor.bonus;

import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;

import fr.ubx.poo.ubomb.go.character.Player;


public class BombRangeInc extends Bonus {


    public BombRangeInc(Game game, Position position) {
        super(game, position);
    }

    public BombRangeInc(Position position) {
        super(position);
    }

    @Override
    public void takenBy(Player player) {
        player.addBombRange();
        this.remove();
    }
}
