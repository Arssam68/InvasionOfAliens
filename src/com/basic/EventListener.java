package com.basic;

import com.basic.model.Direction;

public interface EventListener {
    void move(Direction direction);
    void launch();
    void bombFlush(Alien alien);
        }
