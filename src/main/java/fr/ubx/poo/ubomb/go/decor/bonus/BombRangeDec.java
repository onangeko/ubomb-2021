package fr.ubx.poo.ubomb.go.decor.bonus;

import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.character.Player;


public class BombRangeDec extends Bonus {


    public BombRangeDec(Game game, Position position) {
        super(game, position);
    }

    public BombRangeDec(Position position) {
        super(position);
    }

    @Override
    public void takenBy(Player player) {
        player.substractBombRange();
        if ( player.getBombRange() ==0){
            player.addBombRange();

        }
        this.remove();

    }
}
