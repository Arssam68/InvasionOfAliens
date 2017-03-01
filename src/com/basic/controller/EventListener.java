package com.basic.controller;

import com.basic.model.Direction;
import com.basic.model.GameObject;

public interface EventListener
{
    void move(Direction direction, GameObject gameObject);
}
