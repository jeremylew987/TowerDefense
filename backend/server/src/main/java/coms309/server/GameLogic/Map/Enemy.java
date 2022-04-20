package coms309.server.GameLogic.Map;

import java.awt.*;

public class Enemy {

    public int id;
    private Point point;
    private int iterator;
    private int health;
    private int speed;

    public Enemy(int id, Point point, int health, int speed) {
        this.id = id;
        this.point = point;
        this.iterator = 0;
        this.health = health;
        this.speed = speed;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIterator() {
        return iterator;
    }
    public void setIterator(int iterator) {
        this.iterator = iterator;
    }

    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Point getPoint() {
        return point;
    }
    public void setPoint(Point point) {
        this.point = point;
    }

    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public void decreaseHealth(int health) { this.health -= health; }
}
