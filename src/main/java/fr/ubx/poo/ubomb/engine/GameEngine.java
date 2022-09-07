/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.engine;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.character.Monster;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.go.decor.*;
import fr.ubx.poo.ubomb.go.decor.bonus.Key;
import fr.ubx.poo.ubomb.view.*;
import static fr.ubx.poo.ubomb.view.ImageResource.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.*;
import java.util.Timer;


public final class GameEngine {

    private static AnimationTimer gameLoop;
    private final String windowTitle;
    private final Game game;
    private final Player player;
    private final List<Sprite> sprites = new LinkedList<>();
    private final Set<Sprite> cleanUpSprites = new HashSet<>();
    private final Stage stage;
    private StatusBar statusBar;
    private Pane layer;
    private Input input;



    public GameEngine(final String windowTitle, Game game, final Stage stage) {
        this.stage = stage;
        this.windowTitle = windowTitle;
        this.game = game;
        this.player = game.getPlayer();
        initialize();
        buildAndSetGameLoop();
    }

    private void initialize() {
        Group root = new Group();
        layer = new Pane();

        int height = game.getGrid().getHeight();
        int width = game.getGrid().getWidth();
        int sceneWidth = width * Sprite.size;
        int sceneHeight = height * Sprite.size;
        Scene scene = new Scene(root, sceneWidth, sceneHeight + StatusBar.height);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/application.css")).toExternalForm());

        stage.setTitle(windowTitle);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();

        input = new Input(scene);
        root.getChildren().add(layer);
        statusBar = new StatusBar(root, sceneWidth, sceneHeight, game);

        // Create sprites
        for (Decor decor : game.getGrid().values()) {
            sprites.add(SpriteFactory.create(layer, decor));
            decor.setModified(true);
        }
        sprites.add(new SpritePlayer(layer, player));
    }

    void buildAndSetGameLoop() {
        gameLoop = new AnimationTimer() {

            long lastUpdate = 0;
            public void handle(long now) {
                // Check keyboard actions
                processInput(now);

                // Do actions
                update(now);

                if (now - lastUpdate >= 5_000_000_000L / game.getMonsterVelocity() + (game.actualLevel - 1)*5) {
                    moveMonsters(now);
                    lastUpdate = now ;
                }
                checkCollision(now);
                changeLevel(now);
                // Graphic update
                cleanupSprites();
                render();
                statusBar.update(game);
            }
        };
    }

    private void bomba() {
        Bomb bomb = player.setBomb(player.getPosition());
        final Sprite[] bombState = new Sprite[5];
        Position pos = bomb.getPosition();
        Timer chrono = new Timer();
        chrono.schedule(new TimerTask() {
            private int cycle = 4;
            @Override
            public void run() {
                switch (cycle) {
                    case 4:
                        bombState[0] = new Sprite(layer, BOMB_3.getImage(), bomb);
                        sprites.add(bombState[0]);
                        System.out.println(0);
                        break;
                    case 3:
                        bombState[0].getGameObject().remove();
                        game.getGrid().remove(bomb.getPosition());
                        bombState[1] = new Sprite(layer, BOMB_2.getImage(), new Bomb(game, pos));
                        sprites.add(bombState[1]);
                        System.out.println(1);
                        break;
                    case 2:
                        bombState[1].getGameObject().remove();
                        game.getGrid().remove(bomb.getPosition());
                        bombState[2] = new Sprite(layer, BOMB_1.getImage(), new Bomb(game, pos));
                        sprites.add(bombState[2]);
                        System.out.println(2);
                        break;
                    case 1:
                        bombState[2].getGameObject().remove();
                        game.getGrid().remove(bomb.getPosition());
                        bombState[3] = new Sprite(layer, BOMB_0.getImage(), new Bomb(game, pos));
                        sprites.add(bombState[3]);
                        System.out.println(3);
                        break;
                    case 0:
                        bombState[3].getGameObject().remove();
                        game.getGrid().remove(bomb.getPosition());
                        System.out.println(4);
                        bombExplode(pos);
                        System.out.print("BOOM");
                        player.addBomb();
                        break;
                }
                cycle--;
            }
        }, 0, 1000);

    }

    private void bombExplode(Position pos) {
        explodeCenter(pos);
        explodeUp(pos);
        explodeLeft(pos);
        explodeRight(pos);
        explodeDown(pos);
    }



