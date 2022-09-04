package org.example.model;

import lombok.Data;
import org.example.model.hero.Hero;
import org.example.model.hero.HeroFactory;
import org.example.model.hero.dto.HeroDTO;
import org.example.model.map.Directions;
import org.example.model.map.GameMap;
import org.example.model.map.GameMapCell;
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

    public void deleteHeroFromDb(HeroDTO heroDTO) {
        heroRepository.delete(heroDTO);
    }

    public void retreatToSafeCell() {
        int heroX = currentHero.getPosition().getX();
        int heroY = currentHero.getPosition().getY();
        for (int i = 1; i < gameMap.getSize(); i++) {
            for (int y = heroY - i; y < heroY + i; y++) {
                for (int x = heroX - i; x < heroX + i; x++) {
                    if (
                            y <= gameMap.getSize()
                            && y >= 1
                            && x <= gameMap.getSize()
                            && x >= 1
                            && x != heroX
                            && y != heroY
                            && !gameMap.getCells()[y][x].containsMonster()
                    ) {
                        gameMap.moveTo(new Position(x, y));
                        return;
                    }
                }
            }
        }
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

    public void updateHero() {
        heroRepository.saveAndFlush(new HeroDTO(currentHero));
    }

    public String createHero(String name, String type) {
        Hero hero = new HeroFactory().create(name, type);
        try {
            validationService.validateHero(hero);
            heroRepository.saveAndFlush(new HeroDTO(hero));
        } catch (ConstraintViolationException e) {
            return e.getMessage();
        }
        return null;
    }

    public void loadMap() {
        gameMap = new GameMap(currentHero);
    }

    public static final String MOVE_EXIT = "exit";
    public static final String MOVE_NOT_EXIT = "non exit";

    public String moveHero(Directions direction) {
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
            return MOVE_EXIT;
        }
        if (gameMap.getCellByPosition(newPosition).containsMonster()) {
            currentMonster = gameMap.getCellByPosition(newPosition).getMonster();
        } else {
            currentMonster = null;
        }
        gameMap.moveTo(newPosition);
        return MOVE_NOT_EXIT;
    }

    public void doRetreat() {
        Position position = currentHero.getPosition();
        gameMap.moveTo(currentHero.getPrevPosition());
        currentHero.setPrevPosition(position);
    }

    public void deleteCurrentMonster() {
        GameMapCell currCell = gameMap.getCellByPosition(currentHero.getPosition());
        currentMonster = null;
        currCell.deleteMonster();

    }

}
