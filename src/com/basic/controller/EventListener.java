package com.basic.controller;

import com.basic.Alien;
import com.basic.model.Direction;

public interface EventListener {
    void move(Direction direction);
    void launch();
    void bombFlush(Alien alien);
        }
