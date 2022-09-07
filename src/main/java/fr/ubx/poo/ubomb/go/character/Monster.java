package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;

import fr.ubx.poo.ubomb.go.Movable;
import fr.ubx.poo.ubomb.go.decor.Bomb;
import fr.ubx.poo.ubomb.go.decor.Decor;


public class Monster extends Decor implements Movable {

    public int getLives() {
        return lives;
    }

    private int lives = 1;

    public Monster(Game game, Position position) {
        super(game, position);
    }




    @Override
    public boolean isWalkable(Player player){return true;}

    @Override
    public boolean canMove(Direction direction) {
        Position nextPos = direction.nextPosition(this.getPosition());
        if ( (nextPos.getX() < 0 || nextPos.getY() <0 ) || (nextPos.getX() > game.getGrid().getWidth() - 1|| nextPos.getY()> game.getGrid().getHeight() - 1) ){
            return false;
        }
        if (nextPos.getX() < getPosition().getX() - 1 || nextPos.getX() > getPosition().getX() + 1 || nextPos.getY() < getPosition().getY() - 1 || nextPos.getY() > getPosition().getY() + 1){
            return false;
        }

        return game.getGrid().get(nextPos) == null;

    }

    @Override
    public void doMove(Direction direction) {
        Position nextPos = direction.nextPosition(this.getPosition());
        game.getGrid().remove(this.getPosition());
        game.getGrid().set(nextPos,this);
        this.setPosition(nextPos);

    }

    public void monsterTakeDamage(){
        this.lives= lives - 1;
    }



}

/// Gestion des monstres ///
/*
//UNDONE//

6 - La velocité des monstres doit augmenter de 5 points par niveau. [ ?]
//DONE//
1 - Les déplacements des monstres sont entièrement aléatoires. [V ]
2 - Une collision avec un monstre déclenche la perte d’une vie pour le joueur. [V]
4 - Les monstres ne peuvent pas ramasser les bonus qui se trouvent sur le sol. [V]
5 - Les monstres ont peur des portes et ne peuvent pas les franchir. [V]
//LOCKED//
3 - La seule manière de blesser un monstre est d’utiliser une bombe. [voir Gestion des Bombes]
 */


//monstres détruisent bonus = takenBy ?
//monstres bombe centre = remove ?
