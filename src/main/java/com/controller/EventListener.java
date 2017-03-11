package com.controller;

import com.Alien;
import com.model.Direction;

public interface EventListener {
    void move(Direction direction);
    void launch();
    void bombFlush(Alien alien);
        }
