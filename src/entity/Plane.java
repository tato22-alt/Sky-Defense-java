package entity;

import javax.swing.*;
import java.awt.*;

public class Plane extends Entity{
    int planeWidth;
    int planeHeight;
    Image planeImg;

    int screenWidth;
    int screenHeight;
    int tileSize;

    int maxEnergy;
    int currentEnergy;

    public Plane(int screenWidth, int screenHeight, int tileSize) {
        this.planeImg = new ImageIcon(getClass().getResource("/PracticaJuegos/Sky/Images/Up.png")).getImage();
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.tileSize = tileSize;
        setDefaultValues();
    }

    public void setDefaultValues(){
        planeWidth = tileSize;
        planeHeight = tileSize * 2;

        x = screenWidth / 2 - planeWidth / 2;
        y = screenHeight - planeHeight - tileSize;

        this.maxEnergy = 100;
        this.currentEnergy = maxEnergy;
        speed = 4;
    }

    public void move(int dx, int dy){

        int currentSpeed = speed;

        if(dx != 0 && dy != 0){
            currentSpeed = 3;
        }

        x += dx * currentSpeed;
        y += dy * currentSpeed;


        validateHorizontalBounds();

        validateVerticalBounds();
    }


    @Override
    public void update(){
    }

    public void draw(Graphics g){

        g.drawImage(planeImg,x,y,planeWidth,planeHeight,null);
    }

    public int getY(){
        return y;
    }

    public int getX(){return x;}

    public int getCurrentEnergy(){return currentEnergy;}

    public void reduceEnergy(int amount){
        currentEnergy -= amount;
        if (currentEnergy<=0) {
            currentEnergy =0;
        }
    }


    public void validateHorizontalBounds(){
        if (x + planeWidth > screenWidth){
            x = screenWidth - planeWidth;

        }

        if(x < 0){
            x = 0;
        }
    }

    public void validateVerticalBounds() {
        if(y < 200){
            y = 200;
        }

        if(y + planeHeight > screenHeight ){
            y = screenHeight - planeHeight ;
        }
    }

    public void restoreEnergy(){
        currentEnergy = maxEnergy;
    }

}
