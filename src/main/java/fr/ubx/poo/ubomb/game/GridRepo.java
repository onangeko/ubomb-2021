package fr.ubx.poo.ubomb.game;

import fr.ubx.poo.ubomb.go.character.Monster;
import fr.ubx.poo.ubomb.go.decor.Princess;
import fr.ubx.poo.ubomb.go.decor.*;
import fr.ubx.poo.ubomb.go.decor.bonus.*;


public abstract class GridRepo {

    private final Game game;

    GridRepo(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public abstract Grid load(int level, String name);
    public abstract Grid load(String name);

    Decor processEntityCode(EntityCode entityCode, Position pos) {
        switch (entityCode) {
            case Stone:
                return new Stone(game,pos);
            case Tree:
                return new Tree(game,pos);
            case Key:
                return new Key(game,pos);
            case Heart:
                return  new Heart(game,pos);
            case Box:
                return new Box(game,pos);
            case Monster:
                return new Monster(game,pos);
            case Princess:
                return new Princess(game,pos);
            case BombRangeDec:
                return new BombRangeDec(game,pos);
            case BombRangeInc:
                return new BombRangeInc(game,pos);
            case BombNumberDec:
                return new BombNumberDec(game,pos);
            case BombNumberInc:
                return new BombNumberInc(game,pos);
            case DoorNextClosed:
                return new DoorNextClosed(game,pos);
            case DoorNextOpened:
                return new DoorNextOpened(game,pos);
            case DoorPrevOpened:
                return new DoorPrevOpened(game,pos);
            case Empty:
                default:
                return null;
                // throw new RuntimeException("EntityCode " + entityCode.name() + " not processed");
        }
    }
}
