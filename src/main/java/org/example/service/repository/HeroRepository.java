package org.example.service.repository;

import org.example.model.dto.HeroDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface HeroRepository extends CrudRepository<HeroDTO, Long> {
    Optional<HeroDTO> findByName(String name);
}
