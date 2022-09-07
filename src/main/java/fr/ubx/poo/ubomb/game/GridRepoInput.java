package fr.ubx.poo.ubomb.game;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public interface GridRepoInput {
    Grid load(Reader in) throws IOException;
}


