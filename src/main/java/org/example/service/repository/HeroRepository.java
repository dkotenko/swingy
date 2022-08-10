package org.example.service.repository;

import org.example.model.hero.dto.HeroDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface HeroRepository extends JpaRepository<HeroDTO, Long> {
    Optional<HeroDTO> findByName(String name);
}
