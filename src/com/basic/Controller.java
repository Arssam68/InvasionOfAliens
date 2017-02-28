package com.basic;

import java.awt.event.KeyAdapter;

class Controller extends KeyAdapter {
    private Model model;
    private View view;

    Controller(Model model) {
        this.model = model;
        view = new View(this);
    }

    View getView() {
        return view;
    }
}