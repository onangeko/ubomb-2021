/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go;


import fr.ubx.poo.ubomb.go.character.Player;

public interface Takeable {
    void takenBy(Player player);
}
