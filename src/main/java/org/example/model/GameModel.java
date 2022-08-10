package org.example.model;

import lombok.Data;
import org.example.model.hero.dto.HeroDTO;
import org.example.model.hero.Hero;
import org.example.model.hero.HeroFactory;
import org.example.service.ValidationService;
import org.example.service.repository.HeroRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;

@Component
@Data
public class GameModel {
    Map map;
    Hero currentHero;
    GameState currentState;
    GameState previousState;
    private final ValidationService validationService;
    private final HeroRepository heroRepository;

    public GameModel(ValidationService validationService, HeroRepository heroRepository) {
        this.validationService = validationService;
        this.heroRepository = heroRepository;
        currentState = GameState.START_MENU;
        previousState = GameState.START_MENU;
    }

    public ArrayList<HeroDTO> getHeroes() {
        return (ArrayList<HeroDTO>)heroRepository.findAll();
    }

    public void updateGameState(GameState state) {
        previousState = currentState;
        currentState = state;
    }

    public boolean isHeroExists(String name) {
        return  heroRepository.findByName(name).isPresent();
    }

    public void createHero(String name, String type) {
        Hero hero = new HeroFactory().create(name, type);
        try {
            validationService.validateHero(hero);
            heroRepository.saveAndFlush(new HeroDTO(hero));
        } catch (ConstraintViolationException e) {
            System.out.println("Error: hero hadn't been created, invalid input:");
            System.out.println(e.getMessage());
        }
    }
}
