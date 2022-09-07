package fr.ubx.poo.ubomb.game;


import java.lang.reflect.Field;

import static fr.ubx.poo.ubomb.game.EntityCode.*;

public class GridRepoSample extends GridRepo {

    private final EntityCode[][] level1 = {
            {Stone, Heart, Heart, Empty, BombNumberDec, Empty, Empty, Empty, Empty, Empty, BombRangeDec, Heart},
            {Empty, Stone, Stone, Empty, Stone, Empty, Stone, Stone, Stone, Stone, Empty, Empty},
            {Empty, Empty, Empty, Empty, Stone, Box, Stone, Empty, Empty, Stone, Empty, Empty},
            {Monster, Empty, Empty, Empty, Stone, Box, Stone, Empty, Empty, Stone, Empty, Empty},
            {Empty, Box, Empty, Empty, Stone, Stone, Stone, Empty, Empty, Empty, Empty, Empty},
            {Empty, Empty, Empty, Empty, Empty, Box, Empty, Key, Empty, Stone, Empty, Empty},
            {Empty, Tree, Empty, Tree, Empty, Empty, Empty, Empty, Empty, Stone, Empty, Empty},
            {Empty, Empty, Box, Tree, Empty, Empty, Empty, Empty, Empty, Stone, DoorNextClosed, Empty},
            {Empty, Tree, Tree, Tree, Empty, Empty, Empty, Empty, Empty, Stone, Empty, Empty},
            {Empty, Empty, Empty, Empty, Empty, Empty, BombRangeInc, Empty, Empty, Empty, Empty, Empty},
            {Stone, Stone, Stone, Stone, Stone, Empty, Box, Box, Stone, Stone, Box, Stone},
            {Empty, Heart, Empty, Empty, Box, Empty, Empty, Empty, Empty, Empty, Empty, Empty},
            {Monster, Empty, Empty, Empty, Box, Empty, Empty, Empty, BombNumberInc, Monster, Empty, Princess}
    };

    public GridRepoSample(Game game) {
        super(game);
    }

    @Override
    public final Grid load(int level,String name) {
        EntityCode[][] entities = getEntities(name);
        if (entities == null)
            return null;
        int width = entities[0].length;
        int height = entities.length;
        Grid grid = new Grid(width, height);

        for (int i = 0; i <= width; i++) {
            for (int j = 0; j <= height; j++) {
                Position position = new Position(i, j);
                EntityCode entityCode = entities[j][i];
                grid.set(position, processEntityCode(entityCode, position));
            }
        }
        return grid;
    }

    @Override
    public Grid load(String name) {
        return null;
    }

    private EntityCode[][] getEntities(String name) {
        try {
            Field field = this.getClass().getDeclaredField(name);
            return (EntityCode[][]) field.get(this);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return null;
        }
    }
}
