/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go;

import fr.ubx.poo.ubomb.game.Direction;

public interface Movable {
    boolean canMove(Direction direction);
    void doMove(Direction direction);
}
