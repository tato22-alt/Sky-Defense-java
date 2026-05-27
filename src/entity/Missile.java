package entity;

import javax.swing.*;
import java.awt.*;

public class Missile extends Entity {
    int missileWidth;
    int missileHeight;

    int screenHeight;

    Image missileImg;

    int explosionY;
    boolean exploded;


    public Missile(int startX, int startY, int speed, int screenHeight) {
        x = startX;
        y = startY;

        this.screenHeight = screenHeight;

        missileWidth = 16;
        missileHeight = 32;

        this.speed = speed;

        missileImg = new ImageIcon(
                getClass().getResource("/PracticaJuegos/Sky/Images/Left.png")
        ).getImage();


        setExplosionAltitude();

        exploded = false;
    }

    @Override
    public void update() {
        move();

        exploded = shouldExplode();
    }

    public void draw(Graphics g) {
        g.drawImage(missileImg, x, y, missileWidth, missileHeight, null);
    }



    public void move(){
        y+= speed;
    }

    public void explode(){

    }

    public boolean shouldExplode(){
        return y >= explosionY;

    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExplosionAltitude(){
        explosionY = 200 + (int) (Math.random() * (screenHeight-200));
    }

    public int getAltitude(){return y;}

    public int getX(){return x;}

}