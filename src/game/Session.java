package game;

import entity.Drone;
import entity.Missile;
import entity.Plane;
import gui.KeyHandler;

import java.awt.*;
import java.util.ArrayList;

public class Session {
    GameState gameState;
    Player player;
    Plane plane;

    Squadron squadron;

    ArrayList<Missile> missiles;

    Level currentLevel;

    int screenWidth;
    int screenHeight;


    public Session(int screenWidth, int screenHeight, int tileSize){

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.player = new Player();

        this.plane = new Plane(screenWidth, screenHeight, tileSize);

        missiles = new ArrayList<>();

        currentLevel = new Level(1);

        startLevel();
        gameState = GameState.Running;
    }

    public void startLevel(){
        squadron = new Squadron(screenWidth, currentLevel);

        missiles.clear();

    }

    public void update(KeyHandler keyH){
        // =========================
        // INPUT
        // =========================

        int dx = 0;
        int dy = 0;

        if(keyH.upPressed){
            dy--;
        }

        if(keyH.downPressed){
            dy++;
        }

        if(keyH.leftPressed){
            dx--;
        }

        if(keyH.rightPressed){
            dx++;
        }

        plane.move(dx, dy);

        plane.update();


        // =========================
        // UPDATE SQUADRON
        // =========================

        squadron.update();


        // =========================
        // DRONES SHOOT
        // =========================

        for(Drone drone : squadron.getDrones()){

            if(drone.canShoot()){

                missiles.add(
                        new Missile(
                                drone.getCenterX(),
                                drone.getY(),
                                currentLevel.getMissileSpeed(),
                                screenHeight
                        )
                );

                drone.resetShootCounter();
            }
        }


        // =========================
        // UPDATE MISSILES
        // =========================

        for(int i = 0; i < missiles.size(); i++){

            Missile missile = missiles.get(i);

            missile.update();

            if(missile.isExploded()){

                Explosion explosion = new Explosion(missile.getX(), missile.getAltitude());
                int damage = explosion.calculateDamage(plane);

                plane.reduceEnergy(damage);
                player.calculateScore(damage);

                if (plane.getCurrentEnergy()<=0){
                    if (player.getLives()>0){
                        player.loseLife();
                        plane.restoreEnergy();
                    }
                    else{
                        gameState = GameState.Game_Over;
                    }
                }

                missiles.remove(i);

                i--;

            }

        }


        // =========================
        // NEXT LEVEL
        // =========================

        if(squadron.levelFinished()){
            currentLevel = new Level(
                    currentLevel.getLevelNumber() + 1
            );
            player.addScore(300);
            startLevel();
        }
    }

    public void draw(Graphics g){

        if(gameState == GameState.Game_Over){

            drawGameOverScreen(g);

            return;
        }

        plane.draw(g);

        squadron.draw(g);

        for(Missile missile : missiles){

            missile.draw(g);
        }

        drawHUD(g);
    }

    public void drawHUD(Graphics g){

        Graphics2D g2 = (Graphics2D) g;

        g2.setFont(new Font("Arial", Font.BOLD, 24));

        g2.setColor(Color.WHITE);

        g2.drawString(
                "Lives: " + player.getLives(),
                20,
                40
        );

        g2.drawString(
                "Energy: " + plane.getCurrentEnergy(),
                20,
                80
        );

        g2.drawString(
                "Score: " + player.getScore(),
                20,
                120
        );

        g2.drawString(
                "Level: " + currentLevel.getLevelNumber(),
                20,
                160
        );
    }

    public void drawGameOverScreen(Graphics g){

        Graphics2D g2 = (Graphics2D) g;

        // FONDO

        g2.setColor(Color.BLACK);

        g2.fillRect(
                0,
                0,
                screenWidth,
                screenHeight
        );

        // TITULO

        g2.setColor(Color.RED);

        g2.setFont(
                new Font("Arial", Font.BOLD, 64)
        );

        String title = "GAME OVER";

        int titleX = getCenteredTextX(g2, title);

        g2.drawString(
                title,
                titleX,
                220
        );

        // SCORE

        g2.setColor(Color.WHITE);

        g2.setFont(
                new Font("Arial", Font.BOLD, 32)
        );

        String scoreText =
                "Score: " + player.getScore();

        int scoreX = getCenteredTextX(g2, scoreText);

        g2.drawString(
                scoreText,
                scoreX,
                320
        );

        // RESTART

        g2.setFont(
                new Font("Arial", Font.PLAIN, 24)
        );

        String restartText =
                "Press ENTER to restart";

        int restartX =
                getCenteredTextX(g2, restartText);

        g2.drawString(
                restartText,
                restartX,
                420
        );
    }


    public int getCenteredTextX(
            Graphics2D g2,
            String text
    ){

        int textLength =
                (int)g2.getFontMetrics()
                        .getStringBounds(text, g2)
                        .getWidth();

        return screenWidth / 2 - textLength / 2;
    }

    public GameState getGameState(){
        return gameState;
    }

    public void startNewGame(){

        player = new Player();

        currentLevel = new Level(1);

        plane.setDefaultValues();

        startLevel();

        gameState = GameState.Running;
    }


}