    public void explodeCenter(Position pos){
        final Sprite[] explosionRange = new Sprite[1];
        Timer chrono = new Timer();
        chrono.schedule(new TimerTask() {
            private int cycle = 2;
            @Override
            public void run() {
                switch (cycle) {
                    case 2:
                        if (canBeExploded(pos)) {
                            explosionRange[0] = new Sprite(layer, EXPLOSION.getImage(), new Explosion(game, pos));
                            sprites.add(explosionRange[0]);
                        }
                        break;
                    case 1:
                        if (pos.equals(player.getPosition())){
                            if (!(player.invicibilityEnabled())){
                                player.takeDamage();
                                player.enableInvicibility();
                            }
                        }
                        if (game.getGrid().get(pos) == null){
                            explosionRange[0].getGameObject().remove();
                            break;
                        }
                        if (explosionRange[0] != null){
                            game.getGrid().get(pos).remove();
                            explosionRange[0].getGameObject().remove();
                            game.getGrid().remove(pos);
                        }
                        break;
                }
                cycle--;
            }
        },0,1000);
    }

    public void explodeUp(Position pos) {
        Timer chrono = new Timer();
        final Sprite[] explosionRange = new Sprite[player.getBombRange()];
        chrono.schedule(new TimerTask() {
            private int cycle = 2;
            @Override
            public void run() {
                switch (cycle) {
                    case 2:
                        for (int i = 1; i <= player.getBombRange(); i++) {
                            int x = pos.getX();
                            int y = pos.getY();
                            Position nextPos = new Position(x, y - i);
                            Position nextNextPos = new Position(x,y - 2);
                            if (canBeExploded(nextPos)) {
                                explosionRange[i - 1] = new Sprite(layer, EXPLOSION.getImage(), new Explosion(game, nextPos));
                                sprites.add(explosionRange[i - 1]);
                                if (game.getGrid().get(nextPos) != null && game.getGrid().get(nextNextPos) != null){
                                    break;
                                }
                                if (game.getGrid().get(nextPos) != null && game.getGrid().get(nextNextPos) == null){
                                    break;}
                            }
                        }
                        break;
                    case 1:
                        for (int i = 0;i<explosionRange.length;i++){
                            int x = pos.getX();
                            int y = pos.getY();
                            Position nextPos = new Position(x, y-i-1);
                            if (nextPos.equals(game.getPlayer().getPosition())){
                                if (!(player.invicibilityEnabled())){
                                    player.takeDamage();
                                    player.enableInvicibility();
                                }
                            }
                            if (game.getGrid().get(nextPos) == null){
                                explosionRange[i].getGameObject().remove();
                                continue;
                            }
                            if (explosionRange[i] != null){
                                game.getGrid().get(nextPos).remove();
                                game.getGrid().remove(nextPos);
                                explosionRange[i].getGameObject().remove();
                            }
                        }
                        break;
                }
                cycle--;
            }
        }, 0, 1000);
    }

    public void explodeDown(Position pos) {
        Timer chrono = new Timer();
        final Sprite[] explosionRange = new Sprite[player.getBombRange()];
        chrono.schedule(new TimerTask() {
            private int cycle = 2;
            @Override
            public void run() {
                switch (cycle) {
                    case 2:
                        for (int i = 1; i <= player.getBombRange(); i++) {
                            int x = pos.getX();
                            int y = pos.getY();
                            Position nextPos = new Position(x, y + i);
                            Position nextNextPos = new Position(x,y + 2);
                            if (canBeExploded(nextPos)) {
                                explosionRange[i - 1] = new Sprite(layer, EXPLOSION.getImage(), new Explosion(game, nextPos));
                                sprites.add(explosionRange[i - 1]);
                                if (game.getGrid().get(nextPos) != null && game.getGrid().get(nextNextPos) != null){
                                    break;
                                }
                                if (game.getGrid().get(nextPos) != null && game.getGrid().get(nextNextPos) == null){
                                    break;}
                            }
                        }
                        break;
                    case 1:
                        for (int i = 0;i<explosionRange.length;i++){
                            int x = pos.getX();
                            int y = pos.getY();
                            Position nextPos = new Position(x, y+i+1);
                            if (nextPos.equals(game.getPlayer().getPosition())){
                                if (!(player.invicibilityEnabled())){
                                    player.takeDamage();
                                    player.enableInvicibility();
                                }
                            }
                            if (game.getGrid().get(nextPos) == null){
                                explosionRange[i].getGameObject().remove();
                                continue;
                            }
                            if (explosionRange[i] != null){
                                game.getGrid().get(nextPos).remove();
                                game.getGrid().remove(nextPos);
                                explosionRange[i].getGameObject().remove();
                            }
                        }
                        break;
                }
                cycle--;
            }
        }, 0, 1000);
    }

