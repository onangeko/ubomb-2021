package fr.ubx.poo.ubomb.go.decor;

import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.character.Player;

public class Door extends Decor{


    public Door(Game game, Position position) {
        super(game, position);
    }

    public Door(Position position) {
        super(position);
    }




}
