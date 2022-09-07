package fr.ubx.poo.ubomb.go.decor;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.go.decor.bonus.Bonus;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Bonus {


    public Bomb(Game game, Position position) {
        super(game, position);
    }

    public Bomb(Position position) {
        super(position);
    }


    @Override
    public void takenBy(Player player) {

    }



}

//bomb
