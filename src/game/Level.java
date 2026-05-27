package game;

public class Level {

    int levelNumber;

    int droneSpeed;

    int missileSpeed;

    int shootCooldown;

    public Level(int levelNumber){

        this.levelNumber = levelNumber;

        droneSpeed = (int)(2 * Math.pow(1.15, levelNumber - 1));

        missileSpeed = (int)(4 * Math.pow(1.15, levelNumber - 1));

        shootCooldown = (int)(120 * Math.pow(0.85, levelNumber - 1));
    }

    public int getDroneSpeed() {
        return droneSpeed;
    }

    public int getMissileSpeed() {
        return missileSpeed;
    }

    public int getShootCooldown() {
        return shootCooldown;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}