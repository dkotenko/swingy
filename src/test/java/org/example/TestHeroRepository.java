package org.example;

import org.example.model.hero.dto.HeroDTO;
import org.example.service.repository.HeroRepository;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

@Disabled
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestHeroRepository {
    @Autowired
    HeroRepository heroRepository;
    Connection connection;

    @Disabled
    @BeforeClass
    public void init() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
    }


    @Test
    @Disabled
    @Transactional
    public void findById() {
        Optional<HeroDTO> hero = heroRepository.findById(1L);
        Assert.assertTrue(!hero.isPresent());
    }
}
