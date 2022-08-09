package org.example.model;

import lombok.Data;
import org.example.model.dto.HeroDTO;
import org.example.model.hero.Hero;
import org.example.model.hero.HeroFactory;
import org.example.service.ValidationService;
import org.example.service.repository.HeroRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
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
    private final HeroRepository heroRepository;

    public GameModel(ValidationService validationService, HeroRepository heroRepository) {
        this.validationService = validationService;
        this.heroRepository = heroRepository;
        currentState = GameState.START_MENU;
        previousState = GameState.START_MENU;
        heroes = new HashMap<>();
        loadHeroes();
    }

    public void loadHeroes() {
        ArrayList<HeroDTO> heroDTOS = new ArrayList<>();
        heroRepository.findAll().forEach(heroDTOS::add);
        HeroFactory heroFactory = new HeroFactory();
        heroDTOS.forEach(heroDTO -> heroes.put(
                heroDTO.getName(),
                heroFactory.create(heroDTO)
        ));
    }

    public void updateGameState(GameState state) {
        previousState = currentState;
        currentState = state;
    }

    public void createHero(String name, String type) {
        Hero hero = new HeroFactory().create(name, type);
        try {
            validationService.validateHero(hero);
            heroRepository.save(new HeroDTO(hero));
            System.out.println(heroRepository.findAll().iterator().hasNext());
            heroes.put(name, hero);
        } catch (ConstraintViolationException e) {
            System.out.println("Error: hero hadn't been created, invalid input:");
            System.out.println(e.getMessage());
        }
    }
}