    public void explodeLeft(Position pos) {
        Timer chrono = new Timer();
        final Sprite[] explosionRange = new Sprite[player.getBombRange()];
        chrono.schedule(new TimerTask() {
            private int cycle = 2;
            @Override
            public void run() {
                switch (cycle) {
                    case 2:
                        for (int i = 1; i <= player.getBombRange(); i++) {
                            int x = pos.getX();
                            int y = pos.getY();
                            Position nextPos = new Position(x-i, y );
                            Position nextNextPos = new Position(x-2 ,y);
                            if (canBeExploded(nextPos)) {
                                explosionRange[i - 1] = new Sprite(layer, EXPLOSION.getImage(), new Explosion(game, nextPos));
                                sprites.add(explosionRange[i - 1]);
                                if (game.getGrid().get(nextPos) != null && game.getGrid().get(nextNextPos) != null){
                                    break;
                                }
                                if (game.getGrid().get(nextPos) != null && game.getGrid().get(nextNextPos) == null){
                                    break;}
                            }
                        }
                        break;
                    case 1:
                        for (int i = 0;i<explosionRange.length;i++){
                            int x = pos.getX();
                            int y = pos.getY();
                            Position nextPos = new Position(x-i-1, y);
                            if (nextPos.equals(game.getPlayer().getPosition())){
                                if (!(player.invicibilityEnabled())){
                                    player.takeDamage();
                                    player.enableInvicibility();
                                }
                            }
                            if (game.getGrid().get(nextPos) == null){
                                explosionRange[i].getGameObject().remove();
                                continue;
                            }
                            if (explosionRange[i] != null){
                                game.getGrid().get(nextPos).remove();
                                game.getGrid().remove(nextPos);
                                explosionRange[i].getGameObject().remove();
                            }
                        }
                        break;
                }
                cycle--;
            }
        }, 0, 1000);
    }

    public void explodeRight(Position pos) {
        Timer chrono = new Timer();
        final Sprite[] explosionRange = new Sprite[player.getBombRange()];
        chrono.schedule(new TimerTask() {
            private int cycle = 2;
            @Override
            public void run() {
                switch (cycle) {
                    case 2:
                        for (int i = 1; i <= player.getBombRange(); i++) {
                            int x = pos.getX();
                            int y = pos.getY();
                            Position nextPos = new Position(x+i, y );
                            Position nextNextPos = new Position(x+2 ,y);
                            if (canBeExploded(nextPos)) {
                                explosionRange[i - 1] = new Sprite(layer, EXPLOSION.getImage(), new Explosion(game, nextPos));
                                sprites.add(explosionRange[i - 1]);
                                if (game.getGrid().get(nextPos) != null && game.getGrid().get(nextNextPos) != null){
                                    break;
                                }
                                if (game.getGrid().get(nextPos) != null && game.getGrid().get(nextNextPos) == null){
                                    break;}
                            }
                        }
                        break;
                    case 1:
                        for (int i = 0;i<explosionRange.length;i++){
                            int x = pos.getX();
                            int y = pos.getY();
                            Position nextPos = new Position(x+i+1, y);
                            if (nextPos.equals(game.getPlayer().getPosition())){
                                if (!(player.invicibilityEnabled())){
                                    player.takeDamage();
                                    player.enableInvicibility();
                                }
                            }
                            if (game.getGrid().get(nextPos) == null){
                                explosionRange[i].getGameObject().remove();
                                continue;
                            }
                            if (explosionRange[i] != null){
                                game.getGrid().get(nextPos).remove();
                                game.getGrid().remove(nextPos);
                                explosionRange[i].getGameObject().remove();
                            }
                        }
                        break;
                }
                cycle--;
            }
        }, 0, 1000);
    }

    public boolean canBeExploded(Position pos){
        if (game.getGrid().get(pos) instanceof Key){
            return false;
        }else if (game.getGrid().get(pos) instanceof Door){
            return false;
        }else return !(game.getGrid().get(pos) instanceof Princess);
    }

    private void checkCollision(long now) {
        for (int i = 0; i <= game.getGrid().getWidth(); i++) {
            for (int j = 0; j <= game.getGrid().getWidth(); j++) {
                Position position = new Position(i, j);
                if (game.getGrid().get(position) instanceof Monster){
                    if (position.equals(game.getPlayer().getPosition())){
                        if (!(player.invicibilityEnabled())){
                            player.takeDamage();
                            player.enableInvicibility();
                        }
                    }
                }
            }
        }
    }

