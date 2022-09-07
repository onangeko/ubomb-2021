package fr.ubx.poo.ubomb.go.decor;

import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;

public class DoorNextClosed extends Door{


    public DoorNextClosed(Game game, Position position) {
        super(game, position);
    }

    public DoorNextClosed(Position position) {
        super(position);
    }
}
