package fr.ubx.poo.ubomb.go.decor;

import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.go.decor.Decor;

public class Princess extends Decor {


    public Princess(Game game, Position position) {
        super(game, position);
    }

    public Princess(Position position) {
        super(position);
    }

    @Override
    public boolean isWalkable(Player player){return true;}


}


