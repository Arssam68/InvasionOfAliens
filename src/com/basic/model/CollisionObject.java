package com.basic.model;

public abstract class CollisionObject extends GameObject
{
    public CollisionObject(int x, int y, int width, int height)
    {
        super(x, y, width, height);
    }

    public boolean isCollision(GameObject gameObject, Direction direction) {
        switch (direction) {
            case DOWN: {
                if ((getY() + getHeight() / 2 > gameObject.getY() - gameObject.getHeight() / 2) && (getX() == gameObject.getX()))
                    return true;
                break;
            }
            case LEFT: {
                if ((getX() - getWidth() / 2 < gameObject.getX() + gameObject.getWidth() / 2) && (getY() == gameObject.getY()))
                    return true;
                break;
            }
            case RIGHT: {
                if ((getX() + getWidth() / 2 > gameObject.getX() - gameObject.getWidth() / 2) && (getY() == gameObject.getY()))
                    return true;
                break;
            }
            case UP: {
                if ((getY() - getHeight() / 2 < gameObject.getY() + gameObject.getHeight() / 2) && (getX() == gameObject.getX()))
                    return true;
            }
        }
        return false;
    }
}