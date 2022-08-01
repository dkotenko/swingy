package org.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RandomGenerator {
    @Value("${swingy.random.spread:0.2}")
    double spread;

    /**
     * Math.random() * (b-a) ) + a
     * @return
     */
    public double getRandomCoef() {
        return (Math.random() * spread * 2) + (1 - spread);
    }
}
