package fr.ubx.poo.ubomb.go.decor.bonus;

import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.Takeable;
import fr.ubx.poo.ubomb.go.character.Player;

public class Heart extends Bonus {


    public Heart(Game game, Position position) {
        super(game, position);
    }

    public Heart(Position position) {
        super(position);
    }

    @Override
    public void takenBy(Player player) {
        player.takeLives();
        this.remove();
    }



}