    private void moveMonsters(long now){
        for (int i = 0; i <= game.getGrid().getWidth(); i++) {
            for (int j = 0; j <= game.getGrid().getWidth(); j++) {
                Position position = new Position(i, j);
                Direction direction = Direction.random();
                if (game.getGrid().get(position) instanceof Monster){
                    if (((Monster) game.getGrid().get(position)).canMove(direction)){
                        ((Monster) game.getGrid().get(position)).doMove(direction);
                    }
                }
            }
        }
    }

    private void changeLevel(long now) {
        if (game.getGrid().get(player.getPosition()) instanceof DoorNextOpened) {
            if (player.isOnDoor()) {
                gameLoop.stop();
                sprites.clear();
                game.actualLevel = game.actualLevel + 1;
                game.setGrid(game.getGridList(game.actualLevel));
                //player.setPosition(new Position(0, 0));
                setPlayerDoorPrev();
                initialize();
                gameLoop.start();
                player.setOnDoor(false);
                System.out.println("false");
            }
        }
            if (game.getGrid().get(player.getPosition()) instanceof DoorPrevOpened) {
                if (player.isOnDoor()) {
                    gameLoop.stop();
                    sprites.clear();
                    game.actualLevel = game.actualLevel - 1;
                    game.setGrid(game.getGridList(game.actualLevel));
                    setPlayerDoorNext();
                    initialize();
                    gameLoop.start();
                    player.setOnDoor(false);
                    System.out.println("false");
                }

            }


        }



    private void setPlayerDoorPrev(){
        for (int i = 0; i <= game.getGrid().getWidth(); i++) {
            for (int j = 0; j <= game.getGrid().getHeight(); j++) {
                Position position = new Position(i, j);
                if (game.getGrid().get(position) instanceof DoorPrevOpened){
                    player.setPosition(position);
                }
            }
        }
    }

    private void setPlayerDoorNext(){
        for (int i = 0; i <= game.getGrid().getWidth(); i++) {
            for (int j = 0; j <= game.getGrid().getHeight(); j++) {
                Position position = new Position(i, j);
                if (game.getGrid().get(position) instanceof DoorNextOpened){
                    player.setPosition(position);
                }
            }
        }
    }

    private void processInput(long now) {
        if (input.isExit()) {
            gameLoop.stop();
            Platform.exit();
            System.exit(0);
        } else if (input.isMoveDown()) {
            player.requestMove(Direction.DOWN);
        } else if (input.isMoveLeft()) {
            player.requestMove(Direction.LEFT);
        } else if (input.isMoveRight()) {
            player.requestMove(Direction.RIGHT);
        } else if (input.isMoveUp()) {
            player.requestMove(Direction.UP);
        } else if (input.isKey()) {
            Position nextPos = player.getDirection().nextPosition(player.getPosition());
            if (game.getGrid().get(nextPos) instanceof Door && player.getKey() > 0) {
                Sprite sprite = new Sprite(layer, DOOR_OPENED.getImage(), player.openDoor(nextPos));
                sprite.render();
            }
        }else if (input.isBomb() && player.getBomb()>0) {
            bomba();
            input.clear();
        }
        input.clear();
    }

    private void showMessage(String msg, Color color) {
        Text waitingForKey = new Text(msg);
        waitingForKey.setTextAlignment(TextAlignment.CENTER);
        waitingForKey.setFont(new Font(60));
        waitingForKey.setFill(color);
        StackPane root = new StackPane();
        root.getChildren().add(waitingForKey);
        Scene scene = new Scene(root, 400, 200, Color.WHITE);
        stage.setTitle(windowTitle);
        stage.setScene(scene);
        input = new Input(scene);
        stage.show();
        new AnimationTimer() {
            public void handle(long now) {
                processInput(now);
            }
        }.start();
    }

    private void update(long now) {
        player.update(now);


        if (player.getLives() == 0) {
            gameLoop.stop();
            showMessage("Perdu!", Color.RED);
        }

        if (player.isWinner()) {
            gameLoop.stop();
            showMessage("Gagné", Color.BLUE);
        }
    }

    public void cleanupSprites() {
        sprites.forEach(sprite -> {
            if (sprite.getGameObject().isDeleted()) {
                game.getGrid().remove(sprite.getPosition());
                cleanUpSprites.add(sprite);
            }
        });
        cleanUpSprites.forEach(Sprite::remove);
        sprites.removeAll(cleanUpSprites);
        cleanUpSprites.clear();
    }

    private void render() {
        sprites.forEach(Sprite::render);
    }

    public void start() {
        gameLoop.start();
    }

}
