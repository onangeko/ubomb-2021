package fr.ubx.poo.ubomb.game;

import fr.ubx.poo.ubomb.go.decor.Decor;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

public class Grid {

    private final int width;
    private final int height;

    private final Map<Position, Decor> elements;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.elements = new Hashtable<>();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Decor get(Position position) {
        return elements.get(position);
    }




    public void set(Position position, Decor decor) {
        if (decor != null)
            elements.put(position, decor);
    }

    public void remove(Position position) {
        elements.remove(position);
    }

    public Collection<Decor> values() {
        return elements.values();
    }

}
