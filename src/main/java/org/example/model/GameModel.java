package org.example.model;

import org.example.model.hero.Hero;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GameModel {
    Map map;
    ArrayList<Hero> heroList;
    Hero currentHero;
}
