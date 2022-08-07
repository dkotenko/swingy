package org.example.model;

import lombok.Data;
import org.example.model.hero.Hero;
import org.example.model.hero.HeroFactory;
import org.example.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;

@Component
@Data
public class GameModel {
    Map map;
    HashMap<String, Hero> heroes;
    Hero currentHero;
    GameState currentState;
    GameState previousState;
    private final ValidationService validationService;

    @Autowired
    @Qualifier("hikariDataSource")
    private DataSource hikariDataSource;

    public GameModel(ValidationService validationService) {
        this.validationService = validationService;
        currentState = GameState.START_MENU;
        previousState = GameState.START_MENU;
        heroes = new HashMap<>();
    }

    public void updateGameState(GameState state) {
        previousState = currentState;
        currentState = state;
    }

    public void createHero(String name, String type) {
        Hero hero = new HeroFactory().create(name, type);
        try {
            validationService.validateHero(hero);
            heroes.put(name, hero);
        } catch (ConstraintViolationException e) {
            System.out.println("Error: hero hadn't been created, invalid input:");
            System.out.println(e.getMessage());
        }
    }
}
