/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.Movable;
import fr.ubx.poo.ubomb.go.decor.*;
import fr.ubx.poo.ubomb.go.decor.bonus.*;


public class Player extends GameObject implements Movable {

    private Direction direction;
    private boolean moveRequested = false;


    private long lastInvicibility= 0L;
    private static final long invicibilityTime= 1000L;
    private int bombRange = 1;
    private int bomb = game.bombBagCapacity;
    private int lives;
    private int key = 0;

    public boolean isOnDoor() {
        return onDoor;
    }

    public void setOnDoor(boolean onDoor) {
        this.onDoor = onDoor;
    }

    private boolean onDoor = false;



    public Player(Game game, Position position, int lives) {
        super(game, position);
        this.direction = Direction.DOWN;
        this.lives = lives;
    }

    public int getLives() {
        return lives;
    }

    public int getKey(){
        return key;
    }

    public int getBomb(){
        return bomb;
    }


    public  int getBombRange(){
        return bombRange;
    }

    public Direction getDirection() {
        return direction;
    }

    public void requestMove(Direction direction) {
        if (direction != this.direction) {
            this.direction = direction;
            setModified(true);
        }
        moveRequested = true;
    }   

    public final boolean canMove(Direction direction) {
       Position nextPos = direction.nextPosition(this.getPosition());
       if ( (nextPos.getX() <0 || nextPos.getY() <0 ) || (nextPos.getX() > game.getGrid().getWidth() - 1|| nextPos.getY()> game.getGrid().getHeight() - 1) ){
           return false;
       }
       if (game.getGrid().get(nextPos) == null || game.getGrid().get(nextPos).isWalkable(this)){
            return true;
       }
       if (game.getGrid().get(nextPos)instanceof Box){
           Box box = (Box) game.getGrid().get(direction.nextPosition(this.getPosition()));
           if (box.canMove(this.direction)) {
               box.doMove(this.direction);
               return true;
           }
           return false;
       }
       return false;
    }

    public void update(long now) {
        if (moveRequested) {
            if (canMove(direction)) {
                doMove(direction);
            }
        }
        moveRequested = false;
    }

    public void doMove(Direction direction) {
        // Check if we need to pick something up
        Position nextPos = direction.nextPosition(getPosition());
        if (game.getGrid().get(nextPos) instanceof Bonus) {
            ((Bonus) game.getGrid().get(nextPos)).takenBy(this);
        }
        if (game.getGrid().get(nextPos) instanceof DoorNextOpened){
            this.takeDoor(game.actualLevel + 1);

            onDoor = true;
            System.out.println("true");
        }
        if (game.getGrid().get(nextPos) instanceof DoorPrevOpened){
            this.takeDoor(game.actualLevel - 1 );

            onDoor = true;
            System.out.println("true");
        }
        setPosition(nextPos);

    }


    @Override
    public boolean isWalkable(Player player) {
        return false;
    }

    @Override
    public void explode() {
    }

    // Example of methods to define by the player
    public void takeDoor(int gotoLevel) {

    }

    public DoorNextOpened openDoor(Position nextPos){
        this.key = key - 1 ;
        game.getGrid().remove(this.getPosition());
        DoorNextOpened newDoor= new DoorNextOpened(game,nextPos);
        game.getGrid().set(nextPos,newDoor);
        newDoor.setPosition(nextPos);
        return newDoor;


    }

    public Bomb setBomb(Position pos){
        susbtractBomb();
        Bomb newBomb = new Bomb(game,pos);
        game.getGrid().set(pos,newBomb);
        newBomb.setPosition(pos);
        return newBomb;


    }

    public void enableInvicibility() {
        lastInvicibility = System.currentTimeMillis();
    }

    public boolean invicibilityEnabled() {
        return System.currentTimeMillis() - lastInvicibility < invicibilityTime;
    }

    public void takeKey() {
        this.key = key + 1;
    }

    public void takeLives() {
        this.lives = lives + 1 ;
    }

    public void addBombRange() {
        this.bombRange = bombRange + 1;
    }

    public void addBomb(){this.bomb = bomb + 1;
    }

    public void takeDamage(){
        this.lives = lives -1;
    }

    public void substractBombRange(){
        this.bombRange = bombRange - 1 ;
    }

    public void susbtractBomb(){
        this.bomb = bomb - 1;
        if (this.bomb < 0){
            this.bomb = 0;
        }
    }

    public boolean isWinner() {
        return game.getGrid().get(this.getPosition()) instanceof Princess;
    }
}
