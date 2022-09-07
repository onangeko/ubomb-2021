package fr.ubx.poo.ubomb.game;

import java.io.IOException;
import java.io.Reader;




public class GridRepoFile extends GridRepo implements GridRepoInput {


    GridRepoFile(Game game) {
        super(game);
    }

    @Override
    public Grid load(int level, String name) {
        return null;
    }

    @Override
    public Grid load(String name) {
        String[] tab;
        tab = name.split("\n");
        int width = tab[0].length();
        int height = tab.length;
        Grid grille = new Grid(width, height);
        for (int i = 0;i<width ;i++){
            for (int j = 0 ; j<height ;j++) {
                Position pos = new Position(i,j);
                try {
                    grille.set(pos,processEntityCode(EntityCode.fromCode(tab[j].charAt(i)),pos));
                } catch (GridException e) {
                    e.printStackTrace();
                }
            }
        }
        return grille;
    }


    public Grid load(Reader in) throws IOException {
        int theCharNum = in.read();
        StringBuilder str = new StringBuilder();
        while (theCharNum!= -1) {
            char theChar = (char) theCharNum;
            str.append(theChar);
            theCharNum = in.read();
        }
        in.close();
        return load(str.toString());
    }



}
