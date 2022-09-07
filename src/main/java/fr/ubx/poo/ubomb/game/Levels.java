package fr.ubx.poo.ubomb.game;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


public class Levels {
    String worldPath = getClass().getResource("/sample").getFile();
    String prefix = "level";
    List <Grid> GridList = new ArrayList();

    public List <Grid> Level(Game game) throws FileNotFoundException {
        for (int i = 0 ; i < game.levels;i++){
           Reader in = new FileReader(worldPath +"/" + prefix + (i+1) +".txt");
           GridRepoFile gridRepo = new GridRepoFile(game);
            try {
                GridList.add(i,gridRepo.load(in));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return GridList;
    }
}
