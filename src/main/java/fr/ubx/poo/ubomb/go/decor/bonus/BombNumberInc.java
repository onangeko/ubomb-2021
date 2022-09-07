package fr.ubx.poo.ubomb.go.decor.bonus;

import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;

import fr.ubx.poo.ubomb.go.character.Player;


public class BombNumberInc extends Bonus {


    public BombNumberInc(Game game, Position position) {
        super(game, position);
    }

    public BombNumberInc(Position position) {
        super(position);
    }

    @Override
    public void takenBy(Player player) {
       player.addBomb();
       this.remove();
    }
}
