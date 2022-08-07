package org.example.service;

import org.example.model.hero.Hero;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Service
public class ValidationService {

    private Validator validator;

    public ValidationService(Validator validator) {
        this.validator = validator;
    }

    public void validateHero(Hero hero) {
        Set<ConstraintViolation<Hero>> violations = validator.validate(hero);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
