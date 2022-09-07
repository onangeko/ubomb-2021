package fr.ubx.poo.ubomb.go.decor;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.Movable;
import javafx.scene.layout.Pane;

public class Box extends Decor implements Movable {



    public Box(Game game, Position position) {
        super(game, position);
    }

    public Box(Position position) {
        super(position);
    }

    @Override
    public boolean canMove(Direction direction) {
        Position nextPos = direction.nextPosition(this.getPosition());
        if ( (nextPos.getX() <0 || nextPos.getY() <0 ) || (nextPos.getX() > game.getGrid().getWidth() - 1|| nextPos.getY()> game.getGrid().getHeight() - 1) ){
            return false;
        }
        if (game.getGrid().get(nextPos) == null ){
            return true;
        }
        return false;
    }

    @Override
    public void doMove(Direction direction) {
        game.getGrid().remove(this.getPosition());
        Position nextPos = direction.nextPosition(this.getPosition());
        game.getGrid().set(nextPos,this);
        this.setPosition(nextPos);



















    }


}
