package fr.ubx.poo.ubomb.go.decor.bonus;

import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;

import fr.ubx.poo.ubomb.go.character.Player;


public class BombNumberDec extends Bonus {


    public BombNumberDec(Game game, Position position) {
        super(game, position);
    }

    public BombNumberDec(Position position) {
        super(position);
    }

    @Override
    public void takenBy(Player player) {
        player.susbtractBomb();
        this.remove();

    }
}
