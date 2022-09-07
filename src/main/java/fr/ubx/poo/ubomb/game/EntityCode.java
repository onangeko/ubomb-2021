/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.game;


public enum EntityCode {
    Empty('_'),
    Box('B'),
    Heart('H'),
    Key('K'),
    Monster('M'),
    DoorPrevOpened('V'),
    DoorNextOpened('N'),
    DoorNextClosed('n'),
    Stone('S'),
    Tree('T'),
    Princess('W'),
    BombRangeInc('>'),
    BombRangeDec('<'),
    BombNumberInc('+'),
    BombNumberDec('-'),;


    private final char code;

    EntityCode(char code) {
        this.code = code;
    }

    public static EntityCode fromCode(char c) {
        for (EntityCode entity : values()) {
            if (entity.getCode() == c)
                return entity;
        }
        throw new GridException("Invalid character " + c);
    }

    private char getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "" + code;
    }
}
