package org.example.model;

import lombok.Data;
import org.example.model.hero.dto.HeroDTO;
import org.example.model.hero.Hero;
import org.example.model.hero.HeroFactory;
import org.example.model.map.Directions;
import org.example.model.map.GameMap;
import org.example.model.map.Position;
import org.example.model.monster.Monster;
import org.example.service.ValidationService;
import org.example.service.repository.HeroRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;

@Component
@Data
public class GameModel {
    private GameMap gameMap;
    private Hero currentHero;
    private Monster currentMonster;
    private GameState currentState;
    private GameState previousState;
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

    public void startGame() {
        gameMap = new GameMap(currentHero);
    }

    public void moveHero(Directions direction) {
        boolean isExit = false;
        Position newPosition = new Position(currentHero.getPosition());

        if (direction == Directions.EAST) {
            if (currentHero.getPosition().getX() == gameMap.getSize()) {
                isExit = true;
            } else {
                newPosition.increaseX();
            }
        } else if (direction == Directions.WEST) {
            if (currentHero.getPosition().getX() == 1) {
                isExit = true;
            } else {
                newPosition.decreaseX();
            }
        } else if (direction == Directions.NORTH) {
            if (currentHero.getPosition().getY() == 1) {
                isExit = true;
            } else {
                newPosition.decreaseY();
            }
        } else if (direction == Directions.SOUTH) {
            if (currentHero.getPosition().getY() == gameMap.getSize()) {
                isExit = true;
            } else {
                newPosition.increaseY();
            }
        } else {
            System.err.println("Invalid direction while moving");
        }

        if (isExit) {
            return;
        }
        if (gameMap.getCellByPosition(newPosition).containsMonster()) {
            currentMonster = gameMap.getCellByPosition(newPosition).getMonster();
        } else {
            currentMonster = null;
        }
        gameMap.moveTo(newPosition);
    }

}
