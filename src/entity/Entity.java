package entity;

import java.awt.*;

public abstract class Entity {
    public int x,y;
    public int speed;
    Image img;

    public abstract void update();
}
