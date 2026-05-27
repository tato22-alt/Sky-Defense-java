package game;

import entity.Plane;

public class Explosion {
    int x;
    int y;

    public Explosion(int x,int y){
        this.x = x;
        this.y = y;
    }

    public int calculateDamage(Plane plane){
        double distance = calculateDistance(plane);
        if (distance>=150){
            return 0;
        } else if (distance>=80 ) {
            return 20;
        } else if (distance>=20) {
            return 40;
        }
        return 100;
    }

    public double calculateDistance(Plane plane){
        int dx = plane.getX() - x;
        int dy = plane.getY() - y;

        return Math.sqrt(dx*dx + dy * dy);
    }
}
