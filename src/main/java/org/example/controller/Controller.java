package org.example.controller;

import org.example.model.Model;
import org.example.view.View;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model)
    {
        this.view = view;
        this.model = model;
    }


}
